package com.matas.liteconstruct;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.matas.liteconstruct.ignite.model.ClassAccessRule;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/")
public class TestController {

//  @Autowired
//  AccessRuleIgniteRepository repository;

  @GetMapping("")
  public List<ClassAccessRule> findById() {

//    log.info("result: {}", repository.findAll().iterator().next());
//    repository.findAll().forEach(item -> {
//      log.info("item: {}", item.toString());
//    });
//    return repository.getAllValues();
    return null;
  }
}
