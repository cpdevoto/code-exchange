package extractors;

import ninja.Context;
import ninja.params.ArgumentExtractor;
import utils.auth.AuthUser;

public class LoggedInUserExtractor implements ArgumentExtractor<AuthUser> {

  @Override
  public AuthUser extract(Context context) {
    if (context.getAttribute("auth-user") != null) {
      return context.getAttribute("auth-user", AuthUser.class);
    }
    return null;
  }

  @Override
  public Class<AuthUser> getExtractedType() {
    return AuthUser.class;
  }

  @Override
  public String getFieldName() {
    return null;
  }

}
