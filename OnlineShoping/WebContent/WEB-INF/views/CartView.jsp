<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cart</title>
</head>
<body>

	<jsp:include page="_header.jsp"></jsp:include>
	<jsp:include page="_menu.jsp"></jsp:include>
	<jsp:include page="_searchControl.jsp"></jsp:include>

	<h3>Cart Items</h3>
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
						<th></th>


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
							<td><c:if test="${product.quantity!='0'}">
									<form method="POST" action="updateCart">
										<c:set var="startIndex" scope="page" value="1" />
										<c:set var="endIndex" scope="page" value="${product.availableQuantity}" />
										<select name="numberOFItem" id="numberOFItem">
											<c:forEach begin="${startIndex}" end="${endIndex}" step="1"
												var="index">
												<option ${product.quantity == index ? 'selected' : ''} value="${index}">${index}</option>
											</c:forEach>
										</select> <input type="hidden" name=hdnProductId
											value="${product.productID}" /> <input type="submit"
											value="Update cart" width="100px" />

									</form>
								</c:if>
							</td>
							<td><form method="POST" action="deleteFromCart">
									<input type="hidden" name=hdnProductId
										value="${product.productID}" /> <input type="submit"
										value="Remove" width="100px" />
								</form></td>
						</tr>
					</c:forEach>
				</table>
			</td>
			<td style="vertical-align: top;"><form method="POST"
					action="checkout">
					<table>
						<tr>
							<td colspan="2"><input type="submit" value="Place Order" /></td>
						</tr>
						<tr>
							<td>Credit Card Number</td>
							<td><select name="ddlCreditCardno" required="required"
								id="ddlCreditCardno">
									<c:forEach items="${loginedUser.custPaymentAddresses}"
										var="paymentAddress">

										<option value="${paymentAddress.creditCard.CCNumber}">${paymentAddress.creditCard.CCNumber}</option>

									</c:forEach>
							</select></td>
						</tr>
					</table>

				</form></td>
		</tr>
	</table>
	<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>