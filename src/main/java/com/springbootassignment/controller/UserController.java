package com.springbootassignment.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springbootassignment.model.User;
import com.springbootassignment.repository.UserDetailRepository;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserDetailRepository userDetailRepository;

	@GetMapping("/getUserDetail")
	public ResponseEntity<List<User>> getAllUsers(
			@ApiParam(value = "user name of the user you want to retrieve", required = false) @RequestParam(required = false) String userName) {

		try {
			List<User> userDetailsList = new ArrayList<User>();

			if (userName == null)
				userDetailRepository.findAll().forEach(userDetailsList::add);
			else
				userDetailRepository.findByUserName(userName).forEach(userDetailsList::add);

			if (userDetailsList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(userDetailsList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/updateUser/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User userDetail) {
		Optional<User> userData = userDetailRepository.findById(id);

		if (userData.isPresent()) {
			User user = userData.get();
			user.setUserName(userDetail.getUserName());
			user.setCity(userDetail.getCity());
			user.setPassword(userDetail.getPassword());
			return new ResponseEntity<>(userDetailRepository.save(user), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/deleteUser/{id}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
		try {
			userDetailRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
