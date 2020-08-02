package com.snail.userapi;

import org.springframework.web.bind.annotation.*;

/**
 * 将 接口抽成一个服务，然后install 成jar让 provider和consumer分别引入
 * 这种方式，解耦合，且只适合非异构的客户端，同属java语言这样操作更方便，面向接口编程
 */
// @RequestMapping("/User") // 注意！！如果使用 Hystrix，由于熔断回调类的存在，这里不能加 RequestMapping 注解，这是Hystrix 的坑
public interface UserApi {

	/**
	 * 查看当前服务状态~~~
	 * @return (* ￣3)(ε￣ *)
	 */
	@GetMapping("/alive")
	public String alive();

	/**
	 * 参数传递，必须带 @RequestParam
	 * @param id
	 * @return
	 */
	@GetMapping("/getById")
	public String getById(@RequestParam("id")Integer id);

	@PostMapping("/postPserson")
	public Person postPserson(@RequestBody Person person);
}
