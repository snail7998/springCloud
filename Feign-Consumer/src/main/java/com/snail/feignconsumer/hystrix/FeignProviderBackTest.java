/**
 * 版权所有(C)，上海海鼎信息工程股份有限公司，2020，所有权利保留。
 * <p>
 * 项目名：	springCloud
 * 文件名：	FeignProviderBack.java
 * 模块说明：
 * 修改历史：
 * 2020/8/2 - taozhi - 创建。
 */
package com.snail.feignconsumer.hystrix;

import com.snail.feignconsumer.controller.ConsumerApi;
import com.snail.userapi.Person;
import org.springframework.stereotype.Component;

/**
 * @author taozhi
 */
@Component
public class FeignProviderBackTest implements ConsumerApi {
  @Override
  public String alive() {
    return "降级了";
  }

  @Override
  public String getById(Integer id) {
    return "降级了" + id;
  }

  @Override
  public Person postPserson(Person person) {
    return new Person();
  }
}
