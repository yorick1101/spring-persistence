package me.yorick.poc.persistence.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.yorick.poc.persistence.entity.User;
import me.yorick.poc.persistence.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	private User user;

	@GetMapping("/register/{name}")
	public void register(@PathVariable String name) {

		userService.createNewUser(user);
	}

	@GetMapping("/test")
	public Map<String, Object> test() throws InterruptedException, ExecutionException {
		Map<String, Object> result = new HashMap<>(); 

		result.put("findTwice", userService.findTwice(user.getEmail()));
		result.put("fidnTwiceCrossService", userService.fidnTwiceCrossService(user.getEmail()));
		result.put("fidnTwiceCrossServiceTransactional", userService.fidnTwiceCrossServiceTransactional(user.getEmail()));
		result.put("fidnTwiceAsyncCrossService", userService.fidnTwiceAsyncCrossService(user.getEmail()));
		
		return result;
		
	}
	
	
	@GetMapping("/testFlush")
	public void testFlush() {
		userService.isFlush3(user.getEmail());
	}

	@PostConstruct
	private void defautlUser() {
		User user = new User();
		user.setName("yorick");
		user.setEmail("yorick@hello.com");
		user.setGender("male");
		user.setMobile("1234567");

		this.user = user;
	}
}
