<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="pageTitle" value="Danh sách người dùng" />
<%@ include file="/admin/common/header.jsp"%>
<body>
	<c:url var="path" value="/admin/users"></c:url>
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
						<th scrope="col">Name</th>
						<th scrope="col">Email</th>
						<th scrope="col">Role</th>
						<th scrope="col">Status</th>
						<th scrope="col">Created Date</th>
						<th scrope="col">&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="user" items="${users}">
						<tr class="">
							<td scope="col">${user.id}</td>
							<td>${user.name}</td>
							<td>${user.email}</td>
							<td>${user.roleStr}</td>
							<td>${user.statusStr}</td>
							<td>${user.createAtStr}</td>
							<td><a href="${path}/view/${user.id}"
								class="btn btn-outline-primary btn-sm"> <i class="fa fa-eye"
									aria-hidden="true"></i> View
							</a> <a href="${path}/edit/${user.id}"
								class="btn btn-primary btn-sm"> <i class="fa fa-edit"
									aria-hidden="true"></i> Edit
							</a>
								<button type="button"
									onclick="confirmDelete('${path}/delete/${user.id}', '${user.name}')"
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

	<!-- 	define modal dialog to confirm Delete the product -->
	<%@ include file="/admin/common/delete-confirm-modal.jsp"%>

	<%@ include file="/admin/common/footer.jsp"%>
</body>
</html>