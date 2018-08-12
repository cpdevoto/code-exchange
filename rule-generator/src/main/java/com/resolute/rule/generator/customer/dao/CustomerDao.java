package com.resolute.rule.generator.customer.dao;

import java.util.List;

import javax.sql.DataSource;

import com.resolute.rule.generator.common.model.EntityIndex;

public interface CustomerDao {

  public static CustomerDao create(DataSource dataSource) {
    return CustomerJdbcDao.create(dataSource);
  }

  public List<EntityIndex> get();

}
