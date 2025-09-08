<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="pageTitle" value="Danh sách sản phẩm" />
<%@ include file="/admin/common/header.jsp"%>
<body>
	<c:url var="path" value="/products"></c:url>
	<main class="container mt-2">

		<c:if test="${message != null}">
			<div class="alert alert-success p-1">${message}</div>
		</c:if>
		<c:if test="${error != null}">
			<div class="alert alert-danger p-1">${error}</div>
		</c:if>

		<h2>Products</h2>
		<hr>

		<div class="d-flex justify-content-between mb-3">
			<div class="col-5">
				<form method="get" action="${path}/searchSort">
					<div class="input-group input-group-sm ">
						<input type="text" class="form-control"
							placeholder="Product name" name="name" value="${name}">
						<input type="number" class="form-control"
							placeholder="Product price" name="price" value="${price}">
						<button class="btn btn-outline-secondary" type="submit"
							id="button-search">
							<i class="fa fa-search" aria-hidden="true"></i> Search
						</button>
					</div>
				</form>
			</div>
			<div class="">
				<a href="${path}/create" class="btn btn-outline-primary btn-sm">
					<i class="fa fa-plus" aria-hidden="true"></i> Create
				</a>
			</div>
		</div>
		<hr />
		<c:choose>
			<c:when test="${empty products}">
				<div class="alert alert-info">No products found!</div>
			</c:when>
			<c:otherwise>
				<div class="alert alert-info">
					Found <strong>${totalCount}</strong> product(s).
				</div>
				<div class="table-responsive-sm">
					<table class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th scrope="col">No.</th>
								<th scrope="col">Image</th>
								<th scrope="col" class="w-50">Name</th>
								<th scrope="col">Quantity</th>
								<th scrope="col">Price</th>
								<th scrope="col">&nbsp;</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="product" items="${products}">
								<tr class="">
									<td scope="col">${product.id}</td>
									<td><img
										src="images/${product.imageUrl != null ? product.imageUrl : 'no-image.jpg'}"
										alt="" class="img-fluid" style="height: 80px" /></td>
									<td>${product.name}</td>
									<td>${product.quantity}</td>
									<td>${product.price}</td>
									<td><a href="${path}/view/${product.id}"
										class="btn btn-outline-primary btn-sm"> <i
											class="fa fa-eye" aria-hidden="true"></i> View
									</a> <a href="${path}/edit/${product.id}"
										class="btn btn-primary btn-sm"> <i class="fa fa-edit"
											aria-hidden="true"></i> Edit
									</a>
										<button type="button"
											onclick="confirmDelete('${path}/delete/${product.id}', '${product.name}')"
											class="btn btn-danger btn-sm">
											<i class="fa fa-remove" aria-hidden="true"></i> Delete
										</button></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="row">
					<div class="col">
						<nav aria-label="Page navigation example">
							<ul class="pagination">
								<li class="page-item"><a class="page-link"
									href="${path }/searchSort?name=${name}&price=${price}&page=1&size=${size == null ? 5 : size}"
									aria-label="First"> <span aria-hidden="true">&laquo;</span>
										<span class="sr-only">Previous</span>
								</a></li>
								<c:forEach var="i" begin="1" end="${totalPages}">
									<li class="page-item ${currentPage == i ? 'active' : ''}">
										<a class="page-link"
										href="${path }/searchSort?name=${name}&price=${price}&page=${i}">${i }</a>
									</li>
								</c:forEach>
								<li class="page-item"><a class="page-link"
									href="${path }/searchSort?name=${name}&price=${price}&page=${totalPages }&size=${size == null ? 5 : size}"
									aria-label="Last"> <span aria-hidden="true">&raquo;</span>
										<span class="sr-only">Next</span>
								</a></li>
							</ul>
						</nav>
					</div>
				</div>
			</c:otherwise>
		</c:choose>

	</main>

	<!-- 	define modal dialog to confirm Delete the product -->
	<%@ include file="/admin/common/delete-confirm-modal.jsp"%>

	<%@ include file="/admin/common/footer.jsp"%>
</body>
</html>