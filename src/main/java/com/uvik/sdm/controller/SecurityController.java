package com.uvik.sdm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.uvik.sdm.service.StudentDetailsService;
import com.uvik.security.main.JwtUtil;
import com.uvik.security.model.AuthenticateRequest;
import com.uvik.security.model.AuthenticateResponse;

@CrossOrigin
@RestController
public class SecurityController
{

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private StudentDetailsService studentDetailsService;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@RequestMapping(value = "/authenticate",method = RequestMethod.POST)
	public ResponseEntity<?> createAuthentication(@RequestBody AuthenticateRequest authenticateRequest) throws Exception
	{
		try {
		authenticationManager.authenticate
		(new UsernamePasswordAuthenticationToken(authenticateRequest.getUserName(),authenticateRequest.getPassword()));
		}
		catch(BadCredentialsException e)
		{
			throw new Exception("incorrect userName & Password",e);
		}
		final UserDetails userDetails=studentDetailsService.loadUserByUsername(authenticateRequest.getUserName());

		final String jsonToken=jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticateResponse(jsonToken));
	}
}
