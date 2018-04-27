package utils.http;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;

class HttpUtilsHelper {

  public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
  public static final MediaType TEXT = MediaType.parse("text/plain; charset=utf-8");
  public static final ObjectMapper MAPPER = new ObjectMapper();

  private HttpUtilsHelper() {}

}
