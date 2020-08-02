/**
 * 版权所有(C)，上海海鼎信息工程股份有限公司，2020，所有权利保留。
 * <p>
 * 项目名：	springCloud
 * 文件名：	FeignProviderBackFactoryTest.java
 * 模块说明：
 * 修改历史：
 * 2020/8/2 - taozhi - 创建。
 */
package com.snail.feignconsumer.hystrix;

import com.snail.feignconsumer.controller.ConsumerApi;
import com.snail.userapi.Person;
import feign.FeignException;
import feign.hystrix.FallbackFactory;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.stereotype.Component;

/**
 * @author taozhi
 */
@Component
public class FeignProviderBackFactoryTest implements FallbackFactory<ConsumerApi> {
  @Override
  public ConsumerApi create(Throwable throwable) {
    return new ConsumerApi() {
      @Override
      public String alive() {
        // 针对不同异常信息，网络错误、服务不可用、服务500... 做针对性处理
        System.out.println(ToStringBuilder.reflectionToString(throwable));
        // 或者自定义异常类型，进行不同处理
        if (throwable instanceof FeignException.InternalServerError) {
          return "远程服务器 500" + throwable.getLocalizedMessage();
        }
        return "降级了";
      }

      @Override
      public String getById(Integer id) {
        return null;
      }

      @Override
      public Person postPserson(Person person) {
        return null;
      }
    };
  }
}
