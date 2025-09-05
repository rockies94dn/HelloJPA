<%@page import="com.sun.tools.rngom.ast.builder.Include"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ include file="/admin/common/header.jsp" %>

<body class="bg-light text-dark">

	<main class="container">
		<c:set var="title" value="Category Management Tag"></c:set>
		<h2>
			<c:out value="${title}" />
		</h2>
		<hr>
		<c:if test="${message != null}">
			<div class="alert alert-success">${message}</div>
		</c:if>
		<c:if test="${error != null}">
			<div class="alert alert-danger">${error}</div>
		</c:if>
		<form action="" method="post">
			<div class="row">
				<div class="col-6">
					<div class="mt-3">
						<label for="categoryId" class="form-label">ID</label> <input
							type="text" class="form-control-sm" name="categoryId"
							id="categoryId" placeholder="Category ID" readonly
							value="${category.categoryId}" />
					</div>
				</div>
				<div class="col-6">
					<div class="mt-3">
						<label for="name" class="form-label">Name</label> <input
							type="text" class="form-control-sm" name="name" id="name"
							placeholder="Category Name" value="${category.name}" />
					</div>
				</div>
			</div>
			<div class="row mt-3">
				<div class="col">
					<hr>
					<button formaction="${path}/create" class="btn btn-primary m-2">
						<i class="fa fa-plus" aria-hidden="true"></i> Create
					</button>
					<button formaction="${path}/update" class="btn btn-warning m-2">
						<i class="fa fa-edit" aria-hidden="true"></i> Update
					</button>
					<button formaction="${path}/delete" class="btn btn-danger m-2">
						<i class="fa fa-remove" aria-hidden="true"></i> Delete
					</button>
					<button formaction="${path}/reset" class="btn btn-success m-2">
						<i class=" fa fa-refresh" aria-hidden="true"></i> Reset
					</button>
				</div>
			</div>
		</form>
		<div>
			<div class="row mt-4">
				<div class="col-6">
					<div class="d-flex flex-row">
						<form action="${path}/findbyname" method="get">
							<input type="text" class="form-control form-control-sm mx-2"
								name="textSearch" id="textSearch"
								placeholder="Enter category name" />
							<button class="btn btn-outline-primary btn-sm">Search</button>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="row mt-3">
			<div class="col">
				<div class="table-responsive">
					<table class="table table-responsive table-hover table-striped">
						<thead>
							<tr>
								<th scope="col">Category ID</th>
								<th scope="col">Name</th>
								<th scope="col">&nbsp;</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${categories}">
								<tr class="">
									<td scope="row">${item.categoryId}</td>
									<td>${item.name}</td>
									<td><a href="${path}/edit/${item.categoryId}">Edit</a> <a
										href="${path}/delete/${item.categoryId}">Delete</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

			</div>
		</div>

	</main>


	<%@ include file="/admin/common/footer.jsp" %>
</body>

</html>