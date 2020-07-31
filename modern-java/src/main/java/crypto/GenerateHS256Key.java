package crypto;

import java.security.Key;
import java.util.Base64;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

public class GenerateHS256Key {

  public static void main(String[] args) {
    // generate a random HMAC
    Key key = MacProvider.generateKey(SignatureAlgorithm.HS256);

    // Get the key data
    byte keyData[] = key.getEncoded();
    System.out.println(Base64.getEncoder().encodeToString(keyData));
    // Store data in a file...
  }

}
