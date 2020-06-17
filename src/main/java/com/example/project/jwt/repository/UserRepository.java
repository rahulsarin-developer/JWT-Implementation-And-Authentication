package com.example.project.jwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.jwt.model.Mst_User;

public interface UserRepository extends JpaRepository<Mst_User, Integer> {
	
	List<Mst_User> findByUsernameOrEmail(String username, String email);
}
