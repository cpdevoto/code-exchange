package com.resolute.rule.generator.integration;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Test;

import com.resolute.rule.generator.common.model.EntityIndex;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.equalTo;

public class RuleTemplateApiTest extends AbstractIntegrationTest {

  @Test
  public void test_get() throws IOException {
    List<EntityIndex> templates = client.ruleTemplateApi().get();
    assertNotNull(templates);
    Map<Integer, EntityIndex> templateMap =
        templates.stream().collect(Collectors.toMap(EntityIndex::getId, Function.identity()));

    assertThat(templateMap.containsKey(51335), equalTo(true));
    assertThat(templateMap.get(51335).getText(), equalTo("Invariant"));
  }

}
