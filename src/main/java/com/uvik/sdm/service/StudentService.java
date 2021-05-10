package com.uvik.sdm.service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uvik.api.beans.DocDate;
import com.uvik.api.beans.SNames;
import com.uvik.api.beans.StudentData;
import com.uvik.api.beans.StudentDetails;
import com.uvik.entity.Student;
import com.uvik.repository.StudentRepository;

import org.slf4j.Logger;


//TODO Use SL4J . Generate debug logs for application.

@Service

public class StudentService
{
	@Autowired
	StudentRepository studentRepository;
	
	Logger logger=LoggerFactory.getLogger(StudentService.class);
	
	public SNames findStudentByFirstName(String FirstName)
	{
		logger.info(studentRepository.findByfirstName(FirstName).toString());
		return studentRepository.findByfirstName(FirstName);
	}
	public Student insertStudentData(Student data)
	{
		logger.info(studentRepository.save(data).toString());
		return studentRepository.save(data);
	}

	public Student findTheLatestDocumentByDate()
	{
		logger.info(studentRepository.findTopByOrderByCreatedDateDesc().toString());
		return studentRepository.findTopByOrderByCreatedDateDesc();
	}
	
	public List<SNames> findTop10StudentDetails()
	{
		logger.info(studentRepository.findFirst10ByOrderByFirstNameAsc().toString());
		return studentRepository.findFirst10ByOrderByFirstNameAsc().stream()
				.map(s -> new SNames(s.firstName,s.lastName)).collect(Collectors.toList()); //Nice
	}
	
	public List<StudentDetails> findStudentsHavingNameWithCharacters(String firstName)
	{
		return studentRepository.findByFirstNameStartingWith(firstName).stream()
				.map(s -> new StudentDetails(s.firstName,s.lastName)).collect(Collectors.toList());
	}

	public StudentData findStudentOrderByFirstNameDesc()
	{
		ModelMapper modelMapper=new ModelMapper();
		Student studentData = studentRepository.findFirstByOrderByFirstNameDesc();
		StudentData dto = modelMapper.map(studentData, StudentData.class);
		logger.info(dto.toString());
		return dto;
		
	}
	
	public StudentData findStudentOrderByFirstNameAsc()
	{
		ModelMapper modelMapper=new ModelMapper();
		Student studentData = studentRepository.findFirstByOrderByFirstNameAsc();
		StudentData dto = modelMapper.map(studentData, StudentData.class);
		logger.info(dto.toString());
		return dto;
	}
/*
 * the Below Methods Denotes the Student in last 3 Days
*/
	public List<DocDate> findTheStudentsCreatedInLast3Days()
	{
		Instant instantDate=Instant.now();
		return studentRepository.findByCreatedDateGreaterThanEqual(instantDate.minus(Duration.ofDays(3))).stream()
				  .map(s -> new DocDate(s.firstName,s.lastName,s.createdDate)).collect(Collectors.toList());
	}
	
	public List<DocDate> findTheDocsCreatedInLast10Hours()
	{
		  Instant date=Instant.now();
		  return studentRepository.findByCreatedDateGreaterThanEqual(date.minus(Duration.ofHours(10))).stream()
				  .map(s -> new DocDate(s.firstName,s.lastName,s.createdDate)).collect(Collectors.toList());
	}
//	public Binary getPhoto(String firstName) 
//	{
//		return studentRepository.findByPicture(firstName);
//	}
}