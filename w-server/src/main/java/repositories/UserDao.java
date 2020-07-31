package repositories;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import com.avaje.ebean.Ebean;

import models.User;

public class UserDao {


  public Optional<User> fetch(long id) {
    User user = Ebean.find(User.class)
        .where().eq("id", id)
        .findUnique();

    return Optional.ofNullable(user);
  }

  public Optional<User> fetch(String subject) {
    User user = Ebean.find(User.class)
        .where().eq("subject", subject)
        .findUnique();

    return Optional.ofNullable(user);
  }

  public List<User> fetch() {
    List<User> users = Ebean.find(User.class)
        .findList();

    return users;
  }

  public void save(User user) {
    requireNonNull(user, "user cannot be null");
    Ebean.save(user);
  }

  public void delete(User user) {
    requireNonNull(user, "user cannot be null");
    Ebean.delete(user);
  }

}
