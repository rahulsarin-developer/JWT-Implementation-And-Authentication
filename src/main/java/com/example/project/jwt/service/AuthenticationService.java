package com.example.project.jwt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.project.jwt.model.AuthenticationRequest;
import com.example.project.jwt.model.AuthenticationResponse;
import com.example.project.jwt.model.Mst_User;
import com.example.project.jwt.repository.UserRepository;
import com.example.project.jwt.utility.JwtUtility;

@Service
public class AuthenticationService {

	@Autowired
	private JwtUtility jwtUtility;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UserRepository userRepository;
	
	public ResponseEntity<AuthenticationResponse> authenticateUser(AuthenticationRequest authenticationRequest) throws RuntimeException {
		
		List<Mst_User> user = null;
		String username = Optional.ofNullable(authenticationRequest.getUsername()).orElse(authenticationRequest.getEmail());
		if(Optional.ofNullable(username).isPresent()) {
			user = userRepository.findByUsernameOrEmail(authenticationRequest.getUsername(), authenticationRequest.getEmail());
			if (user.isEmpty()) {
				throw new RuntimeException("Data is not found");
			} else if(!user.get(0).getPassword().equalsIgnoreCase(authenticationRequest.getPassword())) {
				throw new RuntimeException("Wrong Password");
			}
		} else {
			throw new RuntimeException("Username or email id required..");
		}
		
		// to get user details from the db
		final String jwt = jwtUtility.generateToken(user);
		return ResponseEntity.ok(new AuthenticationResponse(username,jwt));	
	}
}
