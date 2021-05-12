package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.dao.UserRepository;
import com.project.entity.MyUser;
import com.project.entity.request.UserCreateRequest;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public MyUser getUserByUsername(String username) {
		MyUser user = null;
		user = userRepository.findByUsername(username);
		return user;
	}

	@Override
	public MyUser saveUser(UserCreateRequest userCreate) {
		MyUser user = new MyUser();
		user.setUsername(userCreate.getUsername());
		user.setPassword(passwordEncoder.encode(userCreate.getPassword()));
		user.setRole(userCreate.getRole());
		return userRepository.save(user);
	}

	@Override
	public MyUser deleteUser(String username) {
		MyUser user = null;
		user = userRepository.findByUsername(username);
		if (user != null) {
			userRepository.deleteById(user.getUserId());
		}
		return user;
	}

	@Override
	public MyUser updateUser(MyUser user) {
		MyUser updatedUser = null;
		MyUser findByUsername = userRepository.findByUsername(user.getUsername());
		if (findByUsername != null) {
			MyUser getUser = findByUsername;
//			getUser.setUserId(user.getUserId());
			getUser.setPassword(passwordEncoder.encode(user.getPassword()));
			updatedUser = userRepository.save(getUser);
			return updatedUser;
		}
		return updatedUser;

	}

	@Override
	public List<MyUser> getAllUsers() {
		return userRepository.findAll();
	}

}
