package com.ax.repository;

import org.springframework.data.repository.CrudRepository;

import com.ax.model.UserModel;

public interface UserRepository extends CrudRepository<UserModel, Long>{
	UserModel findByUsername(String username);

}
