<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="pageTitle" value="Biểu mẫu chi tiết" />
<%@ include file="/admin/common/header.jsp"%>

<body>
	<c:url var="path" value="/products"></c:url>
	<main class="container mt-2">
		<c:if test="${message != null}">
			<div class="alert alert-success">${message}</div>
		</c:if>
		<c:if test="${error != null}">
			<div class="alert alert-danger">${error}</div>
		</c:if>
		<form method="post" enctype="multipart/form-data">
			<div class="row">
				<div class="col col-6">
					<div class="mb-3">
						<label for="categoryId" class="form-label">Category</label> <select
							class="form-select form-select-sm" name="categoryId"
							id="categoryId">
							<option selected>Select one</option>
							<c:forEach var="item" items="${categories}">
								<option value="${item.categoryId}"
									${product.categoryId == item.categoryId ? 'selected' : ''}>${item.name}</option>
							</c:forEach>

						</select>
					</div>

				</div>
			</div>
			<div class="row mt-4">
				<div class="col-6">
					<div class="mb-3">
						<label for="" class="form-label">Product ID</label> <input
							type="number" class="form-control form-control-sm"
							name="id" id="id" placeholder="Product ID" readonly
							value="${product.id}" />
					</div>
					<div class="col-6">
						<div class="mb-3">
							<label for="status" class="form-label">Status</label> <select
								class="form-select form-select-sm" name="status" id="status">
								<option value="AVAILABLE"
									${product.status == 'AVAILABLE' ? 'selected' : ''}>Available</option>
								<option value="OUT_OF_STOCK"
									${product.status == 'OUT_OF_STOCK' ? 'selected' : ''}>Out
									of stock</option>
								<option value="DISCONTINUE"
									${product.status == 'DISCONTINUE' ? 'selected' : ''}>Discontinue</option>
								<option value="DELETED"
									${product.status == 'DELETED' ? 'selected' : ''}>Deleted</option>
							</select>
						</div>

					</div>
				</div>
				<div class="row">
					<div class="col">

						<div class="mb-3">
							<label for="" class="form-label">Product Name</label> <input
								type="text" class="form-control form-control-sm" name="name"
								id="name" aria-describedby="productNameHelper"
								placeholder="Product Name" value="${product.name}" /> <small
								id="productNameHelper" class="form-text text-muted">Product
								name must be entered!</small>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-6">
						<div class="mb-3"></div>
					</div>
					<div class="col-6"></div>
				</div>
			</div>
			<div class="row">
				<div class="col-6">
					<div class="mb-3">
						<label for="price" class="form-label">Price</label> <input
							type="number" class="form-control form-control-sm" name="price"
							id="price" aria-describedby="priceHeplerId" placeholder="Price"
							value="${product.price}" /> <small id="priceHeplerId"
							class="form-text text-muted">Price must be entered!</small>
					</div>
					<label for="quantity" class="form-label">Quantity</label> <input
						type="number" class="form-control form-control-sm" name="quantity"
						id="quantity" aria-describedby="quantityHelpId"
						placeholder="Quantity" min="0" value="${product.quantity}" /> <small
						id="quantityHelpId" class="form-text text-muted">Quantity
						must be entered!</small>
				</div>
				<div class="col-6">
					<div class="mb-3">
						<label for="image" class="form-label">Product Image</label> <br />
						<img src="images/${product.imageUrl}" alt="" class="img-fluid"
							style="height: 150px" /><input type="file"
							class="form-control form-control-sm" name="image" id="image"
							placeholder="Product Image" />
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<div class="mb-3">
						<label for="description" class="form-label">Description</label>
						<textarea class="form-control form-control-sm" name="description"
							id="description" rows="5">${product.description}</textarea>
					</div>

				</div>
			</div>

			<div class="row">
				<div class="col">
					<hr>
				</div>
			</div>
			<div class="row">
				<div class="col d-flex flex-row justify-content-between">
					<div class="">
						<button formaction="${path}/create"
							class="btn btn-primary btn-sm mx-2">
							<i class="fa fa-plus" aria-hidden="true"></i> Create
						</button>
						<button formaction="${path}/update"
							class="btn btn-warning btn-sm mx-2">
							<i class="fa fa-edit" aria-hidden="true"></i> Update
						</button>
						<button formaction="${path}/delete"
							class="btn btn-danger btn-sm mx-2">
							<i class="fa fa-remove" aria-hidden="true"></i> Delete
						</button>
						<button formaction="${path}/reset"
							class="btn btn-success btn-sm mx-2">
							<i class="fa fa-refresh" aria-hidden="true"></i> Reset
						</button>
					</div>
					<div class="">
						<a href="${path}/index" class="btn btn-outline-primary btn-sm">
							<i class="fa fa-list" aria-hidden="true"></i> Products List
						</a>
					</div>
				</div>
			</div>
		</form>

	</main>

	<%@ include file="/admin/common/footer.jsp"%>
</body>

</html>

</html>