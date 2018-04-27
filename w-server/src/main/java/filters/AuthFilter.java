package filters;

import static java.util.Objects.requireNonNull;
import static utils.auth.Jwt.BEARER_PREFIX;
import static utils.auth.keycloak.KeycloakProps.AUDIENCE;
import static utils.auth.keycloak.KeycloakProps.ISSUER;

import java.util.Optional;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import models.User;
import ninja.Context;
import ninja.Cookie;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Result;
import ninja.Results;
import ninja.utils.NinjaProperties;
import repositories.ProtectedRepo;
import utils.auth.Algorithm;
import utils.auth.AuthUser;
import utils.auth.Jwt;
import utils.auth.JwtException;
import utils.auth.JwtFactory;
import utils.auth.JwtVerifier;

public class AuthFilter implements Filter {
  private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);

  private final String issuer;
  private final String audience;
  private final JwtFactory jwtFactory;
  private final JwtVerifier jwtVerifier;
  private ProtectedRepo repo;


  @Inject
  public AuthFilter(NinjaProperties config, JwtFactory jwtFactory, JwtVerifier jwtVerifier,
      ProtectedRepo repo) {
    requireNonNull(config, "config cannot be null");
    this.issuer =
        requireNonNull(config.get(ISSUER), "Expected the configuration property named " + ISSUER);
    this.audience = requireNonNull(config.get(AUDIENCE),
        "Expected the configuration property named " + AUDIENCE);
    this.jwtFactory = requireNonNull(jwtFactory, "jwtFactory cannot be null");
    this.jwtVerifier = requireNonNull(jwtVerifier, "jwtVerifier cannot be null");
    this.repo = requireNonNull(repo, "repo cannot be null");
  }

  @Override
  public Result filter(FilterChain chain, Context context) {
    AuthLevel authLevel = getAuthLevel(context.getRequestPath());
    if (authLevel == AuthLevel.UNPROTECTED_RESOURCE) {
      return filterUnprotectedResource(chain, context);
    } else if (authLevel == AuthLevel.PROTECTED_RESOURCE) {
      return filterProtectedResource(chain, context);
    }
    return filterProtectedApi(chain, context);
  }

  private Result filterProtectedApi(FilterChain chain, Context context) {
    // Protected API Call: check "Authorization" header
    String authHeader = context.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
      return forbiddenApi();
    }

    return filterProtected(chain, context, (parser) -> parser.parseAuthHeader(authHeader),
        this::forbiddenResource);

  }

  private Result filterProtectedResource(FilterChain chain, Context context) {
    // Protected Resource: check "access-token" cookie
    Cookie cookie = context.getCookie("access-token");
    if (cookie == null) {
      return Results.redirect("/auth");
    }
    return filterProtected(chain, context, (parser) -> parser.parse(cookie.getValue()),
        this::forbiddenApi);
  }

  private Result filterUnprotectedResource(FilterChain chain, Context context) {
    // Unprotected Resource: let it go through
    return chain.next(context);
  }

  private Result filterProtected(FilterChain chain, Context context, JwtSupplier jwtSupplier,
      Supplier<Result> forbidden) {
    Jwt jwt = null;
    try {
      jwt = jwtSupplier.get(jwtFactory.newJwt()
          .assertAlgorithm(Algorithm.RS256)
          .assertAudience(this.audience)
          .assertIssuer(this.issuer));

      jwtVerifier.verify(jwt);

    } catch (Exception ex) {
      log.error("A problem occurred while attempting to process a JWT token", ex);
      return forbidden.get();
    }
    AuthUser authUser = jwt.toAuthUser();
    authUser = persistUser(authUser);
    context.setAttribute("auth-user", authUser);
    return chain.next(context);
  }

  private AuthUser persistUser(AuthUser authUser) {
    Optional<User> result = repo.users().fetch(authUser.getSubject());
    User user = result.isPresent() ? result.get() : new User();
    if (!authUser.equalsUser(user)) {
      user.setSubject(authUser.getSubject());
      user.setEmail(authUser.getEmail());
      user.setPreferredUsername(authUser.getPreferredUserName());
      user.setName(authUser.getName());
      user.setGivenName(authUser.getGivenName());
      user.setFamilyName(authUser.getFamilyName());
      repo.users().save(user);
      Optional<User> savedUser = repo.users().fetch(authUser.getSubject());
      if (savedUser.isPresent()) {
        user.setId(savedUser.get().getId());
      } else {
        log.warn("Unable to retrieve the user that was just saved with subject: "
            + authUser.getSubject());
      }
    }
    return AuthUser.builder(authUser)
        .withId(user.getId())
        .build();
  }

  private AuthLevel getAuthLevel(String requestPath) {
    log.info("REQUEST PATH: " + requestPath);
    if (requestPath.equals("/auth") ||
        requestPath.equals("/auth/app") ||
        requestPath.startsWith("/assets/") ||
        requestPath.equals("/privacy-policy.html") ||
        requestPath.equals("/forbidden.html") ||
        requestPath.equals("/favicon.ico")) {
      return AuthLevel.UNPROTECTED_RESOURCE;
    } else if (!requestPath.startsWith("/api/")) {
      return AuthLevel.PROTECTED_RESOURCE;
    }
    return AuthLevel.PROTECTED_API;
  }

  private Result forbiddenResource() {
    return Results.redirect("/forbidden.html");
  }

  private Result forbiddenApi() {
    return Results.noContent().status(403);
  }

  private static enum AuthLevel {
    PROTECTED_RESOURCE, PROTECTED_API, UNPROTECTED_RESOURCE;
  }

  @FunctionalInterface
  private static interface JwtSupplier {
    public Jwt get(JwtFactory.Parser parser) throws JwtException;
  }
}
