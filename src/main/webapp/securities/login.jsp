<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Login</title>
<%@ include file="/securities/common/header/header.jsp"%>
</head>
<body>

	<div class="login-card">
		<div class="brand-icon">
			<i class="fa-solid fa-user-astronaut"></i>
		</div>
		<h4 class="brand-title">MyApp</h4>
		<div class="brand-subtitle">Login to continue</div>

		<!-- Hiển thị thông báo lỗi từ server nếu có -->
		<%
		String error = (String) request.getAttribute("loginError");
		if (error != null) {
		%>
		<div class="alert alert-danger py-2" role="alert">
			<i class="fa-solid fa-triangle-exclamation me-2"></i>
			<%=error%>
		</div>
		<%
		}
		%>

		<!-- Form đăng nhập -->
		<!-- Form sẽ POST tới /login (Servlet xử lý xác thực) -->
		<form action="${pageContext.request.contextPath}/login" method="post">
			<div class="mb-3">
				<div class="input-group">
					<span class="input-group-text input-icon"><i
						class="fa-solid fa-user"></i></span> <input type="text"
						class="form-control" id="email" name="email" placeholder="Email"
						required>
					<div class="invalid-feedback">Please enter your email.</div>
				</div>
			</div>

			<div class="mb-3">
				<div class="input-group">
					<span class="input-group-text input-icon"><i
						class="fa-solid fa-lock"></i></span> <input type="password"
						class="form-control" id="password" name="password"
						placeholder="Password" required>
					<div class="invalid-feedback">Please enter your password.</div>
				</div>
			</div>

			<div class="d-flex justify-content-between align-items-center mb-3">
				<div class="form-check">
					<input class="form-check-input" type="checkbox" value="true"
						id="rememberMe" name="rememberMe"> <label
						class="form-check-label" for="rememberMe">Remember me</label>
				</div>
				<a href="#" class="small">Forgot password?</a>
			</div>

			<div class="d-grid mb-3">
				<button type="submit" class="btn btn-primary btn-lg">
					<i class="fa-solid fa-right-to-bracket me-2"></i>Login
				</button>
			</div>

			<div class="text-center small text-muted">
				Don't have an account? <a href="/register">Sign up</a>
			</div>
		</form>

	</div>

	<%@ include file="/securities/common/footer/footer.jsp"%>
</body>
</html>
