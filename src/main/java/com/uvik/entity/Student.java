package com.uvik.entity;

import java.time.Instant;

import javax.persistence.Id;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Student 
{
	@Id
	public ObjectId id;
	public String firstName;
	public String lastName;
	public String schoolName;
//	@Pattern(regexp = "^[A-Za-z0-9+]+([A-Za-z0-9]+)*@+[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$")
//	public String regularExpression;
//	public Binary picture;
	@CreatedDate
	public Instant createdDate;	
}