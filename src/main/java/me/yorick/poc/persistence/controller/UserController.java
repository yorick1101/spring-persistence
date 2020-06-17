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
	private User user2;

	@GetMapping("/register/{index}")
	public void register(@PathVariable int index) {

		switch(index) {
		case 1:
			userService.createNewUser(user);
			break;
		case 2:
			userService.createNewUser(user2);
			break;
		}
		
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
	
	
	@GetMapping("/testFlush/{id}")
	public void testFlush(@PathVariable int id) {
		switch(id) {
		case 1:
			userService.isFlush(user.getEmail());
			break;
		case 2:
			userService.isFlush2(user.getEmail());
			break;
		case 3:
			userService.isFlush3(user.getEmail());
			break;
		case 4:
			userService.isFlush4(user.getEmail());
			break;
		case 5:
			userService.isFlush5(user.getEmail());
			break;
		case 6:
			userService.isFlush6(user.getEmail());
			break;
		case 7:
			userService.isFlush7(user.getEmail());
			break;
		default:
			break;
			
		}
	}
	
	@GetMapping("/testFind")
	public void testFind() {
		//userService.isFlushFind1(user.getEmail(), user2.getEmail());
		//userService.isFlushFind2(user.getEmail());
		userService.isFlushFind3(user.getEmail(), user2.getEmail());
	}
	@PostConstruct
	private void defautlUser() {
		User user = new User();
		user.setName("yorick");
		user.setEmail("yorick@hello.com");
		user.setGender("male");
		user.setMobile("1234567");

		this.user = user;
		
		User user2 = new User();
		user2.setName("yorick2");
		user2.setEmail("yorick2@hello.com");
		user2.setGender("male");
		user2.setMobile("2234567");

		this.user2 = user2;
	}
}
