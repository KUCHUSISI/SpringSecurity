package com.uvik.security.main;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil 
{
	private String SECRET_KEY="secret";
	
	public String extractUserName(String token)
	{
		return extractClaim(token,Claims::getSubject);		
	}
	
	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) 
	{
		final Claims claims=extractAllClaim(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaim(String token)
	{
		System.out.println(Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody());
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	public String generateToken(UserDetails userDetails)
	{
		Map<String,Object> claims=new HashMap<>();
		return createToken(claims,userDetails.getUsername());
	}
	
	private String createToken(Map<String,Object> claims,String subject)
	{
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+300000))
				.signWith(SignatureAlgorithm.HS256,SECRET_KEY).compact();
	}
	
	public boolean validToken(String token,UserDetails userDetails)
	{
		final String userName=extractUserName(token);
		return (userName.equals(userDetails.getUsername())&& !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) 
	{
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) 
	{
		return extractClaim(token, Claims::getExpiration);
	}
}
