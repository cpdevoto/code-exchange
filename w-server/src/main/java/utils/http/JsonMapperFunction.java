package utils.http;

import java.io.IOException;

@FunctionalInterface
interface JsonMapperFunction<R> {

  R apply(String json) throws IOException;

}
