package me.yorick.poc.persistence.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.yorick.poc.persistence.entity.User;
import me.yorick.poc.persistence.repository.UserRepository;

@Service
public class UserService {


	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMockService userMockService;

	@Autowired
	private EntityManager entityManager;

	public void createNewUser(User user) {
		userRepository.save(user);	
	}

	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	//In same method
	public boolean findTwice(String email) {
		User user1 =  findUserByEmail(email);
		User user2 =  findUserByEmail(email);	
		return user1 == user2;
	}

	//One comes from another service
	public boolean fidnTwiceCrossService(String email) {

		User user1 =  findUserByEmail(email);
		User user2 =  userMockService.findUserByEmail(email);	
		return user1 == user2;
	}

	//One comes from another service
	public boolean fidnTwiceCrossServiceTransactional(String email) {

		User user1 =  findUserByEmail(email);
		User user2 =  userMockService.findUserByEmailTransactional(email);	
		return user1 == user2;
	}

	//One comes from another service asynchrounsly
	@Transactional
	public boolean fidnTwiceAsyncCrossService(String email) throws InterruptedException, ExecutionException {

		User user1 =  findUserByEmail(email);
		CompletableFuture<User> user2 =  userMockService.asyncFindUserByEmail(email);	
		return user1 == user2.get();
	}


	//Update a user without save, then save a new user
	//user's mobile is updated
	public void isFlush(String email) {
		User user =  findUserByEmail(email);
		user.setMobile(new Date().toLocaleString());

		User newUser = newUser();
		userRepository.save(newUser);
	}

	//Save a new user, then find and update a user without save
	//user's mobile is not updated
	public void isFlush2(String email) {

		User newUser = newUser();
		userRepository.save(newUser);

		User user =  findUserByEmail(email);
		user.setMobile(new Date().toLocaleString());
	}

	//Same as isFlush2, but annotated with @Transactional
	//Call this method from inside the class : user's mobile is not updated
	//Call this method from outside the class : user's mobile  updated
	@Transactional
	public void isFlush3(String email) {

		User newUser = newUser();
		userRepository.save(newUser);

		User user =  findUserByEmail(email);
		user.setMobile(new Date().toLocaleString());

	}

	//Detach the entity
	//user's mobile is not updated
	public void isFlush4(String email) {

		User newUser = newUser();
		userRepository.save(newUser);

		User user =  findUserByEmail(email);
		entityManager.detach(user);
		user.setMobile(new Date().toLocaleString());

	}

	//Detach the entity 
	//user's mobile is not updated
	@Transactional
	public void isFlush5(String email) {

		User newUser = newUser();
		userRepository.save(newUser);

		User user =  findUserByEmail(email);
		entityManager.detach(user);
		user.setMobile(new Date().toLocaleString());

	}

	//Detach the entity after saving newUser 
	//user's mobile is  updated
	public void isFlush6(String email) {

		User user =  findUserByEmail(email);
		user.setMobile(new Date().toLocaleString());

		User newUser = newUser();
		userRepository.save(newUser);

		entityManager.detach(user);
	}

	
	//With @Transactional, detach the entity after saving newUser
    //user's mobile is not updated
	@Transactional
	public void isFlush7(String email) {

		User user =  findUserByEmail(email);
		user.setMobile(new Date().toLocaleString());

		User newUser = newUser();
		userRepository.save(newUser);

		entityManager.detach(user);
	}



	private User newUser() {
		User newUser = new User();
		newUser.setName("new User");
		newUser.setEmail(UUID.randomUUID().toString());
		newUser.setGender("male");
		newUser.setMobile(Long.valueOf(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)).toString());

		return newUser;
	}

	public void testFlush(String email) {
		isFlush3(email);
	}

}
