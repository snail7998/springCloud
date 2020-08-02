/**
 * 版权所有(C)，上海海鼎信息工程股份有限公司，2020，所有权利保留。
 * <p>
 * 项目名：	springCloud
 * 文件名：	ConsumerApi.java
 * 模块说明：
 * 修改历史：
 * 2020/8/1 - taozhi - 创建。
 */
package com.snail.feignconsumer.controller;

import com.snail.feignconsumer.hystrix.FeignProviderBackFactoryTest;
import com.snail.userapi.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author taozhi
 */
// @FeignClient(name = "xxoo", url = "http://localhost:81") 不走Eureka 的形式，用url属性指定服务器列表
// @FeignClient(name = "feign-provider") // 结合Eureka，name是服务提供方的 application name
// @FeignClient(name = "feign-provider", fallback = FeignProviderBackTest.class) // feign 整合 Hystrix
@FeignClient(name = "feign-provider", fallback = FeignProviderBackFactoryTest.class) // feign 整合 Hystrix，使用FallBackFactory
public interface ConsumerApi extends UserApi {
}
