<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Forgot Password</title>
<%@ include file="/securities/common/header/header.jsp"%>
</head>
<body>

	<div class="forgot-card">
		<div class="brand-icon">
			<i class="fa-solid fa-key"></i>
		</div>
		<h4 class="brand-title">Forgot Password</h4>
		<div class="brand-subtitle">Enter your email or username to
			receive reset instructions</div>

		<!-- Hiển thị thông báo lỗi từ server nếu có -->
		<%
		String error = (String) request.getAttribute("forgotError");
		if (error != null) {
		%>
		<div class="alert alert-danger py-2" role="alert">
			<i class="fa-solid fa-triangle-exclamation me-2"></i>
			<%=error%>
		</div>
		<%
		}
		%>

		<!-- Hiển thị thông báo thành công nếu có -->
		<%
		String success = (String) request.getAttribute("forgotSuccess");
		if (success != null) {
		%>
		<div class="alert alert-success py-2" role="alert">
			<i class="fa-solid fa-circle-check me-2"></i>
			<%=success%>
		</div>
		<%
		}
		%>

		<!-- Form forgot password -->
		<!-- Form sẽ POST tới /forgot-password (Servlet gửi hướng dẫn đặt lại mật khẩu) -->
		<form id="forgotForm" action="/forgot-password" method="post"
			novalidate>
			<div class="mb-3">
				<div class="input-group">
					<span class="input-group-text input-icon"><i
						class="fa-solid fa-user"></i></span> <input type="text"
						class="form-control" id="identifier" name="identifier"
						placeholder="Email or Username" required>
					<div class="invalid-feedback">Please enter your email or
						username.</div>
				</div>
			</div>

			<div class="d-grid mb-3">
				<button type="submit" class="btn btn-primary btn-lg">
					<i class="fa-solid fa-paper-plane me-2"></i>Send Instructions
				</button>
			</div>

			<div class="text-center small text-muted">
				Remember your password? <a href="/login">Back to Login</a>
			</div>
		</form>
	</div>

	<%@ include file="/securities/common/footer/footer.jsp"%>

</body>
</html>