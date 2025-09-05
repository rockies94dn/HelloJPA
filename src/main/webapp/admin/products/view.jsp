<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="pageTitle" value="Biểu mẫu chi tiết" />
<%@ include file="/admin/common/header.jsp"%>

<body>
	<c:url var="path" value="/products"></c:url>
	<h2 style="justify-self: center">Product Details</h2>
	<hr />
	<main class="container mt-2">
		<c:if test="${message != null}">
			<div class="alert alert-success">${message}</div>
		</c:if>
		<c:if test="${error != null}">
			<div class="alert alert-danger">${error}</div>
		</c:if>
		<div class="container">
			<div class="card mx-auto my-4">
				<div class="card-header">
					<h5 class="card-title">Product Details</h5>
				</div>
				<div class="card-body">
					<div class="row">
						<div class="col-6 border-end d-flex justify-content-center">
							<img
								src="images/${product.imageUrl != null ? product.imageUrl : 'no-image.jpg'}"
								class="img-fluid w-75" alt="">
						</div>
						<div class="col-6">
							<p class="card-text">
								<strong>Product ID:</strong> ${product.id}
							</p>
							<p class="card-text">
								<strong>Name:</strong> ${product.name}
							</p>
							<p class="card-text">
								<strong>Price:</strong> ${product.price}
							</p>
							<p class="card-text">
								<strong>Quantity:</strong> ${product.quantity}
							</p>
							<p class="card-text">
								<strong>Status:</strong> ${product.status}
							</p>
							<p class="card-text">
								<strong>Category:</strong> ${product.categoryName}
							</p>
						</div>
					</div>
					<div class="row my-3">
						<p class="card-text">
							<strong>Description:</strong> ${product.description}
						</p>
					</div>
				</div>
				<div class="col d-flex flex-row justify-content-between">
					<div
						class="card-footer text-muted col d-flex flex-row justify-content-between">
						<a href="${path}/index" class="btn btn-primary btn-sm">Back to
							products list</a>
						<div class="">
							<a href="${path}/edit/${product.id}"
								class="btn btn-primary btn-sm"> <i class="fa fa-edit"
								aria-hidden="true"></i> Edit
							</a>
							<button type="button"
								onclick="confirmDelete('${path}/delete/${product.id}', '${product.name}')"
								class="btn btn-danger btn-sm">
								<i class="fa fa-remove" aria-hidden="true"></i> Delete
							</button>
						</div>
					</div>
				</div>


			</div>
		</div>

	</main>
	
	<%@ include file="/admin/common/delete-confirm-modal.jsp"%>

	<%@ include file="/admin/common/footer.jsp"%>
</body>

</html>

</html>