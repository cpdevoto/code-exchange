package controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import extractors.LoggedInUser;
import models.User;
import models.dto.UserDto;
import ninja.Result;
import ninja.Results;
import repositories.ProtectedRepo;
import utils.auth.AuthUser;

/**
 * Created by douglasdrouillard on 3/25/18.
 */

@Singleton
public class UserApiController {
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(UserApiController.class);

  private final ProtectedRepo repo;

  @Inject
  public UserApiController(ProtectedRepo protectedRepo) {
    this.repo = protectedRepo;
  }

  public Result users_index(@LoggedInUser AuthUser authUser) {
    List<User> users = this.repo.users().fetch();
    List<UserDto> userDtos =
        users.stream().map(u -> UserDto.builder(u).build()).collect(Collectors.toList());
    return Results.json().render(userDtos);
  }

}
