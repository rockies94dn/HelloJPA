<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="pageTitle" value="Đăng ký" />
<%@ include file="/admin/common/header.jsp"%>

<body>
	<c:url var="path" value="admin/users"></c:url>
	<main class="container mt-2">
		<c:if test="${message != null}">
			<div class="alert alert-success">${message}</div>
		</c:if>
		<c:if test="${error != null}">
			<div class="alert alert-danger">${error}</div>
		</c:if>

		<h3 class="tetx-center">New User</h3>
		<hr />
		<form method="post">

			<div class="row">
				<input type="hidden" name="id" value="${user.id}" />
				<div class="col">
					<label for="name">Name</label> <input type="text" id="name"
						name="name" class="form-control" value="${user.name }" required />
				</div>
				<div class="col">
					<label for="email">Email</label> <input type="text" id="email"
						name="email" class="form-control" value="${user.email }" required />
				</div>
			</div>

			<div class="row mt-2">
				<div class="col">
					<label for="password">Password</label> <input type="password"
						id="password" name="password" class="form-control"
						value="${user.password }" required />
				</div>

				<div class="col">
					<label for="role">Role</label> <select
						class="form-select form-select-sm" name="role" id="role">
						<option value="ADMIN" ${user.role == 'ADMIN' ? 'selected' : ''}>ADMIN</option>
						<option value="USER" ${user.role == 'USER' ? 'selected' : ''}>USER</option>
						<option value="GUEST" ${user.role == 'GUEST' ? 'selected' : ''}>GUEST</option>
						<option value="MODERATOR"
							${user.role == 'MODERATOR' ? 'selected' : ''}>MODERATOR</option>
						<option value="SUPER_ADMIN"
							${user.role == 'SUPER_ADMIN' ? 'selected' : ''}>SUPER
							ADMIN</option>
					</select>
				</div>
			</div>
			<hr />
			<div class="row mt-2">
				<div class="col d-flex flex-row justify-content-between">
					<div class="">
						<button formaction="${path}/create" class="btn btn-primary btn-sm">
							<i class="fa fa-plus" aria-hidden="true"></i> Create
						</button>
						<button formaction="${path}/update" class="btn btn-warning btn-sm">
							<i class="fa fa-edit" aria-hidden="true"></i> Update
						</button>
						<button formaction="${path}/delete" class="btn btn-danger btn-sm">
							<i class="fa fa-remove" aria-hidden="true"></i> Delete
						</button>
						<button formaction="${path}/reset" class="btn btn-success btn-sm">
							<i class="fa fa-refresh" aria-hidden="true"></i> Reset
						</button>
					</div>
					<div class="">
						<a href="${path}/index" class="btn btn-outline-primary btn-sm">
							<i class="fa fa-list" aria-hidden="true"></i> Users List
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