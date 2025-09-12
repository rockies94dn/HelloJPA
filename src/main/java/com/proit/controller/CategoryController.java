package com.proit.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.proit.common.CustomBeanUtils;
import com.proit.dao.CategoryDao;
import com.proit.dao.CategoryDaoImpl;
import com.proit.dao.UserDao;
import com.proit.dao.UserDaoImpl;
import com.proit.dto.LoginDto;
import com.proit.model.Category;
import com.proit.model.User;
import com.proit.model.UserStatus;

/**
 * Servlet implementation class CategoryController
 */
@WebServlet({ "/admin/categories/index", "/admin/categories/create", "/admin/categories/update", "/admin/categories/edit/*",
		"/admin/categories/reset", "/admin/categories/delete/*", "/admin/categories/delete", "/admin/categories/findbyname" })
public class CategoryController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("In service");
//		
//		 // --- 1. Kiểm tra session / cookie rememberMe ---
//	    HttpSession session = request.getSession(false);
//	    if (session == null || session.getAttribute("user") == null) {
//	        // Chưa có session → check cookie
//	        Cookie[] cookies = request.getCookies();
//	        if (cookies != null) {
//	            for (Cookie cookie : cookies) {
//	                if ("rememberMe".equals(cookie.getName())) {
//	                    String email = cookie.getValue();
//	                    if (email != null && !email.isEmpty()) {
//	                        UserDao userDao = new UserDaoImpl();
//	                        User user = userDao.findByEmail(email);
//	                        if (user != null && user.getStatus() == UserStatus.ACTIVE) {
//	                            // Tạo session mới
//	                            session = request.getSession(true);
//	                            LoginDto loginDto = new LoginDto();
//	                            try {
//									CustomBeanUtils.instance().copyProperties(loginDto, user);
//								} catch (IllegalAccessException | InvocationTargetException e) {
//									e.printStackTrace();
//								}
//	                            loginDto.setPassword(null);
//	                            loginDto.setRememberMe(true);
//	                            session.setAttribute("user", loginDto);
//	                            break; // cookie hợp lệ → thoát vòng for
//	                        }
//	                    }
//	                }
//	            }
//	        }
//
//	        // Nếu sau check cookie vẫn chưa có session → redirect login
//	        if (session == null || session.getAttribute("user") == null) {
//	            response.sendRedirect(request.getContextPath() + "/login");
//	            return;
//	        }
//	    }

		CategoryDao dao = new CategoryDaoImpl();
		request.removeAttribute("message");
		String textSearch = null;

		if (request.getRequestURI().contains("create")) {
			System.out.println("Create a category");

			Category entity = new Category();

			try {
				BeanUtils.populate(entity, request.getParameterMap());
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}

			dao.insert(entity);

			request.setAttribute("message", "The new category has been inserted!");

		} else if (request.getRequestURI().contains("update")) {
			// Tiến hành cập nhật
			System.out.println("Update a category");

			Category entity = new Category();

			try {
				BeanUtils.populate(entity, request.getParameterMap());
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}

			dao.update(entity.getCategoryId(), entity);

			request.setAttribute("category", entity);
			request.setAttribute("message", "Update successfully!");

		} else if (request.getRequestURI().contains("edit")) {
			// Hiển thị thông tin chi tiết được chọn lên form
			System.out.println("Edit a category");

			String path = request.getPathInfo();
			String id = path.substring(path.lastIndexOf("/") + 1);

			System.out.println(id);

			var found = dao.findById(Integer.parseInt(id));
			request.setAttribute("category", found);

		} else if (request.getRequestURI().contains("delete")) {
			// Xoá đối tượng được chọn
			System.out.println("Delete a category");
			String id = request.getParameter("categoryId");

			if (id == null || id.isEmpty()) {
				String path = request.getPathInfo();
				if (path != null) {
					id = path.substring(path.lastIndexOf("/") + 1);
				}
			}

			if (id != null && !id.isEmpty()) {
				dao.delete(Integer.parseInt(id));
				request.setAttribute("message", "Deleted!");
			} else {
				request.setAttribute("error", "The Id of category is null or empty");
			}
			
		} else if (request.getRequestURI().contains("findbyname")) {
			System.out.println("Find category by name");
			
			textSearch = request.getParameter("textSearch");
			
		} else {
			System.out.println("Reset");

			request.removeAttribute("category");
		}
		
		List<Category> list = null;
		if (textSearch == null || textSearch.isEmpty()) {
			list = dao.findAll();
		} else {
			list = dao.findByName(textSearch);
		}
		
		request.setAttribute("categories", list);

		RequestDispatcher rd = request.getRequestDispatcher("/admin/categories/index.jsp");
		rd.forward(request, response);
	}

}
