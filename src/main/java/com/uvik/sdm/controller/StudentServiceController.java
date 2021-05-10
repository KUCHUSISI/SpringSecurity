package com.uvik.sdm.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.uvik.api.authenticate.AuthenticateRequest;
import com.uvik.api.authenticate.AuthenticateResponse;
import com.uvik.api.beans.DocDate;
import com.uvik.api.beans.SNames;
import com.uvik.api.beans.StudentData;
import com.uvik.api.beans.StudentDetails;
import com.uvik.entity.Student;
import com.uvik.sdm.service.StudentService;
import com.uvik.security.main.JwtUtil;
import com.uvik.security.main.MyUserDetailsService;

@CrossOrigin
@RestController
@RequestMapping(path = "/student")
public class StudentServiceController 
{
	@Autowired(required = true)
	StudentService studentService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService studentDetailsService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	@GetMapping("/")
	@ResponseStatus(value = HttpStatus.OK)
	public String getStudent() {
		return "<h1>Hello</h1>";
	}
	
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
	
	@GetMapping("/fetchByFirstName/{firstName}")
	@ResponseStatus(value = HttpStatus.OK)
	public SNames getStudentByFirstName(@PathVariable("firstName") String firstName)
	{
		return studentService.findStudentByFirstName(firstName);
	}
	
	@PostMapping("/insertData")
	@ResponseStatus(value=HttpStatus.CREATED)
	public Student saveStudentData(@RequestBody Student model)
	{
		return studentService.insertStudentData(model);
	}
	@GetMapping("/fetchTheLatestDocumentByDate")
	@ResponseStatus(value=HttpStatus.OK)
	public Student findTheLatestDocumentByDate()
	{
		return studentService.findTheLatestDocumentByDate();
	}
	
	@GetMapping("/fetchTop10StudentDeatils")
	@ResponseStatus(value=HttpStatus.OK)
	public List<SNames> findTop10StudentDeatils()
	{
		return studentService.findTop10StudentDetails();
	}

	@GetMapping("/fetchFirstOrderByFirstNameDesc")
	@ResponseStatus(value=HttpStatus.OK)
	public StudentData findFirstStudentOrderByFirstNameDesc()
	{
		return studentService.findStudentOrderByFirstNameDesc();
	}

	@GetMapping("/fetchFirstByOrderByFirstNameAsc")
	@ResponseStatus(value=HttpStatus.OK)
	public StudentData findFirstStudentOrderByFirstNameAsc()
	{
		return studentService.findStudentOrderByFirstNameAsc();
	}
	
	@GetMapping("/fetchByFirstnameStartingWith/{firstName}")
	@ResponseStatus(value=HttpStatus.OK)
	public List<StudentDetails> findByFirstnameStartingWith(@PathVariable("firstName") String firstName)
	{
		return studentService.findStudentsHavingNameWithCharacters(firstName);
	}

	@GetMapping("/fetchCreatedInLast10Hrs")
	@ResponseStatus(value=HttpStatus.OK)
	public List<DocDate> getDocumentsCreatedInLast10Hrs()
	{
		return studentService.findTheDocsCreatedInLast10Hours();
	}
	
	@GetMapping("/fetchStudentCreatedInLast3Days")
	@ResponseStatus(value=HttpStatus.OK)
	public List<DocDate> findTheStudentsCreatedInLast3Days()
	{
		return studentService.findTheStudentsCreatedInLast3Days();
	}
	/*
	 * Inserteing picture and retriving picture
	 * */
//	@GetMapping("/photos/{firstName}")
//	public Binary getPhoto(@PathVariable("firstName") String firstName) 
//	{
//	    Binary photo = studentService.getPhoto(firstName);
//	    return photo;
//	}
}