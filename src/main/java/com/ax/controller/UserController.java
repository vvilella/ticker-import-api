package com.ax.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ax.model.UserModel;
import com.ax.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	UserRepository repository;

	@PostMapping("/auth")
	public ResponseEntity<String> auth(@RequestBody UserModel user) {

		ResponseEntity<String> response = new ResponseEntity<>("User Autenticated!", HttpStatus.OK);

		UserModel validatedUser = repository.findByUsername(user.getUsername());

		if (validatedUser == null || !(validatedUser.getPassword().equals(user.getPassword()))) {
			response = new ResponseEntity<>("Invalid Credentials", HttpStatus.UNAUTHORIZED);
		} else {
			if (!validatedUser.getActive()) {
				response = new ResponseEntity<>("Inactive User", HttpStatus.UNAUTHORIZED);
			}
		}

		return response;
	}

	@PostMapping("/user")
	public ResponseEntity<String> createUser(@RequestBody UserModel user) {

		if (user.getUsername() != null && user.getPassword() != null) {
			user.setActive(false);
			repository.save(user);
		}

		return new ResponseEntity<>("User Created!", HttpStatus.OK);
	}

}
