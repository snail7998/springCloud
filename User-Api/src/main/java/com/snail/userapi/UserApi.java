package com.snail.userapi;

import org.springframework.web.bind.annotation.*;

/**
 * 将 接口抽成一个服务，然后install 成jar让 provider和consumer分别引入
 * 这种方式，解耦合，且只适合非异构的客户端，同属java语言这样操作更方便，面向接口编程
 */
@RequestMapping("/User")
public interface UserApi {

	/**
	 * 查看当前服务状态~~~
	 * @return (* ￣3)(ε￣ *)
	 */
	@GetMapping("/alive")
	public String alive();
	
	@GetMapping("/getById")
	public String getById(@RequestParam("id")Integer id);

	@PostMapping("/postPserson")
	public Person postPserson(@RequestBody Person person);
}
