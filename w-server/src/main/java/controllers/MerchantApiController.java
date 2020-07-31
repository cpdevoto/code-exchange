package controllers;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import extractors.LoggedInUser;
import inputs.FetchMerchantInputs;
import inputs.FetchMerchantsInputs;
import inputs.FetchUserFavoriteMerchantsInputs;
import models.dto.MerchantDetailsDto;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import ninja.params.PathParam;
import repositories.ProtectedRepo;
import utils.auth.AuthUser;
import utils.validation.Validation;

@Singleton
public class MerchantApiController {
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(MerchantApiController.class);

  private final Validation validation;
  private final ProtectedRepo repo;

  @Inject
  public MerchantApiController(ProtectedRepo protectedRepo) {
    this.repo = protectedRepo;
    this.validation = new Validation();
  }

  public Result merchants(@LoggedInUser AuthUser authUser, @Param("lat") String latitude,
      @Param("lng") String longitude,
      @Param("limit") String limit, @Param("skip") Optional<String> skip,
      @Param("radius") String rad, @Param("search") String search) {
    FetchMerchantsInputs inputs = FetchMerchantsInputs.builder()
        .withLat(latitude)
        .withLng(longitude)
        .withLimit(limit)
        .withSkip(skip)
        .withRadius(rad)
        .withSearch(search)
        .build();

    Optional<Result> validationErrors = validation.validateInputs("merchants", inputs);
    if (validationErrors.isPresent()) {
      return validationErrors.get();
    }

    List<MerchantDetailsDto> merchants =
        repo.merchants().fetch_merchants(inputs);

    return Results.json().render(merchants);
  }

  public Result merchant(@LoggedInUser AuthUser authUser, @PathParam("id") Long id,
      @Param("lat") String latitude, @Param("lng") String longitude) {
    FetchMerchantInputs inputs = FetchMerchantInputs.builder()
        .withLat(latitude)
        .withLng(longitude)
        .build();

    Optional<Result> validationErrors = validation.validateInputs("merchant", inputs);
    if (validationErrors.isPresent()) {
      return validationErrors.get();
    }

    Optional<MerchantDetailsDto> merchant =
        repo.merchants().fetch_merchant(authUser.getId(), id, inputs);

    if (!merchant.isPresent()) {
      return Results.noContent().status(404);
    }
    return Results.json().render(merchant.get());
  }

  public Result user_favorite_merchants(@LoggedInUser AuthUser authUser,
      @Param("lat") String latitude, @Param("lng") String longitude, @Param("limit") String limit,
      @Param("skip") Optional<String> skip) {
    FetchUserFavoriteMerchantsInputs inputs = FetchUserFavoriteMerchantsInputs.builder()
        .withLat(latitude)
        .withLng(longitude)
        .withLimit(limit)
        .withSkip(skip)
        .build();

    Optional<Result> validationErrors =
        validation.validateInputs("user_favorite_merchants", inputs);
    if (validationErrors.isPresent()) {
      return validationErrors.get();
    }

    List<MerchantDetailsDto> merchants =
        repo.merchants().fetch_user_favorite_merchants(authUser.getId(),
            inputs);

    return Results.json().render(merchants);
  }

}
