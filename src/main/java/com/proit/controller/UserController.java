package com.proit.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.proit.common.CustomBeanUtils;
import com.proit.common.PageInfor;
import com.proit.common.PaginationUtils;
import com.proit.common.UserMapper;
import com.proit.dao.UserDao;
import com.proit.dao.UserDaoImpl;
import com.proit.dto.UserDto;
import com.proit.dto.UserListDto;
import com.proit.model.User;
import com.proit.model.UserRole;
import com.proit.model.UserStatus;

/**
 * Servlet implementation class UserController
 */
@WebServlet({ "/admin/users/create", "/admin/users/update", "/admin/users/delete/*", "/admin/users/edit/*",
		"/admin/users/list" })
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String uri = request.getRequestURI();
		String viewType = "/admin/users/form.jsp";

		UserDao dao = new UserDaoImpl();

		try {
			if (uri.contains("/create") && request.getMethod().equalsIgnoreCase("GET")) {

				UserDto userDto = new UserDto();
				request.setAttribute("user", userDto);
				viewType = "/admin/users/form.jsp";

			} else if (uri.contains("/create") && request.getMethod().equalsIgnoreCase("POST")) {

				createUser(request, dao);
				request.setAttribute("message", "New user has been created!");

			} else if (uri.contains("/update")) {

				viewType = updateUser(request, dao);

			} else if (uri.contains("/edit")) {

				viewType = editUser(request, uri, dao);

			} else if (uri.contains("/delete/*")) {

				viewType = deleteUser(request, uri, dao);
			} else if (uri.contains("/delete")) {

				viewType = deleteUser(request, uri, dao);

			} else if (uri.contains("/list")) {
				viewType = usersList(request, dao);
			} else {

			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
		}

		RequestDispatcher rd = request.getRequestDispatcher(viewType);
		rd.forward(request, response);
	}

	private String deleteUser(HttpServletRequest request, String uri, UserDao dao) throws Exception {
		String viewType;
		String idStr = uri.substring(uri.lastIndexOf("/") + 1);
		if (idStr != null && !idStr.isEmpty()) {
			Long id = Long.parseLong(idStr);
			dao.delete(id);
			request.setAttribute("message", "User has been removed.");
			viewType = usersList(request, dao);
		} else {
			throw new Exception("User ID is required for deletion.");
		}
		return viewType;
	}

	private String updateUser(HttpServletRequest request, UserDao dao)
			throws IllegalAccessException, InvocationTargetException {
		String viewType;
		UserDto userDto = new UserDto();
		CustomBeanUtils.instance().populate(userDto, request.getParameterMap());
		User user = UserMapper.dtoToEntity(userDto);
		BeanUtils.copyProperties(user, userDto);
		dao.update(userDto.getId(), user);
		request.setAttribute("message", "User has been updated!");
		viewType = usersList(request, dao);
		return viewType;
	}

	private String usersList(HttpServletRequest request, UserDao dao) {
		String viewType;
		var users = dao.findAll();

		List<UserListDto> dtos = UserMapper.entitiesToListDtos(users);

		request.setAttribute("users", dtos);

		PageInfor pageInfo = PaginationUtils.getPageFromRequest(request);
		int pageNo = pageInfo.getPageNo();
		int pageSize = pageInfo.getPageSize();
		Long totalCount = dao.countAllUsers();
		int totalPages = (int) Math.ceil((double) totalCount / pageSize);

		request.setAttribute("totalPages", totalPages);
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("page", pageNo + 1);
		request.setAttribute("size", pageSize);
		request.setAttribute("message", "Found " + totalCount + " users.");

		viewType = "/admin/users/list.jsp";
		return viewType;
	}

	private String editUser(HttpServletRequest request, String uri, UserDao dao) throws Exception {
		String viewType;
		String idStr = uri.substring(uri.lastIndexOf("/") + 1);
		if (idStr != null && !idStr.isEmpty()) {
			Long id = Long.parseLong(idStr);
			User user = dao.findById(id);
			if (user == null) {
				throw new Exception("User not found with ID: " + id);
			}
			UserDto userDto = UserMapper.toDto(user);
			request.setAttribute("user", userDto);

		}
		viewType = "/admin/users/form.jsp";
		return viewType;
	}

	private void createUser(HttpServletRequest request, UserDao dao) {
		UserDto userDto = new UserDto();
		try {
			CustomBeanUtils.instance().populate(userDto, request.getParameterMap());

		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}

		User entity = new User();
		try {
			CustomBeanUtils.instance().copyProperties(entity, userDto);
			entity.setRole(UserRole.valueOf(userDto.getRoleStr()));
			entity.setStatus(UserStatus.ACTIVE);
			dao.insert(entity);
			request.setAttribute("message", "New user has been inserted!");
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}

	}
}
