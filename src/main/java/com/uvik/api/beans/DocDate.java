package com.uvik.api.beans;


import java.time.Instant;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocDate
{
	public String firstName;
	public String lastName;
	public Instant createdDateInstant; 
}