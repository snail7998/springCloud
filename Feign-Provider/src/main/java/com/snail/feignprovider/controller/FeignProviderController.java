/**
 * 版权所有(C)，上海海鼎信息工程股份有限公司，2020，所有权利保留。
 * <p>
 * 项目名：	springCloud
 * 文件名：	UserController.java
 * 模块说明：
 * 修改历史：
 * 2020/8/1 - taozhi - 创建。
 */
package com.snail.feignprovider.controller;

import com.snail.userapi.Person;
import com.snail.userapi.UserApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author taozhi
 */
@RestController
public class FeignProviderController implements UserApi {

  @Value("${server.port}")
  String port;

  @Override
  public String alive(){
    // int i = 1/0;
    return "ok" + port;
  }

  @Override
  public String getById(Integer id) {
    return "provider：" + id;
  }

  @Override
  public Person postPserson(Person person) {
    System.out.println(person);
    person.setId(346);
    return person;
  }
}
