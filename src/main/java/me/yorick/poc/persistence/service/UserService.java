package me.yorick.poc.persistence.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.yorick.poc.persistence.entity.User;
import me.yorick.poc.persistence.repository.UserRepository;

@Service
public class UserService {

	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public void createNewUser(User user) {
		
		if(userRepository.existsByEmail(user.getEmail())) {
			throw new RuntimeException("Email is already registered");
		}
		
		userRepository.save(user);
		
	}

	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	
	public void clone(String email) {
		
		User user =  findUserByEmail(email);
		user.setGender("female");
		
		User clone = new User();
		clone.setEmail(user.getEmail());
		clone.setName(user.getName());
		clone.setMobile(user.getMobile());
		clone.setGender("male");
		
		
		userRepository.save(clone);
	}
	
	public boolean isSameReference(String email, User outside) {
		System.out.println("--------------------------------------------------------------------------------------");
		
		return  outside == findUserByEmail("yorick1@hello.com");
	   
	}
	
	
}
