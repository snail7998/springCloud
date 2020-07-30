/**
 * 版权所有(C)，上海海鼎信息工程股份有限公司，2020，所有权利保留。
 * <p>
 * 项目名：	springCloud
 * 文件名：	ConsumerController.java
 * 模块说明：
 * 修改历史：
 * 2020/7/29 - taozhi - 创建。
 */
package com.snail.eurekaserver.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author taozhi
 */
@RestController
public class ConsumerController {

  @Autowired
  DiscoveryClient client; // 是 springframework 下的包，是提供的一个抽象接口，而不是具体实现

  @Autowired
  EurekaClient client2; // EurekaClient 是具体的实现类，无需注入

  @Autowired
  LoadBalancerClient lb; // 注意也是一个 抽象接口，ribbon只是其一个实现

  @GetMapping("/getServices")
  public void getServices(){
    // 打印出所有的 服务
    List<String> services = client.getServices();
    for (String service : services) {
      System.out.println(service); // 输出 provider consumer
    }
  }

  @GetMapping("/getInstance")
  public Object getInstance(){
    // 访问 provider节点的信息，为下一步客户端调用做准备
    List<ServiceInstance> instanceList = client.getInstances("provider");
    for (ServiceInstance instance : instanceList) {
      System.out.println(ToStringBuilder.reflectionToString(instance));
    }
    return client.getInstances("provider");
  }

  /**
   * 最简单版本的远程服务调用
   * 通过Eureka 服务器调用
   */
  @GetMapping("/getInstance2")
  public void getInstance2(){
    // 具体服务
    //	List<InstanceInfo> instances = client2.getInstancesById("localhost:provider:80");

    // 使用服务名 ，找列表
    List<InstanceInfo> instances = client2.getInstancesByVipAddress("provider", false);

//    for (InstanceInfo ins : instances) {
//      System.out.println(ToStringBuilder.reflectionToString(ins));
//    }

    if(instances.size()>0) {
      // 服务
      InstanceInfo instanceInfo = instances.get(0);
      if(instanceInfo.getStatus() == InstanceInfo.InstanceStatus.UP) {

        String url =	"http://" + instanceInfo.getHostName() +":"+ instanceInfo.getPort() + "/getHi";
        System.out.println("url:" + url);

        // 使用RestTemplate 替代 httpClient 调用服务
        RestTemplate restTemplate = new RestTemplate();
        String respStr = restTemplate.getForObject(url, String.class);
        System.out.println("respStr ..."  + respStr);
      }

    }
  }

  /**
   * 使用 ribbon 实现远程调用
   */
  @GetMapping("/myRPC")
  public void myRPC(){
    // ribbon 会自动帮我们剔除 status 是 down 的节点
    ServiceInstance instance = lb.choose("provider");

    String url =	"http://" + instance.getHost() +":"+ instance.getPort() + "/getHi";
    System.out.println("url:" + url);

    // 使用RestTemplate 替代 httpClient 调用服务
    RestTemplate restTemplate = new RestTemplate();
    String respStr = restTemplate.getForObject(url, String.class);
    System.out.println("respStr ribbon ..."  + respStr);
  }
}
