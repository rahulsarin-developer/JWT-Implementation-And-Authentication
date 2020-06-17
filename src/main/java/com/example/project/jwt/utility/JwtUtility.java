package com.example.project.jwt.utility;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.project.jwt.model.Mst_User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtility {
	
	@Value("${jwt.secret.token.key}")
	private String SECRET_KEY;
	
	public String generateToken(List<Mst_User> user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("username", user.get(0).getUsername());
		claims.put("email", user.get(0).getEmail());
		return createToken(claims, "data");		
	}

	private String createToken(Map<String, Object> claims, String subject) {
		System.out.println(SECRET_KEY);
		return Jwts.builder()
			.setClaims(claims)
			.setSubject(subject)
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // last value 10 indicates it is for 10 hour.
			.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
			.compact();
	}
	
	public Boolean validateToken(String token, String user) {
		final Claims claims = extractAllClaims(token);
		final String username = Optional.ofNullable(claims.get("username")).get().toString();
		final String email =  Optional.ofNullable(claims.get("email")).get().toString();
		System.out.println("userNAME:"+username);
		return (( username.equals(user) || email.equals(user)) && !isTokenExpired(token));
	}
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		claims.get("username");
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		System.out.println(SECRET_KEY);
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
}
 