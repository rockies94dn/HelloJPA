package com.proit.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDto {

	private Long id;

	private String name;

	private String email;

	private String password;

	private LocalDate createAt;
	private String createAtStr;

	private LocalDate updateAt;
	private String updateAtStr;

	private LocalDate lastLoginAt;
	private String lastLoginAtStr;

	private String roleName;

	private String statusName;

}
