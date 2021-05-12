package com.project.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.entity.MyUser;
import com.project.entity.request.UserCreateRequest;
import com.project.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;

	@Autowired
	private MockMvc mockMvc;

	private UserCreateRequest user1;
	private MyUser user2;
	private List<MyUser> allUsers = new ArrayList<>();

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

		user1 = new UserCreateRequest("user", "user", "USER");
		user2 = new MyUser(null, "user", "user", "USER");
		allUsers.add(user2);
	}

	@AfterEach
	public void tearDown() {
		user1 = null;
		user2 = null;
		allUsers = null;
	}

	@Test
	void testSaveUserAndReturnSavedDetails() throws Exception {
		when(userService.saveUser(user1)).thenReturn(user2);
		
		mockMvc.perform(post("/user/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(convertStringtoJSON(user1)))
			.andExpect(status().isCreated())
			.andDo(MockMvcResultHandlers.print());
		
		verify(userService, times(1)).saveUser(user1);
	}

	@Test
	void testGetAllUsersAndReturnList() throws Exception {
		when(userService.getAllUsers()).thenReturn(allUsers);
		
		mockMvc.perform(get("/user/all")
				.contentType(MediaType.APPLICATION_JSON)
				.content(convertStringtoJSON(allUsers)))
			.andExpect(status().isOk())
			.andDo(MockMvcResultHandlers.print());
		
		verify(userService, times(1)).getAllUsers();
	}


	@Test
	void testUpdateUser() throws Exception {
		
		mockMvc.perform(put("/user/passwordupdate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(convertStringtoJSON(user2)))
			.andExpect(status().isOk())
			.andDo(MockMvcResultHandlers.print());
		
		verify(userService, times(1)).getUserByUsername(any());
	}
	
	public static String convertStringtoJSON(Object object) throws Exception {
		try {
			return new ObjectMapper().writeValueAsString(object);
			
		}catch(Exception e) {
			throw new Exception();
		}
	}


}
