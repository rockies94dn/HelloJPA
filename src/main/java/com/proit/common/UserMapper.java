package com.proit.common;

import java.lang.reflect.InvocationTargetException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.proit.dto.UserDetailDto;
import com.proit.dto.UserDto;
import com.proit.dto.UserListDto;
import com.proit.model.User;
import com.proit.model.UserRole;
import com.proit.model.UserStatus;

public class UserMapper {
	public static UserDto toDto(User entity) {
		if (entity == null) {
			return null;
		}
		UserDto dto = new UserDto();
		try {
			BeanUtils.copyProperties(dto, entity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		dto.setRoleStr(entity.getRole() != null ? entity.getRole().name() : null);
		return dto;
	}

	public static User dtoToEntity(UserDto dto) {
		if (dto == null) {
			return null;
		}
		User entity = new User();
		try {
			BeanUtils.copyProperties(entity, dto);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		entity.setRole(UserRole.valueOf(dto.getRoleStr()));
		return entity;
	}

	public static UserListDto toListDto(User entity) {
		if (entity == null) {
			return null;
		}
		UserListDto dto = new UserListDto();
		try {
			BeanUtils.copyProperties(dto, entity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		dto.setRoleStr(entity.getRole() != null ? entity.getRole().name() : null);
		dto.setStatusStr(entity.getStatus() != null ? entity.getStatus().name() : null);
		dto.setCreateAtStr(
				entity.getCreateAt() != null ? entity.getCreateAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
						: "");
		return dto;
	}

	public static User listDtoToEntity(UserListDto dto) {
		if (dto == null) {
			return null;
		}
		User entity = new User();
		try {
			BeanUtils.copyProperties(entity, dto);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		entity.setRole(UserRole.valueOf(dto.getRoleStr()));
		entity.setStatus(UserStatus.valueOf(dto.getStatusStr()));
		return entity;
	}

	public static UserDetailDto toDetailDto(User entity) {
		if (entity == null) {
			return null;
		}
		UserDetailDto dto = new UserDetailDto();
		try {
			BeanUtils.copyProperties(dto, entity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		dto.setRoleName(entity.getRole() != null ? entity.getRole().name() : "");
		dto.setStatusName(entity.getStatus() != null ? entity.getStatus().name() : "");
		dto.setCreateAtStr(entity.getCreateAt() != null ? entity.getCreateAt().toString() : "");
		dto.setLastLoginAtStr(entity.getLastLoginAt() != null ? entity.getLastLoginAt().toString() : "");
		dto.setUpdateAtStr(entity.getUpdateAt() != null ? entity.getUpdateAt().toString() : "");
		return dto;
	}

	public static List<UserDto> entitiesToDtos(List<User> entities) {
		List<UserDto> dtos = new ArrayList<UserDto>();
		for (User entity : entities) {
			UserDto dto = new UserDto();
			dto = UserMapper.toDto(entity);
			dtos.add(dto);
		}
		return dtos;
	}

	public static List<UserListDto> entitiesToListDtos(List<User> entities) {
		List<UserListDto> dtos = new ArrayList<UserListDto>();
		for (User entity : entities) {
			UserListDto dto = new UserListDto();
			dto = UserMapper.toListDto(entity);
			dtos.add(dto);
		}
		return dtos;
	}

	public static List<User> dtosToEntities(List<UserDto> dtos) {
		List<User> entities = new ArrayList<User>();
		for (UserDto dto : dtos) {
			User entity = new User();
			entity = UserMapper.dtoToEntity(dto);
			entities.add(entity);
		}
		return entities;
	}

	public static List<User> listDtosToEntities(List<UserListDto> dtos) {
		List<User> entities = new ArrayList<User>();
		for (UserListDto dto : dtos) {
			User entity = new User();
			entity = UserMapper.listDtoToEntity(dto);
			entities.add(entity);
		}
		return entities;
	}
}
