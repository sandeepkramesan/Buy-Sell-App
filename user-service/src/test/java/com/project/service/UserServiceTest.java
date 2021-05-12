	package com.project.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.project.dao.UserRepository;
import com.project.entity.MyUser;
import com.project.entity.request.UserCreateRequest;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private BCryptPasswordEncoder passwordEncoder;
	
	@InjectMocks
	private UserServiceImpl userService;
	
	private UserCreateRequest user1;
	private MyUser user2;
	private List<MyUser> allUsers = new ArrayList<>();
	
	@BeforeEach
	public void setUp() {
		user1 = new UserCreateRequest("user", "user", "USER");
		user2 = new MyUser(null, "user", "user", "USER");
		allUsers.add(user2);
	}
	
	@AfterEach
	public void tearDown() {
		userRepository.deleteAll();
		user1 = null;
		user2 = null;
		allUsers = null;
	}
	
	@Test
	void testGetUserByUsername() {
		
		when(userRepository.findByUsername(user2.getUsername())).thenReturn(user2);
		
		MyUser userByUsername = userService.getUserByUsername(user2.getUsername());
		assertEquals(user2.getUsername(), userByUsername.getUsername());
		
		verify(userRepository, times(1)).findByUsername(any());
	}

	@Test
	void testSaveUser() {

		when(userRepository.save(any())).thenReturn(user2);
		MyUser savedUser = userService.saveUser(user1);
		assertNotNull(savedUser);
		assertEquals(user1.getUsername(), savedUser.getUsername());
		
		verify(userRepository, times(1)).save(any());
	}

	@Test
	void testDeleteUser() {
		
		when(userRepository.findByUsername(user2.getUsername())).thenReturn(user2);
		
		userService.saveUser(user1);
		MyUser deletedUser = userService.deleteUser(user1.getUsername());
		assertEquals(user1.getUsername(), deletedUser.getUsername());
		
		verify(userRepository, times(1)).findByUsername(any());
		verify(userRepository, times(1)).deleteById(any());
		
	}

	@Test
	@Disabled
	void testUpdateUser() {
		
		when(userRepository.findByUsername(user1.getUsername())).thenReturn(user2);
		
		MyUser savedUser = userService.saveUser(user1);
		assertEquals(user1.getUsername(), savedUser.getUsername());

		MyUser updatedUser = userService.updateUser(user2);
		assertEquals(user1.getUsername(), updatedUser.getUsername());
		
		verify(userRepository, times(1)).findByUsername(any());
		verify(userRepository, times(1)).save(any());
	}

	@Test
	void testGetAllUsers() {

		when(userService.getAllUsers()).thenReturn(allUsers);
		
		userService.saveUser(user1);
		List<MyUser> users = userService.getAllUsers();
		assertEquals(allUsers, users);
		
		verify(userRepository, times(1)).save(any());
		verify(userRepository, times(1)).findAll();
	}

}
