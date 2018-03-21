package com.example.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.helpdesk.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmai(String email);
}
