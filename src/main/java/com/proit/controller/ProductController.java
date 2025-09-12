package com.proit.controller;

import jakarta.servlet.RequestDispatcher;
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
import org.apache.jasper.tagplugins.jstl.core.ForEach;

import com.proit.common.CustomBeanUtils;
import com.proit.common.FileUploadUtils;
import com.proit.common.PageInfor;
import com.proit.common.PaginationUtils;
import com.proit.common.ProductMapper;
import com.proit.dao.CategoryDao;
import com.proit.dao.CategoryDaoImpl;
import com.proit.dao.ProductDao;
import com.proit.dao.ProductDaoImpl;
import com.proit.dto.ProductDto;
import com.proit.dto.ProductViewDto;
import com.proit.model.Category;
import com.proit.model.Product;
import com.proit.model.ProductStatus;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet({ "/admin/products/index", "/admin/products/create", "/admin/products/update", "/admin/products/updateStatus", "/admin/products/edit/*",
		"/admin/products/reset", "/admin/products/delete", "/admin/products/delete/*", "/admin/products/search", "/admin/products/view/*",
		"/admin/products/listByCategory/*", "/admin/products/searchSort", "/admin/products/deleteByStatus", "/admin/products/searchByStatus" })
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
				if (part != null && part.getSize() > 0) {
					String fileName = FileUploadUtils.processImage("P" + entity.getId(), part, getServletContext());
					entity.setImageUrl(fileName);
					dao.update(entity.getId(), entity.getImageUrl());
				} else {
					// nếu không chọn gì thì lấy ảnh mặc định
					entity.setImageUrl("no-image.jpg");
				}
				System.out.println(entity.getImageUrl());

				dao.update(entity.getId(), entity.getImageUrl());
				dto.setId(entity.getId());
				request.setAttribute("products", dto);
				request.setAttribute("message", "New product has been inserted!");

			} else if (uri.contains("updateStatus") && request.getMethod().toUpperCase().contains("POST")) {
				System.out.println("updateStatus");
				String id = request.getParameter("id");
				String status = request.getParameter("status");

				if (id == null || id.isEmpty()) {
					throw new Exception("Product ID is required to update status.");
				}

				if (status == null || status.isEmpty()) {
					throw new Exception("Product status is required to update.");
				}
				
				ProductStatus productStatus = ProductStatus.valueOf(status);
				dao.updateStatusById(Long.parseLong(id), productStatus);
				
				request.setAttribute("status", productStatus);
				request.setAttribute("message", "Product status has been updated!");
				RequestDispatcher rd = request.getRequestDispatcher("/products/searchByStatus?status=" + productStatus);
				rd.forward(request, response);

				return;		

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
				} else {
					// nếu không chọn gì thì lấy ảnh mặc định
					imageUrl = "no-image.jpg";
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

			} else if (uri.contains("deleteByStatus")) {

				List<Product> products = dao.findByStatusOutOfStockAndDeleted();
				if (products.isEmpty()) {
					request.setAttribute("message", "No products found with status out of stock or deleted");
				} else {
					for (Product product : products) {
						if (product.getImageUrl() != null && !product.getImageUrl().isEmpty()) {
							FileUploadUtils.deleteFile(product.getImageUrl(), getServletContext());
						}
						dao.delete(product.getId());
					}
					request.setAttribute("message", "Products with out of stock or deleted status have been removed!");
				}

				viewType = "/admin/products/search.jsp";

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
					response.sendRedirect(request.getContextPath() + "/products/index");
					return;

				} else {
					request.setAttribute("error", "The Id of category is null or empty");
				}

			} else if (uri.contains("reset")) {
				System.out.println("reset");

			} else if (uri.contains("view")) {
				System.out.println("view");

				String path = request.getPathInfo();
				String id = null;
				if (path != null) {
					id = path.substring(path.lastIndexOf("/") + 1);
				}

				var productFound = dao.findById(Long.parseLong(id));
				if (productFound == null) {
					request.setAttribute("error", "Product not found with ID: " + id);
					request.getRequestDispatcher("/products/index").forward(request, response);
					return;
				}

				ProductViewDto dto = new ProductViewDto();
				BeanUtils.copyProperties(dto, productFound);
				dto.setCategoryName(productFound.getCategory().getName());

				request.setAttribute("product", dto);
				System.out.println(dto.getImageUrl());
				viewType = "/admin/products/view.jsp";

			} else if (uri.contains("searchByStatus")) {
				System.out.println("searchByStatus");

				String statusStr = request.getParameter("status");
				ProductStatus status = ProductStatus.valueOf(statusStr != null ? statusStr : "AVAILABLE");

				List<Product> products;
				PageInfor pageInfo = PaginationUtils.getPageFromRequest(request);
				int pageNo = pageInfo.getPageNo();
				int pageSize = pageInfo.getPageSize();
				Long totalCount;

				products = dao.findByStatus(status, pageNo, pageSize);
				totalCount = dao.countByStatus(status);
				int totalPages = (int) Math.ceil((double) totalCount / pageSize);

				request.setAttribute("totalPages", totalPages);
				request.setAttribute("totalCount", totalCount);
				request.setAttribute("page", pageNo + 1);
				request.setAttribute("size", pageSize);
				request.setAttribute("products", ProductMapper.toDtoList(products));
				request.setAttribute("status", status);

				viewType = "/admin/products/search-by-status.jsp";

			} else if (uri.contains("searchSort")) {
				System.out.println("searchSort");

				String name = request.getParameter("name");
				String priceStr = request.getParameter("price");

				name = name != null ? name.trim() : "";
				Double price = Double.parseDouble(priceStr != null ? priceStr : "0");

				List<Product> products;
				PageInfor pageInfo = PaginationUtils.getPageFromRequest(request);
				int pageNo = pageInfo.getPageNo();
				int pageSize = pageInfo.getPageSize();
				Long totalCount;

				products = dao.findByNameAndPrice(name, price, pageNo, pageSize);
				totalCount = dao.countByNameAndPrice(name, price);

				int totalPages = (int) Math.ceil((double) totalCount / pageSize);

				request.setAttribute("totalPages", totalPages);
				request.setAttribute("totalCount", totalCount);
				request.setAttribute("page", pageNo + 1);
				request.setAttribute("size", pageSize);
				request.setAttribute("products", ProductMapper.toDtoList(products));
				request.setAttribute("name", name);
				request.setAttribute("price", price);

				viewType = "/admin/products/search-sort.jsp";

			} else if (uri.contains("search")) {
				System.out.println("search");

				String keyword = request.getParameter("keyword");

				if (keyword == null || keyword.isEmpty()) {
					keyword = "";
				}

				PageInfor pageInfo = PaginationUtils.getPageFromRequest(request);

				int pageNo = pageInfo.getPageNo();
				int pageSize = pageInfo.getPageSize();
				List<Product> products = dao.findByName(keyword, pageNo, pageSize);
				Long totalCount = dao.countByName(keyword);
				int totalPages = (int) Math.ceil((double) totalCount / pageSize);

				request.setAttribute("totalPages", totalPages);
				request.setAttribute("totalCount", totalCount);
				request.setAttribute("page", pageNo + 1);
				request.setAttribute("size", pageSize);

				request.setAttribute("products", ProductMapper.toDtoList(products));
				request.setAttribute("keyword", keyword);

				viewType = "/admin/products/search.jsp";

			} else if (uri.contains("listByCategory")) {
				System.out.println("listByCategory");
				List<Product> products;
				PageInfor pageInfo = PaginationUtils.getPageFromRequest(request);
				int pageNo = pageInfo.getPageNo();
				int pageSize = pageInfo.getPageSize();
				Long totalCount;
				String categoryId = request.getParameter("categoryId");
				if (categoryId == null || categoryId.isEmpty()) {
					products = dao.findAll(pageNo, pageSize);
					totalCount = dao.countAll();
				} else {
					products = dao.findByCategory(Long.parseLong(categoryId), pageNo, pageSize);
					totalCount = dao.countByCategory(Long.parseLong(categoryId));
				}

				int totalPages = (int) Math.ceil((double) totalCount / pageSize);

				request.setAttribute("totalPages", totalPages);
				request.setAttribute("totalCount", totalCount);
				request.setAttribute("page", pageNo + 1);
				request.setAttribute("size", pageSize);
				request.setAttribute("selectedCategoryId", categoryId);
				request.setAttribute("categories", categoryDao.findAll());
				request.setAttribute("products", ProductMapper.toDtoList(products));

				viewType = "/admin/products/list-by-category.jsp";

			} else {
				System.out.println("Index");
				viewType = "/admin/products/list.jsp";
				List<Product> products = dao.findAll();

				request.setAttribute("products", ProductMapper.toDtoList(products));

			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
		}
		request.getRequestDispatcher(viewType).forward(request, response);

	}

}
