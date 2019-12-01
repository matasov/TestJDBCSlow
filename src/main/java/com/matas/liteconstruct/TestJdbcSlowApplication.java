package com.matas.liteconstruct;

import java.lang.reflect.Field;
import java.util.UUID;
import javax.sql.DataSource;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CachePeekMode;
import org.apache.ignite.cache.query.Query;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.springdata20.repository.config.EnableIgniteRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.matas.liteconstruct.ignite.config.ServerConfigurationFactory;
import com.matas.liteconstruct.ignite.model.ClassAccessRule;
import com.matas.liteconstruct.ignite.test.TestQueryRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class TestJdbcSlowApplication implements CommandLineRunner {

  @Autowired
  DataSource datasource;

  // @Autowired
  // ServerConfigurationFactory configFactory;

  @Autowired
  Ignite ignite;

  @Autowired
  TestQueryRepository testRepository;

  // @Autowired
  // AccessRuleIgniteRepository repository;

  // @Autowired
  // ApplicationContext context;

  // private static AnnotationConfigApplicationContext ctx;
  // //
  // private static AccessRuleIgniteRepository accessIgniteRepository;

  public static void main(String[] args) {
    SpringApplication.run(TestJdbcSlowApplication.class, args);
    // ctx = new AnnotationConfigApplicationContext();
    //
    // // Explicitly registering Spring configuration.
    // ctx.register(ServerConfigurationFactory.class);
    //
    // ctx.refresh();
    //
    // // Getting a reference to PersonRepository.
    // accessIgniteRepository = ctx.getBean(AccessRuleIgniteRepository.class);
    // ctx = new AnnotationConfigApplicationContext();
    // ctx.register(ServerConfigurationFactory.class);
    // ctx.register(DataSource.class);
    // ctx.register(AccessRuleIgniteRepository.class);
    // ctx.refresh();
  }

  @Override
  public void run(String... args) throws Exception {
    // log.info("before data source: {}", datasource);
    // Ignite ignite = Ignition.start(configFactory.createConfiguration());
    // log.info("test 1 data source: {}", datasource);
    // System.out.println(">>> Loading caches...");
    // // log.info(">>> dataSource {}", dataSource);
    IgniteCache<UUID, ClassAccessRule> cache = ignite.cache("ClassAccessRuleCache");
    // cache.loadCache(null);
    // System.out.println(">>> Loading cache: ClassAccessRuleCache");
    ignite.cache("ClassAccessRuleCache").loadCache(null);
    log.info(">>> Load {}", cache.localSize(CachePeekMode.ALL));
    log.info(">>> found element: {}",
        cache.get(UUID.fromString("11000000-0000-0000-0000-000000000000")));
    ClassAccessRule element = cache.get(UUID.fromString("11000000-0000-0000-0000-000000000000"));
    log.info(">>> exist element: {}", element.getClass().getClassLoader()
        .loadClass("com.matas.liteconstruct.ignite.model.ClassAccessRule"));

    ClassAccessRule elementNew =
        new ClassAccessRule(element.getId(),
            element.getName(), element.getCompanyId(), element.getClassId(),
            UUID.fromString("11000000-0000-0000-0000-000000000000"), element.getEditAccess(), element.getPriority(),
            element.getLevelAccess(), element.getLevelValue());
    cache.replace(element.getId(), element, elementNew);
    
    // testRepository.updateLngRecord("test for ignite");
    log.info(">>> zero element: {}",
        cache.get(UUID.fromString("00000000-0000-0000-0000-000000000000")));
    log.info(">>> check element: {}",
        cache.get(UUID.fromString("11000000-0000-0000-0000-000000000000")));
    log.info(">>> all size: {}", cache.localSize(CachePeekMode.ALL));
    // context.getBean(AccessRuleIgniteRepository.class).findByName("");
    // Query qry = new SqlFieldsQuery("select * from class_access_rule"); //
    Query qry =
        new SqlFieldsQuery("select * from class_access_rule where name = 'test for ignite'"); //
    // create query
    qry.setLocal(true); // make query local

    QueryCursor cursor = cache.query(qry); // exceute query
    cursor.getAll().stream().forEach(item -> {
      log.info(">>> item: {}", item);
    });

    System.out.println(">>> All caches loaded!");
  }

}
