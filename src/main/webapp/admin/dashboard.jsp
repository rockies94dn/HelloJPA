<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Shop Dashboard</title>

<!-- Bootstrap 5 CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Font Awesome -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

<style>
body {
	min-height: 100vh;
	display: flex;
	flex-direction: column;
}

.sidebar {
	min-width: 220px;
	max-width: 220px;
	background-color: #343a40;
	min-height: 100vh;
}

.sidebar a {
	color: #fff;
	text-decoration: none;
	display: block;
	padding: 12px 20px;
}

.sidebar a:hover {
	background-color: #495057;
}

.content {
	flex: 1;
	padding: 20px;
}

.card i {
	font-size: 2rem;
}
</style>
</head>
<body>
	<div class="d-flex">
		<!-- Sidebar -->
		<div class="sidebar d-flex flex-column">
			<h3 class="text-white text-center py-3">Shop Admin</h3>
			<a href="#"><i class="fas fa-tachometer-alt me-2"></i> Dashboard</a>

			<!-- Products Menu -->
			<a class="d-flex justify-content-between align-items-center"
				data-bs-toggle="collapse" href="#productsMenu" role="button"
				aria-expanded="false" aria-controls="productsMenu"> <span><i
					class="fas fa-box-open me-2"></i> Products</span> <i
				class="fas fa-chevron-down"></i>
			</a>
			<div class="collapse ps-4" id="productsMenu">
				<a href="allProducts.jsp" class="d-block py-1">All Products</a> <a
					href="addProduct.jsp" class="d-block py-1">Add Product</a>
			</div>

			<!-- Orders Menu -->
			<a class="d-flex justify-content-between align-items-center"
				data-bs-toggle="collapse" href="#ordersMenu" role="button"
				aria-expanded="false" aria-controls="ordersMenu"> <span><i
					class="fas fa-shopping-cart me-2"></i> Orders</span> <i
				class="fas fa-chevron-down"></i>
			</a>
			<div class="collapse ps-4" id="ordersMenu">
				<a href="allOrders.jsp" class="d-block py-1">All Orders</a> <a
					href="pendingOrders.jsp" class="d-block py-1">Pending Orders</a>
			</div>

			<!-- Customers Menu -->
			<a class="d-flex justify-content-between align-items-center"
				data-bs-toggle="collapse" href="#customersMenu" role="button"
				aria-expanded="false" aria-controls="customersMenu"> <span><i
					class="fas fa-users me-2"></i> Customers</span> <i
				class="fas fa-chevron-down"></i>
			</a>
			<div class="collapse ps-4" id="customersMenu">
				<a href="allCustomers.jsp" class="d-block py-1">All Customers</a> <a
					href="vipCustomers.jsp" class="d-block py-1">VIP Customers</a>
			</div>

			<a href="#"><i class="fas fa-cog me-2"></i> Settings</a>
		</div>


		<!-- Main Content -->
		<div class="content flex-grow-1">
			<!-- Top Navbar -->
			<nav class="navbar navbar-expand-lg navbar-light bg-light mb-4">
				<div class="container-fluid">
					<span class="navbar-brand mb-0 h1">Dashboard</span>
					<div class="d-flex ms-auto">
						<i class="fas fa-user-circle fa-2x me-2"></i> <span>Admin</span>
					</div>
				</div>
			</nav>

			<!-- Statistic Cards -->
			<div class="row g-3 mb-4">
				<div class="col-md-3">
					<div class="card text-white bg-primary h-100">
						<div
							class="card-body d-flex justify-content-between align-items-center">
							<div>
								<h5 class="card-title">Products</h5>
								<h3>120</h3>
							</div>
							<i class="fas fa-box-open"></i>
						</div>
					</div>
				</div>
				<div class="col-md-3">
					<div class="card text-white bg-success h-100">
						<div
							class="card-body d-flex justify-content-between align-items-center">
							<div>
								<h5 class="card-title">Orders</h5>
								<h3>75</h3>
							</div>
							<i class="fas fa-shopping-cart"></i>
						</div>
					</div>
				</div>
				<div class="col-md-3">
					<div class="card text-white bg-warning h-100">
						<div
							class="card-body d-flex justify-content-between align-items-center">
							<div>
								<h5 class="card-title">Customers</h5>
								<h3>50</h3>
							</div>
							<i class="fas fa-users"></i>
						</div>
					</div>
				</div>
				<div class="col-md-3">
					<div class="card text-white bg-danger h-100">
						<div
							class="card-body d-flex justify-content-between align-items-center">
							<div>
								<h5 class="card-title">Revenue</h5>
								<h3>$8,500</h3>
							</div>
							<i class="fas fa-dollar-sign"></i>
						</div>
					</div>
				</div>
			</div>

			<!-- Products Table -->
			<div class="card">
				<div class="card-header">Product List</div>
				<div class="card-body">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>#</th>
								<th>Product Name</th>
								<th>Category</th>
								<th>Price</th>
								<th>Stock</th>
								<th>Actions</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="product" items="${products}">
								<tr>
									<td>${product.id}</td>
									<td>${product.name}</td>
									<td>${product.category}</td>
									<td>${product.price}</td>
									<td>${product.stock}</td>
									<td><a href="editProduct?id=${product.id}"
										class="btn btn-sm btn-warning"> <i class="fas fa-edit"></i>
									</a> <a href="deleteProduct?id=${product.id}"
										class="btn btn-sm btn-danger"> <i class="fas fa-trash-alt"></i>
									</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>

		</div>
	</div>

	<!-- Bootstrap 5 JS Bundle -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
