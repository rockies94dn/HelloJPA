<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="pageTitle" value="Chi tiết người dùng" />
<%@ include file="/admin/common/header.jsp"%>

<body>
	<c:url var="path" value="/admin/users"></c:url>
	<h2 style="justify-self: center">User Details</h2>
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
					<h5 class="card-title">${user.name}</h5>
				</div>
				<div class="card-body">
					<div class="row">
						<div class="col-6">
							<p class="card-text">
								<strong>User ID:</strong> ${user.id}
							</p>
							<p class="card-text">
								<strong>Name:</strong> ${user.name}
							</p>
							<p class="card-text">
								<strong>Email:</strong> ${user.email}
							</p>
							<p class="card-text">
								<strong>Role:</strong> ${user.roleName}
							</p>
						</div>
						<div class="col-6">
							<p class="card-text">
								<strong>Status:</strong> ${user.statusName}
							</p>
							<p class="card-text">
								<strong>Created At:</strong> ${user.createAtStr}
							</p>
							<p class="card-text">
								<strong>Updated At:</strong> ${user.updateAtStr}
							</p>
							<p class="card-text">
								<strong>Last Login At:</strong> ${user.lastLoginAtStr}
							</p>
						</div>
					</div>
				</div>
				<div class="col d-flex flex-row justify-content-between">
					<div
						class="card-footer text-muted col d-flex flex-row justify-content-between">
						<a href="${path}/list" class="btn btn-primary btn-sm">Back to
							Users list</a>
						<div class="">
							<a href="${path}/edit/${user.id}" class="btn btn-primary btn-sm">
								<i class="fa fa-edit" aria-hidden="true"></i> Edit
							</a>
							<button type="button"
								onclick="confirmDelete('${path}/delete/${user.id}', '${user.name}')"
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