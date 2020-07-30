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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author taozhi
 */
@RestController
public class ProviderController {

  @Value("${server.port}")
  String port;

  @GetMapping("/getHi")
  public String getHi(){
    return "Hi，我的端口是：" + port;
  }

}
