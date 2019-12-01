package com.matas.liteconstruct.ignite.test;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.Query;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TestQueryRepository {

  @Autowired
  JdbcTemplate jdbcTemplate;
  
  @Autowired
  Ignite ignite;

  public void updateLngRecord(String name) {
    IgniteCache cache = ignite.cache("ClassAccessRuleCache");
    String sql = String.format("update class_access_rule set id = '%1$s' where name = '%2$s'",
        "00000000-0000-0000-0000-000000000000", name);
    Query qry =
        new SqlFieldsQuery(sql); //
    // create query
    qry.setLocal(true); // make query local

    QueryCursor cursor = cache.query(qry); // exceute query
//    cursor.getAll().stream().forEach(item -> {
//      log.info(">>> item: {}", item);
//    });
//    String sql = String.format("update class_access_rule set id = '%1$s' where name = '%2$s'",
//        "00000000-0000-0000-0000-000000000000", name);
//    log.info("sql: {}", sql);
//    jdbcTemplate.update(sql);
  }
}
