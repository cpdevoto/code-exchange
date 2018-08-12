package com.resolute.rule.generator.integration;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Ignore;
import org.junit.Test;

import com.resolute.rule.generator.common.model.EntityIndex;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.equalTo;

public class CustomerApiTest extends AbstractIntegrationTest {

  @Test
  public void test_get() throws IOException {
    List<EntityIndex> customers = client.customerApi().get();
    assertNotNull(customers);
    Map<Integer, EntityIndex> customerMap =
        customers.stream().collect(Collectors.toMap(EntityIndex::getId, Function.identity()));

    assertThat(customerMap.containsKey(3), equalTo(true));
    assertThat(customerMap.get(3).getText(), equalTo("McLaren"));
  }

}
