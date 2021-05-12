package com.project.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.dao.UserRepository;
import com.project.entity.MyUser;

@SpringBootTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	private MyUser user;

	@BeforeEach
	public void setUp() {
		user = new MyUser(null, "admin", "admin", "ADMIN");
	}

	@AfterEach
	public void tearDown() {
		userRepository.deleteAll();
		user = null;
	}

	@Test
	public void saveUserReturnSavedDetails() {

		MyUser savedUser = userRepository.save(user);
		assertNotNull(savedUser);
		assertEquals(user.getUsername(), savedUser.getUsername());
		assertEquals(user.getPassword(), savedUser.getPassword());
		assertEquals(user.getRole(), savedUser.getRole());
	}
	
	@Test
	public void getUserByUsernameReturnUserDetails() {
		
		userRepository.save(user);
		MyUser findByUsername = userRepository.findByUsername(user.getUsername());
		assertNotNull(findByUsername);
		assertEquals(user.getUsername(), findByUsername.getUsername());
		assertEquals(user.getPassword(), findByUsername.getPassword());
		assertEquals(user.getRole(), findByUsername.getRole());
	}
	
	@Test
	public void deleteUserReturnMovie() {
		
		userRepository.save(user);
		
		MyUser findByUsername = userRepository.findByUsername(user.getUsername());
		userRepository.deleteById(findByUsername.getUserId());
		
		MyUser findByUsernameAfterDelete = userRepository.findByUsername(user.getUsername());
		assertEquals(null, findByUsernameAfterDelete);
	}
	
	@Test
	public void getAllUsersReturnList() {

		userRepository.save(user);

		List<MyUser> findAll = userRepository.findAll();
		
		assertEquals(1, findAll.size());
		assertEquals(user.getUsername(), findAll.get(0).getUsername());
	}

}
