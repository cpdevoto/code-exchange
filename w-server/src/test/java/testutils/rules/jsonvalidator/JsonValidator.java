package testutils.rules.jsonvalidator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.equalTo;

import static java.util.Objects.requireNonNull;

public class JsonValidator implements TestRule {
  private final String json;
  private final String compactJson;
  private final ObjectMapper mapper = new ObjectMapper();


  public static JsonValidatorJsonResourceBuilder builder() {
    return new Builder();
  }

  private JsonValidator(Builder builder) {
    ClassResource jsonResource = builder.jsonResource;
    try (
        InputStream in =
            jsonResource.getFileLocatorClass().getResourceAsStream(jsonResource.getFileName())) {
      if (in == null) {
        throw new FileNotFoundException("File " + jsonResource.getFileName()
            + " could not be found in the same package as "
            + jsonResource.getFileLocatorClass().getName());
      }
      try (Scanner s = new Scanner(in)) {
        s.useDelimiter("\\A");
        this.json = s.hasNext() ? s.next() : "";

        JsonNode jsonNode = mapper.readValue(json, JsonNode.class);
        this.compactJson = jsonNode.toString();
      }
    } catch (IOException io) {
      throw new RuntimeException(io);
    }
  }

  @Override
  public Statement apply(Statement base, Description description) {
    return new JsonValidatorStatement(base);
  }

  public <T> void assertJson(T object) throws JsonProcessingException {
    requireNonNull(object, "object cannot be null");
    String serializedJson = mapper.writeValueAsString(object);
    assertThat(serializedJson, equalTo(compactJson));
  }

  public <T> T deserialize(Class<T> klass)
      throws JsonParseException, JsonMappingException, IOException {
    requireNonNull(klass, "klass cannot be null");
    T object = mapper.readValue(json, klass);
    return object;
  }

  public <T> T deserialize(TypeReference<T> klass)
      throws JsonParseException, JsonMappingException, IOException {
    requireNonNull(klass, "klass cannot be null");
    T object = mapper.readValue(json, klass);
    return object;
  }

  static class Builder implements JsonValidatorJsonResourceBuilder, JsonValidatorBuilder {
    private ClassResource jsonResource;

    private Builder() {}

    @Override
    public Builder withJsonResource(Class<?> fileLocatorClass, String fileName) {
      requireNonNull(fileLocatorClass, "fileLocatorClass cannot be null");
      requireNonNull(fileName, "fileName cannot be null");
      this.jsonResource = new ClassResource(fileLocatorClass, fileName);
      return this;
    }

    @Override
    public JsonValidator build() {
      return new JsonValidator(this);
    }


  }

  public static interface JsonValidatorJsonResourceBuilder {
    JsonValidatorBuilder withJsonResource(Class<?> fileLocatorClass, String fileName);
  }

  public static interface JsonValidatorBuilder {
    JsonValidator build();
  }
}
