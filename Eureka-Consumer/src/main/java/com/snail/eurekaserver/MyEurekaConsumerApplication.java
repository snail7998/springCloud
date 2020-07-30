/**
 * 版权所有(C)，上海海鼎信息工程股份有限公司，2020，所有权利保留。
 * <p>
 * 项目名：	springCloud
 * 文件名：	EurekaConsumerApplication.java
 * 模块说明：
 * 修改历史：
 * 2020/7/28 - taozhi - 创建。
 */
package com.snail.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author taozhi
 */
// cloud Hoxton版本，客户端不需要加 @EnableEurekaClient 注解了
@SpringBootApplication
public class MyEurekaConsumerApplication {
  public static void main(String[] args) {
    SpringApplication.run(MyEurekaConsumerApplication.class, args);
  }
}
