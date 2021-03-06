package utils.http;

import static java.util.Objects.requireNonNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public abstract class AbstractHttpRequestFactory {
  private final String baseUrl;
  private final OkHttpClient client;

  protected <F extends AbstractHttpRequestFactory, B extends Builder<F, B>> AbstractHttpRequestFactory(
      Builder<F, B> builder) {
    this.baseUrl = builder.baseUrl;
    this.client = new OkHttpClient.Builder()
        .connectTimeout(builder.connectTimeout.timeout, builder.connectTimeout.unit)
        .readTimeout(builder.readTimeout.timeout, builder.readTimeout.unit)
        .build();
  }

  String getBaseUrl() {
    return baseUrl;
  }

  OkHttpClient getClient() {
    return client;
  }

  public abstract static class Builder<F extends AbstractHttpRequestFactory, B extends Builder<F, B>> {
    private String baseUrl;
    private Timeout connectTimeout = new Timeout(30, TimeUnit.SECONDS);
    private Timeout readTimeout = new Timeout(30, TimeUnit.SECONDS);

    protected Builder(String baseUrl) {
      this.baseUrl = requireNonNull(baseUrl, "baseUrl cannot be null");
    }

    public B withConnectTimeout(int timeout, TimeUnit unit) {
      requireNonNull(unit, "unit cannot be null");
      this.connectTimeout = new Timeout(timeout, unit);
      return getThis();
    }

    public B withReadTimeout(int timeout, TimeUnit unit) {
      requireNonNull(unit, "unit cannot be null");
      this.readTimeout = new Timeout(timeout, unit);
      return getThis();
    }

    public F build() {
      return newInstance();
    }

    protected abstract B getThis();

    protected abstract F newInstance();

  }

  private static class Timeout {
    private int timeout;
    private TimeUnit unit;

    private Timeout(int timeout, TimeUnit unit) {
      this.timeout = timeout;
      this.unit = unit;
    }
  }

}
