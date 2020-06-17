package com.example.project.jwt.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.jwt.model.AuthenticationRequest;
import com.example.project.jwt.model.User;
import com.example.project.jwt.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/login")
public class JwtController {
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		
		return authenticationService.authenticateUser(authenticationRequest);
	}
	
	@GetMapping("/getUserInformation")
	public User getUserDetails(@RequestParam(name="username", required=false) final String username, @RequestParam(name="email", required=false) final String email) {
		return new User(username, email);
	}
	
	@GetMapping("/validate")
	public Map<String, String> getMapping(@RequestParam(name="username", required=false) final String username, @RequestParam(name="email", required=false) final String email) {
		Map<String, String> response = new HashMap<>();
		response.put("status", "success");
		return response;
	}
}