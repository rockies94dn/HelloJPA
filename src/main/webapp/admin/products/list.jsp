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

		<div class="row">
			<div class="col d-flex justify-content-end">
				<a href="${path}/create" class="btn btn-outline-primary btn-sm">
					<i class="fa fa-plus" aria-hidden="true"></i> Create
				</a>
			</div>
		</div>
		<hr>
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
							<td><img src="images/${product.imageUrl}" alt=""
								class="img-fluid" style="height: 80px" /></td>
							<td>${product.name}</td>
							<td>${product.quantity}</td>
							<td>${product.price}</td>
							<td><a href="${path}/view/${product.id}"
								class="btn btn-outline-primary btn-sm"> <i class="fa fa-eye"
									aria-hidden="true"></i> View
							</a> <a href="${path}/edit/${product.id}"
								class="btn btn-primary btn-sm"> <i class="fa fa-edit"
									aria-hidden="true"></i> Edit
							</a> <a href="${path}/delete/${product.id}"
								class="btn btn-danger btn-sm"> <i class="fa fa-remove"
									aria-hidden="true"></i> Delete
							</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="row">
			<div class="col">
				<nav aria-label="Page navigation example">
					<ul class="pagination">
						<li class="page-item"><a class="page-link" href="#"
							aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
								<span class="sr-only">Previous</span>
						</a></li>
						<li class="page-item"><a class="page-link" href="#">1</a></li>
						<li class="page-item"><a class="page-link" href="#">2</a></li>
						<li class="page-item"><a class="page-link" href="#">3</a></li>
						<li class="page-item"><a class="page-link" href="#"
							aria-label="Next"> <span aria-hidden="true">&raquo;</span> <span
								class="sr-only">Next</span>
						</a></li>
					</ul>
				</nav>
			</div>
		</div>
	</main>
	<%@ include file="/admin/common/footer.jsp"%>
</body>
</html>