package com.project.service;

import java.util.List;

import com.project.entity.MyUser;
import com.project.entity.request.UserCreateRequest;

public interface UserService {

	MyUser saveUser(UserCreateRequest user);

	List<MyUser> getAllUsers();

	MyUser getUserByUsername(String username);

	MyUser deleteUser(String username);

	MyUser updateUser(MyUser user);
}
