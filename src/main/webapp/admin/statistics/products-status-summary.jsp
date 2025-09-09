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

		<h1>Product Status Summaries</h1>
		<hr />
		<table class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<th scope="col">Product Status</th>
					<th scope="col">Number of products</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${data}">
					<tr>
						<td>${item.status}</td>
						<td>${item.count}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</main>

	<!-- 	define modal dialog to confirm Delete the product -->
	<%@ include file="/admin/common/delete-confirm-modal.jsp"%>

	<%@ include file="/admin/common/footer.jsp"%>
</body>
</html>