package me.yorick.poc.persistence.service;

import java.util.concurrent.CompletableFuture;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import me.yorick.poc.persistence.entity.User;
import me.yorick.poc.persistence.repository.UserRepository;

@Service
@EnableAsync
public class UserMockService {

	@Autowired
	private UserRepository userRepository;
	

	@Transactional
	public User findUserByEmailTransactional(String email) {
		return userRepository.findByEmail(email);
	}
	
	
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	@Async
	public CompletableFuture<User> asyncFindUserByEmail(String email){
		return CompletableFuture.completedFuture(findUserByEmail(email));
	}
	
}
