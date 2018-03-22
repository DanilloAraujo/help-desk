package com.example.helpdesk.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.example.helpdesk.entity.User;

@Component
public interface UserService {

	User findByEmail(String email);
	
	User createOrUpdate(User user);
	
	User findById(Long id);
	
	void delete(Long id);
	
	Page<User> findAll(int page, int count);
}
