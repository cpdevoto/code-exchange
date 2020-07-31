package com.resolute.utils.simple.pojo_generator;

import static com.resolute.utils.simple.pojo_generator.PojoGenerator.dataMember;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import com.resolute.utils.simple.IOUtils;

public class PojoGeneratorTest {

  @Test
  public void test() throws IOException {
    String expected = IOUtils.resourceToString("generated-pojo.txt", StandardCharsets.UTF_8,
        PojoGeneratorTest.class);

    // @formatter:off
    String source = PojoGenerator.forClass("Equipment")
        .inPackage("com.resolute.api.model.building")
        .jacksonAnnotations()
        .dataMembers(
            dataMember(dm -> dm.name("id")
                .dataType("String")
                .required()
            ),
            dataMember(dm -> dm.name("name")
                .dataType("String")
                .required()
            ),
            dataMember(dm -> dm.name("type")
                .dataType("String")
            )
        )
        .generate();
    // @formatter:on

    System.out.println(source);
    // assertThat(source.trim(), equalTo(expected.trim()));
  }
}
