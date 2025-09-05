package com.proit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.proit.common.CustomBeanUtils;
import com.proit.common.FileUploadUtils;
import com.proit.dao.CategoryDao;
import com.proit.dao.CategoryDaoImpl;
import com.proit.dao.ProductDao;
import com.proit.dao.ProductDaoImpl;
import com.proit.dto.ProductDto;
import com.proit.model.Category;
import com.proit.model.Product;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet({ "/products/index", "/products/create", "/products/update", "/products/edit/*", "/products/reset",
		"/products/delete", "/products/delete/*", "/products/findbyname", "/products/paginate", "/products/view" })
public class ProductController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String uri = request.getRequestURI();

		ProductDao dao = new ProductDaoImpl();
		CategoryDao categoryDao = new CategoryDaoImpl();
		String viewType = "/admin/products/form.jsp";

		try {
			if (uri.contains("create") && request.getMethod().toUpperCase().contains("GET")) {
				System.out.println("create");

				List<Category> categories = categoryDao.findAll();
				request.setAttribute("categories", categories);
//				request.getRequestDispatcher(viewType).forward(request, response);

			} else if (uri.contains("create") && request.getMethod().toUpperCase().contains("POST")) {
				System.out.println("create post");

				List<Category> categories = categoryDao.findAll();
				request.setAttribute("categories", categories);

				ProductDto dto = new ProductDto();
				CustomBeanUtils.instance().populate(dto, request.getParameterMap());

				Product entity = new Product();

				CustomBeanUtils.instance().copyProperties(entity, dto);

				entity.setEnteredDate(new Date());

				Category category = new Category();
				category.setCategoryId(dto.getCategoryId());
				entity.setCategory(category);

				dao.insert(entity);

				Part part = request.getPart("image");
				String fileName = FileUploadUtils.processImage("P" + entity.getId(), part, getServletContext());
				entity.setImageUrl(fileName);
				System.out.println(entity.getImageUrl());

				dao.update(entity.getId(), entity.getImageUrl());
				dto.setId(entity.getId());
				request.setAttribute("products", dto);
				request.setAttribute("message", "New product has been inserted!");

			} else if (uri.contains("update") && request.getMethod().toUpperCase().contains("POST")) {
				System.out.println("update");

				// tạo DTO và nhận giá trị từ form
				ProductDto dto = new ProductDto();
				CustomBeanUtils.instance().populate(dto, request.getParameterMap());

				// tạo mới Product entity với ID từ DTO
				Product productFound = dao.findById(dto.getId());

				// lấy product hiện tại từ DB dựa trên ID
				if (productFound == null) {
					throw new Exception("Product not found with ID: " + dto.getId());
				}

				// lấy đường dẫn img của product
				String imageUrl = productFound.getImageUrl();

				// Kiểm tra xem có upload file mới không, nếu có thì set lại img của
				// product dựa trên file mới
				Part part = request.getPart("image");
				if (part != null && part.getSize() > 0) {
					imageUrl = FileUploadUtils.processImage("P" + dto.getId(), part, getServletContext());
				}

				// copy các thuộc tính của DTO sang product entity
				CustomBeanUtils.instance().copyProperties(productFound, dto);
				// người dùng không upload file mới có thể khiến img bị ghi đè thành
				// null, nên phải set lại đường dẫn
				productFound.setImageUrl(imageUrl);
				dto.setImageUrl(imageUrl);
				dto.setCategoryId(productFound.getCategory().getCategoryId());

				/*
				 * tạo category tạm để set cho product entity vì trong DTO, category chỉ
				 * có thông tin ID, còn trong entity nó là cả đối tượng Category nên
				 * không thể dùng trường categoryID trong DTO để copy trực tiếp vào
				 * entity
				 */
				Category category = new Category();
				category.setCategoryId(dto.getCategoryId());
				productFound.setCategory(category);

				// gọi method cập nhật đường dẫn ảnh
				dao.update(productFound.getId(), productFound.getImageUrl());

				// đổ lại dữ liệu vào select category
				List<Category> categories = categoryDao.findAll();
				request.setAttribute("categories", categories);

				request.setAttribute("product", dto);
				request.setAttribute("message", "Product has been updated!");

			} else if (uri.contains("edit")) {
				System.out.println("edit");

				List<Category> categories = categoryDao.findAll();
				request.setAttribute("categories", categories);

				String path = request.getPathInfo();
				String id = path.substring(path.lastIndexOf("/") + 1);

				var productFound = dao.findById(Long.parseLong(id));

				ProductDto dto = new ProductDto();
				BeanUtils.copyProperties(dto, productFound);

				dto.setCategoryId(productFound.getCategory().getCategoryId());
				request.setAttribute("product", dto);

				viewType = "/admin/products/form.jsp";

			} else if (uri.contains("delete")) {

				// Xoá đối tượng được chọn
				System.out.println("Delete a product");

				String id = request.getParameter("id");

				if (id == null || id.isEmpty()) {
					String path = request.getPathInfo();
					if (path != null) {
						id = path.substring(path.lastIndexOf("/") + 1);
					}
				}

				if (id != null && !id.isEmpty()) {
					var productFound = dao.findById(Long.parseLong(id));

					if (productFound == null) {
						request.setAttribute("error", "Product not found with ID: " + id);
						request.getRequestDispatcher("/products/index").forward(request, response);
						return;
					}
					// Xoá hình ảnh sản phẩm trong thư mục
					if (productFound.getImageUrl() != null && !productFound.getImageUrl().isEmpty()) {
						FileUploadUtils.deleteFile(productFound.getImageUrl(), getServletContext());
					}

					// Xoá trong DB
					dao.delete(productFound.getId());

					request.setAttribute("message", "Product has been deleted!");
					// Dẫn về lại trang danh sách sau khi xoá xong
					viewType = "/products/index";
					
				} else {
					request.setAttribute("error", "The Id of category is null or empty");
				}

			} else if (uri.contains("reset")) {
				System.out.println("reset");

			} else if (uri.contains("findbyname")) {
				System.out.println("findbyname");

			} else if (uri.contains("paginate")) {
				System.out.println("paginate");

			} else {
				System.out.println("Index");
				viewType = "/admin/products/list.jsp";
				List<Product> products = dao.findAll();
				request.setAttribute("products", products);

			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
		}
		request.getRequestDispatcher(viewType).forward(request, response);

	}

}
