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
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;

/**
 * @author taozhi
 */
// cloud Hoxton版本，客户端不需要加 @EnableEurekaClient 注解了
@SpringBootApplication
public class MyEurekaConsumerApplication {
  public static void main(String[] args) {
    SpringApplication.run(MyEurekaConsumerApplication.class, args);
  }

  @Bean
  @LoadBalanced
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  }

  /**
   * 通过代码切换负载均衡策略
   * @return
   */
/*  @Bean
  public IRule myRule() {
    //return new RoundRobinRule();
    return new RandomRule();
    //return new RetryRule();
  }*/
}
