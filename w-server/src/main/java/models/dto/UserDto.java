package models.dto;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import models.User;

@JsonInclude(Include.NON_NULL)
@JsonDeserialize(builder = UserDto.Builder.class)
public class UserDto {
  private final Long id;
  private final boolean admin;
  private final String subject;
  private final String email;
  private final String preferredUsername;
  private final String name;
  private final String givenName;
  private final String familyName;

  @JsonCreator
  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(User user) {
    return new Builder(user);
  }

  public static Builder builder(UserDto user) {
    return new Builder(user);
  }

  private UserDto(Builder builder) {
    this.id = builder.id;
    this.admin = builder.admin;
    this.subject = builder.subject;
    this.email = builder.email;
    this.preferredUsername = builder.preferredUsername;
    this.name = builder.name;
    this.givenName = builder.givenName;
    this.familyName = builder.familyName;
  }

  public Long getId() {
    return id;
  }

  public boolean getAdmin() {
    return admin;
  }

  public String getSubject() {
    return subject;
  }

  public String getEmail() {
    return email;
  }

  public String getPreferredUsername() {
    return preferredUsername;
  }

  public String getName() {
    return name;
  }

  public String getGivenName() {
    return givenName;
  }

  public String getFamilyName() {
    return familyName;
  }

  @JsonPOJOBuilder
  public static class Builder {
    private Long id;
    private Boolean admin;
    private String subject;
    private String email;
    private String preferredUsername;
    private String name;
    private String givenName;
    private String familyName;

    private Builder() {}

    private Builder(User user) {
      requireNonNull(user, "user cannot be null");
      this.id = user.getId();
      this.admin = user.getAdmin();
      this.subject = user.getSubject();
      this.email = user.getEmail();
      this.preferredUsername = user.getPreferredUsername();
      this.name = user.getName();
      this.givenName = user.getGivenName();
      this.familyName = user.getFamilyName();
    }

    private Builder(UserDto user) {
      requireNonNull(user, "user cannot be null");
      this.id = user.id;
      this.admin = user.admin;
      this.subject = user.subject;
      this.email = user.email;
      this.preferredUsername = user.preferredUsername;
      this.name = user.name;
      this.givenName = user.givenName;
      this.familyName = user.familyName;
    }

    public Builder withId(long id) {
      this.id = id;
      return this;
    }

    public Builder withAdmin(boolean admin) {
      this.admin = admin;
      return this;
    }

    public Builder withSubject(String subject) {
      requireNonNull(subject, "subject cannot be null");
      this.subject = subject;
      return this;
    }

    public Builder withEmail(String email) {
      requireNonNull(email, "email cannot be null");
      this.email = email;
      return this;
    }

    public Builder withPreferredUsername(String preferredUsername) {
      requireNonNull(preferredUsername, "preferredUsername cannot be null");
      this.preferredUsername = preferredUsername;
      return this;
    }

    public Builder withName(String name) {
      requireNonNull(name, "name cannot be null");
      this.name = name;
      return this;
    }

    public Builder withGivenName(String givenName) {
      requireNonNull(givenName, "givenName cannot be null");
      this.givenName = givenName;
      return this;
    }

    public Builder withFamilyName(String familyName) {
      requireNonNull(familyName, "familyName cannot be null");
      this.familyName = familyName;
      return this;
    }

    public UserDto build() {
      requireNonNull(admin, "admin cannot be null");
      requireNonNull(subject, "subject cannot be null");
      requireNonNull(email, "email cannot be null");
      requireNonNull(preferredUsername, "preferredUsername cannot be null");
      requireNonNull(name, "name cannot be null");
      requireNonNull(givenName, "givenName cannot be null");
      requireNonNull(familyName, "familyName cannot be null");
      return new UserDto(this);
    }
  }
}
