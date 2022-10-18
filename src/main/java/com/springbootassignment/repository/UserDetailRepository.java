package com.springbootassignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springbootassignment.model.User;

public interface UserDetailRepository extends JpaRepository<User, Long> {
	List<User> findByUserName(String userName);

	List<User> findByCityContaining(String city);
}
