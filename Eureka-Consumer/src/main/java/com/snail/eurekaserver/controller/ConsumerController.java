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
import com.snail.eurekaserver.entity.User;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

  @Autowired
  RestTemplate restTemplate;

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
  @GetMapping("/testRibbon")
  public String testRibbon(){
    // ribbon 会自动帮我们剔除 status 是 down 的节点
    // 默认轮询算法：区域轮询算法
    ServiceInstance instance = lb.choose("provider");

    String url =	"http://" + instance.getHost() +":"+ instance.getPort() + "/getHi";
    System.out.println("url:" + url);
    // 使用RestTemplate 替代 httpClient 调用服务
    return restTemplate.getForObject(url, String.class);
  }

  /**
   * 使用 ribbon + restTemplate 实现远程调用
   * 需使用到 @LoadBalanced 注解
   */
  @GetMapping("/testRestTemplate")
  public String testRestTemplate(){
    String url =	"http://provider/getHi";
    return restTemplate.getForObject(url, String.class);
  }

  /**
   * 测试 RestTemplate
   *
   *  getForXxx 使用 GET 请求
   *  postForXxx 使用 POST 请求
   */

  @GetMapping("/getUser")
  public User getUser(){
    String url =	"http://provider/getUser";
    return restTemplate.getForObject(url, User.class);
  }

  @GetMapping("/getUserWithParm")
  public User getUserWithParm(){

    // 使用单个参数
    // String url =	"http://provider/getUserWithParm?name={1}";
    // return restTemplate.getForObject(url, User.class, "james");

    // 使用map作为参数
    Map<String, String> map = Collections.singletonMap("name", "curry");
    String url =	"http://provider/getUserWithParm?name={name}"; // 必须和map的key一致

    return restTemplate.getForObject(url, User.class, map);
  }

  @GetMapping("/postUsers")
  public User postUsers(){
    String url ="http://provider/postParam";
    Map<String, String> map = Collections.singletonMap("name", "tomphson");
    ResponseEntity<User> entity = restTemplate.postForEntity(url, map, User.class);
    System.out.println("entity : " + entity);
    return null;
  }

  /**
   * 使用  postForLocation
   * @param resp
   * @throws Exception
   */
  @GetMapping("/postLocation")
  public void psotLocation(HttpServletResponse resp) throws Exception {
    String url = "http://provider/postLocation";
    Map<String, String> map = Collections.singletonMap("name", "curry");
    URI location = restTemplate.postForLocation(url, map, User.class);
    resp.sendRedirect(location.toURL().toString());
  }

}
