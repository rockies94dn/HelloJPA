package com.proit.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.proit.dao.CategoryDao;
import com.proit.dao.CategoryDaoImpl;
import com.proit.model.Category;

/**
 * Servlet implementation class DeleteCategory
 */
@WebServlet("/DeleteCategory")
public class DeleteCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			CategoryDao dao = new CategoryDaoImpl();
			Category entity = new Category();
			entity.setCategoryId(1);
			dao.delete(entity.getCategoryId());
			System.out.println("Category has been deleted!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
