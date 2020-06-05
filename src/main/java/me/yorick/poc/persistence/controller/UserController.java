package me.yorick.poc.persistence.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.yorick.poc.persistence.entity.User;
import me.yorick.poc.persistence.repository.UserRepository;
import me.yorick.poc.persistence.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/register/{name}")
	public void register(@PathVariable String name) {

		String email = createEmail(name);
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setGender("male");
		user.setMobile("1234567");

		userService.createNewUser(user);
	}

	@GetMapping("/clone/{name}")
	public void clone(@PathVariable String name) {

		String email = createEmail(name);
		User user = userRepository.findByEmail(email);
		user.setMobile("abcdefg");

		userService.clone(email);
	}

	@GetMapping("/check/{name}")
	public boolean check(@PathVariable String name){
		String email = createEmail(name);
		return userService.isSameReference(email, userRepository.findByEmail(email));
	}

	private String createEmail(String name) {
		return name+"@hello.com";	
	}
}
