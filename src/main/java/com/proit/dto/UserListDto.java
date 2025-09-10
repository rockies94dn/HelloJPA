package com.proit.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserListDto {

	private Long id;

	private String name;

	private String email;

	private String statusStr;

	private String roleStr;
	
	private LocalDate createAt;
	
	private String createAtStr;
}
