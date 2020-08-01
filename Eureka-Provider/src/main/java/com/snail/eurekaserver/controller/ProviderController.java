/**
 * 版权所有(C)，上海海鼎信息工程股份有限公司，2020，所有权利保留。
 * <p>
 * 项目名：	springCloud
 * 文件名：	MainController.java
 * 模块说明：
 * 修改历史：
 * 2020/7/29 - taozhi - 创建。
 */
package com.snail.eurekaserver.controller;

import com.snail.eurekaserver.entity.User;
import com.snail.eurekaserver.health.HealthStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

/**
 * @author taozhi
 */
@RestController
public class ProviderController {

  @Autowired
  HealthStatusService healthStatusService;

  @Value("${server.port}")
  String port;

  @GetMapping("/getHi")
  public String getHi(){
    return "Hi，我的端口是：" + port;
  }

  @GetMapping("/health")
  public String health(@RequestParam("status") Boolean status) {
    healthStatusService.setStatus(status);
    return healthStatusService.getStatus();
  }

  @GetMapping("/getUser")
  public User getUser(){
    return new User("snail", 26);
  }

  @GetMapping("/getUserWithParm")
  public User getUserWithParm(String name){
    User user = new User();
    user.setName(name);
    user.setAge(12);
    return user;
  }

  @PostMapping("/postParam")
  public User postParam(@RequestBody String name) {
    System.out.println(name);
    User person = new User();
    person.setAge(100);
    person.setName(name);
    return person;
  }

  /**
   * postForLocation 返回一个url 让请求发跳转
   * 需要设置头信息！！
   */
  @PostMapping("/postLocation")
  public URI postParam(@RequestBody User user, HttpServletResponse response) throws Exception {
    // 直接跳转到百度，并且搜索
    URI uri = new URI("https://www.baidu.com/s?wd="+user.getName());
    // 不设置会报错
    response.addHeader("Location", uri.toString());
    return uri;
  }
}
