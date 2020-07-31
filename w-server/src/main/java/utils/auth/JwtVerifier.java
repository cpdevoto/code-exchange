package utils.auth;

import java.security.PublicKey;
import java.security.Signature;
import java.util.Objects;

import javax.inject.Inject;

public class JwtVerifier {

  private final KeyProvider keyProvider;

  @Inject
  public JwtVerifier(KeyProvider keyProvider) {
    this.keyProvider = keyProvider;
  }

  public void verify(Jwt jwt) throws JwtVerificationException {
    Objects.requireNonNull(jwt, "jwt cannot be null");
    Algorithm algorithm = jwt.getHeader().getAlgorithm();
    PublicKey key = keyProvider.getPublicKey(algorithm);
    if (key == null) {
      throw new JwtVerificationException("Could not locate the public key");
    }
    try {
      Signature signature = Signature.getInstance("SHA256withRSA");
      signature.initVerify(key);
      byte[] contentBytes = jwt.getContentBytes();
      byte[] signatureBytes = jwt.getSignatureBytes();
      signature.update(contentBytes);
      if (!signature.verify(signatureBytes)) {
        throw new JwtVerificationException("Invalid signature");
      }
    } catch (Exception e) {
      throw new JwtVerificationException(e);
    }

  }

}
