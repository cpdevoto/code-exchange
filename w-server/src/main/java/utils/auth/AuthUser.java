package utils.auth;

import static java.util.Objects.requireNonNull;

import models.User;

public class AuthUser {
  private final Long id;
  private final String subject;
  private final String name;
  private final String preferredUserName;
  private final String givenName;
  private final String familyName;
  private final String email;

  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(AuthUser jwtPayload) {
    return new Builder(jwtPayload);
  }

  private AuthUser(Builder builder) {
    this.id = builder.id;
    this.subject = builder.subject;
    this.name = builder.name;
    this.preferredUserName = builder.preferredUserName;
    this.givenName = builder.givenName;
    this.familyName = builder.familyName;
    this.email = builder.email;
  }

  public Long getId() {
    return id;
  }

  public String getSubject() {
    return subject;
  }

  public String getName() {
    return name;
  }

  public String getPreferredUserName() {
    return preferredUserName;
  }

  public String getGivenName() {
    return givenName;
  }

  public String getFamilyName() {
    return familyName;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    result = prime * result + ((familyName == null) ? 0 : familyName.hashCode());
    result = prime * result + ((givenName == null) ? 0 : givenName.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((preferredUserName == null) ? 0 : preferredUserName.hashCode());
    result = prime * result + ((subject == null) ? 0 : subject.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AuthUser other = (AuthUser) obj;
    if (email == null) {
      if (other.email != null)
        return false;
    } else if (!email.equals(other.email))
      return false;
    if (familyName == null) {
      if (other.familyName != null)
        return false;
    } else if (!familyName.equals(other.familyName))
      return false;
    if (givenName == null) {
      if (other.givenName != null)
        return false;
    } else if (!givenName.equals(other.givenName))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (preferredUserName == null) {
      if (other.preferredUserName != null)
        return false;
    } else if (!preferredUserName.equals(other.preferredUserName))
      return false;
    if (subject == null) {
      if (other.subject != null)
        return false;
    } else if (!subject.equals(other.subject))
      return false;
    return true;
  }

  public boolean equalsUser(User user) {
    if (user == null)
      return false;
    if (email == null) {
      if (user.getEmail() != null)
        return false;
    } else if (!email.equals(user.getEmail()))
      return false;
    if (familyName == null) {
      if (user.getFamilyName() != null)
        return false;
    } else if (!familyName.equals(user.getFamilyName()))
      return false;
    if (givenName == null) {
      if (user.getGivenName() != null)
        return false;
    } else if (!givenName.equals(user.getGivenName()))
      return false;
    if (name == null) {
      if (user.getName() != null)
        return false;
    } else if (!name.equals(user.getName()))
      return false;
    if (preferredUserName == null) {
      if (user.getPreferredUsername() != null)
        return false;
    } else if (!preferredUserName.equals(user.getPreferredUsername()))
      return false;
    if (subject == null) {
      if (user.getSubject() != null)
        return false;
    } else if (!subject.equals(user.getSubject()))
      return false;
    return true;
  }


  @Override
  public String toString() {
    return "AuthUser [id=" + id + ", subject=" + subject + ", name=" + name + ", preferredUserName="
        + preferredUserName + ", givenName=" + givenName + ", familyName=" + familyName + ", email="
        + email + "]";
  }

  public static class Builder {
    private Long id;
    private String subject;
    private String name;
    private String preferredUserName;
    private String givenName;
    private String familyName;
    private String email;

    private Builder() {}

    private Builder(AuthUser user) {
      requireNonNull(user, "user cannot be null");
      this.id = user.id;
      this.subject = user.subject;
      this.name = user.name;
      this.preferredUserName = user.preferredUserName;
      this.givenName = user.givenName;
      this.familyName = user.familyName;
      this.email = user.email;
    }

    public Builder withId(Long id) {
      this.id = id;
      return this;
    }

    public Builder withSubject(String subject) {
      requireNonNull(subject, "subject cannot be null");
      this.subject = subject;
      return this;
    }

    public Builder withName(String name) {
      requireNonNull(name, "name cannot be null");
      this.name = name;
      return this;
    }

    public Builder withPreferredUserName(String preferredUserName) {
      requireNonNull(preferredUserName, "preferredUserName cannot be null");
      this.preferredUserName = preferredUserName;
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

    public Builder withEmail(String email) {
      requireNonNull(email, "email cannot be null");
      this.email = email;
      return this;
    }

    public AuthUser build() {
      requireNonNull(subject, "subject cannot be null");
      requireNonNull(name, "name cannot be null");
      requireNonNull(preferredUserName, "preferredUserName cannot be null");
      requireNonNull(givenName, "givenName cannot be null");
      requireNonNull(familyName, "familyName cannot be null");
      requireNonNull(email, "email cannot be null");
      return new AuthUser(this);
    }
  }
}
