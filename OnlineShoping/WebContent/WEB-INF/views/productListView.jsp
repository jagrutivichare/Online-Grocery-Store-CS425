<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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

	<h3>Product List</h3>
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
						<th>Quantity</th>
						<c:if test="${checkUserLogin=='Admin'}">
						    <th>Supplier</th>
						    <th>Warehouse</th>
							<th>Edit</th>
							<th>Delete</th>
						</c:if>
						<c:if test="${checkUserLogin=='NormalUser'}">

							<th></th>
						</c:if>
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
							<c:if test="${checkUserLogin=='Admin'}">
                                <td>${product.supplier}</td>
                                <td>${product.warehouse}</td>
								<td><a href="editProduct?productID=${product.productID}">Edit</a></td>
								<td><a
									href="deleteProduct?productID=${product.productID}&state=${product.state}">Delete</a></td>
							</c:if>
							<c:if test="${checkUserLogin=='NormalUser'}">
								<c:if test="${product.quantity!='0'}">
									<td><c:set var="isItemAdded" scope="page" value="no" /> <c:forEach
											items="${loginedUser.cartItem}" var="crtItm">
											<c:if test="${crtItm.productID==product.productID}">
												<c:set var="isItemAdded" scope="page" value="yes" />
											</c:if>
										</c:forEach> <c:if test="${isItemAdded=='no'}">
											<form method="POST" action="addToCart">
												<c:set var="startIndex" scope="page" value="1" />
												<c:set var="endIndex" scope="page"
													value="${product.quantity}" />
												<select name="numberOFItem" id="numberOFItem">
													<c:forEach begin="${startIndex}" end="${endIndex}" step="1"
														var="index">
														<option value="${index}">${index}</option>
													</c:forEach>
												</select> <input type="hidden" name=hdnProductId
													value="${product.productID}" /> <input type="submit"
													value="Add to cart" width="100px" />

											</form>
										</c:if> <c:if test="${isItemAdded=='yes'}">
							   Item Added to cart
							</c:if></td>
								</c:if>
								<c:if test="${product.quantity=='0'}">
									<td></td>
								</c:if>
							</c:if>
						</tr>
					</c:forEach>
				</table> <c:if test="${checkUserLogin=='Admin'}">
					<a href="createProduct">Create Product</a>
					<a href="warehouseList">Warehouse</a>
					<a href="supplierList">Supplier</a>
				</c:if>
			</td>
			<td style="vertical-align: top;"><c:set var="total" value="${0}" />
				<c:forEach items="${loginedUser.cartItem}" var="cart">
					<c:set var="total" value="${total + cart.quantity}" />
				</c:forEach> <c:if test="${total>0}">
					<form method="POST" action="checkout">
						<table>
							<tr>
								<td colspan="2"><input type="submit"
									value="Place Order" /></td>
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

					</form>
				</c:if></td>
		</tr>
	</table>
	<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>