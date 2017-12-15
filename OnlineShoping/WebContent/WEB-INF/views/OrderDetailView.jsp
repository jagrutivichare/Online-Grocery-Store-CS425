<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Product List</title>
</head>
<body>

	<jsp:include page="_header.jsp"></jsp:include>
	<jsp:include page="_menu.jsp"></jsp:include>
	<jsp:include page="_searchControl.jsp"></jsp:include>

	<h3>Order Detail</h3>
	<p style="color: red;">${errorString}</p>
	<table>
		<tr>
			<td style="vertical-align: top;">


				<table border="1" cellpadding="5" cellspacing="1">
					<tr>
						<th></th>
						<th>Produce Code</th>
						<th>Product Name</th>
						<th>Price</th>
						<th>Type</th>
						<th>Description</th>
						<th>productSize</th>
						<th>State</th>
						<th>Order Quantity</th>
						<th>Order Date</th>
						<th>Status</th>

					</tr>
					<c:forEach items="${productList}" var="product">
						<tr>
							<td><img width='80' height='80'
								src="DisplayPhoto?id=${product.productID}"></img></td>
							<td>${product.productID}</td>
							<td>${product.productName}</td>
							<td>${product.productPrice}</td>
							<td>${product.producttType}</td>
							<td>${product.description}</td>
							<td>${product.productSize}</td>
							<td>${product.state}</td>
							<td>${product.quantity}</td>
							<td>${product.orderIssueDate}</td>
							<td>${product.orderStatus}</td>
						</tr>
					</c:forEach>
				</table>
			</td>
			<td style="vertical-align: top;">
			</td>
		</tr>
	</table>
	<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>