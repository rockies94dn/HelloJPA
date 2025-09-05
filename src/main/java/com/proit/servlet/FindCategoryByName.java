package com.proit.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.proit.dao.CategoryDao;
import com.proit.dao.CategoryDaoImpl;
import com.proit.model.Category;

@WebServlet("/FindCategoryByName")
public class FindCategoryByName extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CategoryDao dao = new CategoryDaoImpl();
		List<Category> list = dao.findByName("Clothes");
		if (list.isEmpty()) {
			System.out.println("Category not found!");
			return;
		}
		for (Category category : list) {
			System.out.println(category.getCategoryId() + " - " + category.getName());
		}
	}


}
