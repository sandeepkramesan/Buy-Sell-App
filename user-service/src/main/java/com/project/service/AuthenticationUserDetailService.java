package com.project.service;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.entity.MyUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationUserDetailService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		MyUser user = userService.getUserByUsername(username);

		if (user == null)
			throw new UsernameNotFoundException(username);

		return new User(user.getUsername(), user.getPassword(), getAuthorities(user.getRole()));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(String role) {
		return Arrays.asList(new SimpleGrantedAuthority(role));
	}
}
