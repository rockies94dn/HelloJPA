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

@WebServlet("/InsertCategory")
public class InsertCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			CategoryDao dao = new CategoryDaoImpl();
			Category entity = new Category();
			entity.setName("Clothes");
			dao.insert(entity);
			System.out.println("New category has been inserted!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
