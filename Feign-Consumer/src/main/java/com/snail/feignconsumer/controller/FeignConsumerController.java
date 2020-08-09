/**
 * 版权所有(C)，上海海鼎信息工程股份有限公司，2020，所有权利保留。
 * <p>
 * 项目名：	springCloud
 * 文件名：	MainController.java
 * 模块说明：
 * 修改历史：
 * 2020/8/1 - taozhi - 创建。
 */
package com.snail.feignconsumer.controller;

import com.snail.userapi.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author taozhi
 */
@RestController
public class FeignConsumerController {

  @Value("${server.port}")
  String port;

//  @Qualifier("")
  @Autowired
  ConsumerApi api;

  @GetMapping("/alive")
  public String alive(){
    // 测试 zuul 访问
    return "Consumer port:" + port + ">>>>>>>" + "Provider port:" + this.api.alive();
  }

  @GetMapping("/getById")
  public String getById() {
    return this.api.getById(123);
  }

  @GetMapping("/postPserson")
  public Person postPserson() {
    Person person = new Person();
    person.setName("snail");
    person.setId(12);
    return this.api.postPserson(person);
  }

}
