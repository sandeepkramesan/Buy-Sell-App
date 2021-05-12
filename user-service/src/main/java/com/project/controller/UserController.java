package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.project.entity.MyUser;
import com.project.entity.Product;
import com.project.entity.request.UserCreateRequest;
import com.project.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * Access Through WebSite
	 */
	@GetMapping("/")
	public ModelAndView getHome(ModelAndView model) {

		model.setViewName("index");
		return model;
	}

	/**
	 * Create Members Through WebSite
	 */
	@PostMapping("/register/ui")
	public ResponseEntity<?> saveUserUI(@RequestParam String username, @RequestParam String password,
			@RequestParam String role) {
		UserCreateRequest user = new UserCreateRequest();
		user.setUsername(username);
		user.setPassword(password);
		user.setRole(role);

		MyUser byUsername = userService.getUserByUsername(user.getUsername());
		if (byUsername == null) {
			userService.saveUser(user);
			return new ResponseEntity<>("<h1>Member Created</h1><br>Carry on in the POSTMAN APP", HttpStatus.CREATED);
		}
		return new ResponseEntity<>("<h2>Username Already Exists</h2><br><a href='/'>Back to Registration</a>",
				HttpStatus.CREATED);

	}

	/**
	 * Access Through Postman App
	 */
	@PostMapping("/register")
	public ResponseEntity<?> saveUser(@RequestBody UserCreateRequest user) {

		MyUser byUsername = userService.getUserByUsername(user.getUsername());
		if (byUsername == null) {
			userService.saveUser(user);
			return new ResponseEntity<>("Member Created", HttpStatus.CREATED);
		}
		return new ResponseEntity<>("Username Already Exists", HttpStatus.CONFLICT);
	}

	/**
	 * Get A List Of All Members
	 */
	@GetMapping("/all")
	public List<MyUser> getAllUsers() {
		List<MyUser> allUsers = userService.getAllUsers();
//		return new ResponseEntity<>(allUsers, HttpStatus.OK);
		return allUsers;
	}

	/**
	 * Get Products Of Specific Users
	 */
	@GetMapping("/{name}/products")
	public ResponseEntity<?> getProductsOfUser(@PathVariable("name") String username) {

		ResponseEntity<Product[]> forEntity = restTemplate.getForEntity("http://PRODUCT-SERVICE/product/" + username,
				Product[].class);
		Product[] products = forEntity.getBody();

		if (products == null)
			return new ResponseEntity<>("No Product(s) for " + username, HttpStatus.OK);

		return new ResponseEntity<>(products, HttpStatus.OK);

	}

	/**
	 * Update Password Of User
	 */
	@PutMapping("/passwordupdate")
	public ResponseEntity<?> updateUser(@RequestBody UserCreateRequest user) {

		MyUser userByUsername = userService.getUserByUsername(user.getUsername());

		if (userByUsername != null) {
			MyUser updateUser = userService.updateUser(userByUsername);
			return new ResponseEntity<>(updateUser, HttpStatus.OK);

		}

		return new ResponseEntity<>("No user by that username", HttpStatus.OK);

	}
}
