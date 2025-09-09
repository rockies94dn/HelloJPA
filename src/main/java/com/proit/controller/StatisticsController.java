package com.proit.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.proit.dao.ProductDao;
import com.proit.dao.ProductDaoImpl;
import com.proit.model.ProductsInCategoryStatistics;

@WebServlet({ "/statistics/productsByCategory", "/statistics/productStatusSummary" })
public class StatisticsController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse respone)
			throws ServletException, IOException {
		String uri = request.getRequestURI();
		String viewType = "/admin/statistics/products-in-category.jsp";
		try {
			ProductDao dao = new ProductDaoImpl();
			if (uri.contains("productsByCategory")) {
				System.out.println("productByCategory");
				List<ProductsInCategoryStatistics> statistics = dao.countProductsByCategory();

				request.setAttribute("data", statistics);

			} else if (uri.contains("productStatusSummary")){
				System.out.println("productStatusSummary");
				var statistics = dao.countProductByStatus();
				request.setAttribute("data", statistics);
				viewType = "/admin/statistics/products-status-summary.jsp";
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
		}

		RequestDispatcher rd = request.getRequestDispatcher(viewType);
		rd.forward(request, respone);

	}

}
