package com.example.project.jwt.filter;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.project.jwt.service.MyUserDetailsService;
import com.example.project.jwt.utility.JwtUtility;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtUtility jwtUtility;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException, RuntimeException {
		
		final String authorizationHeader = request.getHeader("Authorization");
		String username = null;
		String jwt = null;
		
		if(Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
			jwt = authorizationHeader.substring(7);
			username = jwtUtility.extractUsername(jwt);
			//username = username.substring(1, username.length()-1);
					
			//System.out.println(request.getParameter("username").trim());
			//System.out.println(username.trim());
		}
		
		if(Objects.nonNull(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
			String user = Optional.ofNullable(request.getParameter("username")).orElse(request.getParameter("email"));
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(user);
			//UserDetails userDetails = this.myUserDetailsService.loadUserByUsername(user);
			//System.out.println("usr---"+userDetails.getUsername());
			if(jwtUtility.validateToken(jwt, user)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		
		filterChain.doFilter(request, response);
	}
}