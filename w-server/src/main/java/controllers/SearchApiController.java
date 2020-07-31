package controllers;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import extractors.LoggedInUser;
import inputs.SearchInputs;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import repositories.ProtectedRepo;
import utils.auth.AuthUser;
import utils.validation.Validation;

@Singleton
public class SearchApiController {
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(SearchApiController.class);

  private final Validation validation;
  private final ProtectedRepo repo;

  @Inject
  public SearchApiController(ProtectedRepo protectedRepo) {
    this.repo = protectedRepo;
    this.validation = new Validation();
  }

  public Result search(@LoggedInUser AuthUser authUser, @Param("value") String value,
      @Param("limit") Optional<String> limit) {
    SearchInputs inputs = SearchInputs.builder()
        .withSearch(value)
        .withLimit(limit.orElseGet(null))
        .build();

    Optional<Result> validationErrors = validation.validateInputs("search", inputs);
    if (validationErrors.isPresent()) {
      return validationErrors.get();
    }

    List<String> results =
        repo.search().search(inputs);

    return Results.json().render(results);
  }

}
