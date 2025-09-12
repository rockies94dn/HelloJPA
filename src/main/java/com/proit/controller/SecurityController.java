package com.proit.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpCookie;

import com.proit.common.CustomBeanUtils;
import com.proit.dao.UserDao;
import com.proit.dao.UserDaoImpl;
import com.proit.dto.LoginDto;
import com.proit.model.User;
import com.proit.model.UserStatus;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "SecurityController", urlPatterns = { "/login", "/logout", "/register", "/forgot-password" })
public class SecurityController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String uri = request.getRequestURI();
		String viewType = "/securities/login.jsp";
		UserDao userDao = new UserDaoImpl();

		try {
			if (uri.contains("login") && request.getMethod().equalsIgnoreCase("GET")) {

				if (!showLogin(request, response)) {
					return;
				}

			} else if (uri.contains("login") && request.getMethod().equalsIgnoreCase("POST")) {
				processLogin(request, response, userDao);
				return;
			} else if (uri.contains("logout")) {

			} else if (uri.contains("register")) {

			} else if (uri.contains("forgot-password")) {

			} else {

				throw new Exception("Invalid request");
			}

		} catch (

		Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
		}

		RequestDispatcher rd = request.getRequestDispatcher(viewType);
		rd.forward(request, response);
	}

	private boolean showLogin(HttpServletRequest request, HttpServletResponse response)
			throws IOException, IllegalAccessException, InvocationTargetException {

		// 1. Kiểm tra session hiện tại
		HttpSession session = request.getSession(false);
		System.out.println(session != null);
		if (session != null && session.getAttribute("user") != null) {
			response.sendRedirect(request.getContextPath() + "/categories/index");
			return false;
		}
		// 2. Nếu chưa có session -> kiểm tra cookie Remember Me
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				System.out.println("Cookie: " + cookie.getName() + "=" + cookie.getValue());
				if ("rememberMe".equals(cookie.getName())) {
					String email = cookie.getValue();
					System.out.println(email);

					if (email != null && !email.isEmpty()) {
						UserDao userDao = new UserDaoImpl();
						User user = userDao.findByEmail(email);

						if (user != null && user.getStatus() == UserStatus.ACTIVE) {
							// Tạo mới session và set LoginDto
							HttpSession newSession = request.getSession(true);

							LoginDto loginDto = new LoginDto();
							CustomBeanUtils.instance().copyProperties(loginDto, user);
							loginDto.setPassword(null);
							loginDto.setRememberMe(true);

							newSession.setAttribute("user", loginDto);

							response.sendRedirect(request.getContextPath() + "/categories/index");
							return false;
						}
					}
				}
			}
		}

		// 3. Không có session + không có cookie -> cho phép hiển thị login.jsp
		return true;
	}

	private void processLogin(HttpServletRequest request, HttpServletResponse response, UserDao userDao)
			throws Exception {

		// Lấy dữ liệu từ form
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String remember = request.getParameter("rememberMe"); // checkbox

		// Kiểm tra DB
		User user = userDao.findByEmailAndPassword(email, password);

		if (user == null) {
			throw new Exception("Invalid email or password");
		}

		if (user.getStatus() == UserStatus.DEACTIVATED || user.getStatus() == UserStatus.DELETED) {
			throw new Exception("User currently is deactivated or deleted!");
		}

		// Chuyển User → LoginDto (session dùng LoginDto)
		LoginDto loginDto = new LoginDto();
		CustomBeanUtils.instance().copyProperties(loginDto, user);
		loginDto.setPassword(null); // Không lưu password vào session

		// Tạo session
		HttpSession session = request.getSession(true);
		session.setAttribute("user", loginDto);

		// Remember Me
		if ("true".equals(remember)) { // nếu checkbox được tick
			Cookie cookie = new Cookie("rememberMe", loginDto.getEmail());
			cookie.setMaxAge(60 * 60 * 24 * 30); // 30 ngày
			cookie.setPath("/"); // áp dụng cho toàn bộ app
			System.out.println(cookie.getValue());
			response.addCookie(cookie);
		} else {
			// Xóa cookie cũ nếu có
			Cookie cookie = new Cookie("rememberMe", "");
			cookie.setMaxAge(0);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
		System.out.println("rememberMe param = " + remember);

		// Chuyển hướng
		response.sendRedirect(request.getContextPath() + "/categories/index");
	}

}
