package controllers;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import extractors.LoggedInUser;
import inputs.FetchMerchantNoticesInputs;
import inputs.FetchUserNoticesInputs;
import models.dto.NoticeDto;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import ninja.params.PathParam;
import repositories.ProtectedRepo;
import utils.auth.AuthUser;
import utils.validation.Validation;

@Singleton
public class NoticeApiController {
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(NoticeApiController.class);

  private final Validation validation;
  private final ProtectedRepo repo;

  @Inject
  public NoticeApiController(ProtectedRepo protectedRepo) {
    this.repo = protectedRepo;
    this.validation = new Validation();
  }

  public Result user_notices(@LoggedInUser AuthUser authUser,
      @Param("lat") String latitude, @Param("lng") String longitude, @Param("limit") String limit,
      @Param("skip") Optional<String> skip) {
    FetchUserNoticesInputs inputs = FetchUserNoticesInputs.builder()
        .withLat(latitude)
        .withLng(longitude)
        .withLimit(limit)
        .withSkip(skip)
        .build();

    Optional<Result> validationErrors = validation.validateInputs("user_notices", inputs);
    if (validationErrors.isPresent()) {
      return validationErrors.get();
    }

    List<NoticeDto> notices =
        repo.notices().fetch_user_notices(authUser.getId(),
            inputs);

    return Results.json().render(notices);
  }

  public Result merchant_notices(@LoggedInUser AuthUser authUser, @PathParam("id") Long id,
      @Param("lat") String latitude, @Param("lng") String longitude, @Param("limit") String limit,
      @Param("skip") Optional<String> skip) {
    FetchMerchantNoticesInputs inputs = FetchMerchantNoticesInputs.builder()
        .withLat(latitude)
        .withLng(longitude)
        .withLimit(limit)
        .withSkip(skip)
        .build();

    Optional<Result> validationErrors = validation.validateInputs("user_notices", inputs);
    if (validationErrors.isPresent()) {
      return validationErrors.get();
    }

    List<NoticeDto> notices =
        repo.notices().fetch_merchant_notices(authUser.getId(), id,
            inputs);

    return Results.json().render(notices);
  }
}
