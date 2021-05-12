package com.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.entity.MyUser;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Integer> {

	public MyUser findByUsername(String username);

}
