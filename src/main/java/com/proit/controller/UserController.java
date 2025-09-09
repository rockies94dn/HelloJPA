package com.proit.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.proit.common.CustomBeanUtils;
import com.proit.dao.UserDao;
import com.proit.dao.UserDaoImpl;
import com.proit.dto.UserDto;
import com.proit.model.User;

/**
 * Servlet implementation class UserController
 */
@WebServlet({ "/admin/users/create", "/admin/users/update", "/admin/users/delete", "/admin/users/list" })
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse respone)
			throws ServletException, IOException {

		String uri = request.getRequestURI();
		String viewType = "/admin/users/form.jsp";
		
		UserDao dao = new UserDaoImpl();

		try {
			if (uri.contains("/create") && request.getMethod().equalsIgnoreCase("POST")) {
				User user = new User();
				CustomBeanUtils.instance().populate(user, request.getParameterMap());
				dao.insert(user);
				request.setAttribute("user", user);
				viewType = "/admin/users/form.jsp";
				
			} else if (uri.contains("/update") && request.getMethod().equalsIgnoreCase("POST")) {
				User user = new User();
				CustomBeanUtils.instance().populate(user, request.getParameterMap());
				dao.update(user.getId(), user);
				request.setAttribute("user", user);
				viewType = "/admin/users/form.jsp";
			} else if (uri.contains("/delete")) {

			} else if (uri.contains("/list")) {
				
				List<User> users = new ArrayList<User>();
				users = dao.findAll();
				request.setAttribute("users", users);
				
			} else {

			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
		}

		RequestDispatcher rd = request.getRequestDispatcher(viewType);
		rd.forward(request, respone);
	}
}
