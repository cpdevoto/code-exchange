package utils.http;

import java.io.IOException;

import okhttp3.Response;

@FunctionalInterface
public interface ResponseHandlerFunction<R> {

  R apply(Response response) throws IOException;

}
