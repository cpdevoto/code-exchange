package com.resolute.rule.generator.customer.dao;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.resolute.rule.generator.common.model.EntityIndex;
import com.resolute.rule.generator.dao.AbstractDaoTest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.equalTo;

public class CustomerDaoTest extends AbstractDaoTest {

  private CustomerDao dao;

  @Before
  public void before() {
    this.dao = CustomerDao.create(dataSource);
  }

  @Test
  public void test_get() {
    List<EntityIndex> customers = dao.get();

    assertNotNull(customers);
    Map<Integer, EntityIndex> customerMap =
        customers.stream().collect(Collectors.toMap(EntityIndex::getId, Function.identity()));

    assertThat(customerMap.containsKey(3), equalTo(true));
    assertThat(customerMap.get(3).getText(), equalTo("McLaren"));
  }
}
