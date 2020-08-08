/**
 * 版权所有(C)，上海海鼎信息工程股份有限公司，2020，所有权利保留。
 * <p>
 * 项目名：	springCloud
 * 文件名：	FeignProviderApplication.java
 * 模块说明：
 * 修改历史：
 * 2020/8/1 - taozhi - 创建。
 */
package com.snail.feignconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author taozhi
 */
@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker // hystrix 整合 restTemplate
@EnableHystrixDashboard
public class FeignConsumerApplication {

  public static void main(String[] args) {
    SpringApplication.run(FeignConsumerApplication.class, args);
  }
}
