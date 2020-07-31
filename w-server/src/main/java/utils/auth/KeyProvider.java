package utils.auth;

import java.security.PublicKey;

public interface KeyProvider {

  public PublicKey getPublicKey(Algorithm algorithm);
}
