<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Product</title>
</head>
<body>

	<jsp:include page="_header.jsp"></jsp:include>
	<jsp:include page="_menu.jsp"></jsp:include>

	<h3>Edit Product</h3>

	<p style="color: red;">${errorString}</p>

	<c:if test="${not empty product}">
		<form method="POST" action="doEditProduct"
			enctype="multipart/form-data">
			<table border="0">
				<tr>
					<td style="vertical-align: top;"><table border="0">
							<tr>
								<td>Product ID</td>
								<td><input type="text" name="pid" readonly="readonly"
									value="${product.productID}" /></td>
							</tr>
							<tr>
								<td>Name</td>
								<td><input type="text" name="Name"
									value="${product.productName}" /></td>
							</tr>
							<tr>
								<td>Description</td>
								<td><input type="text" name="description"
									value="${product.description}" /></td>
							</tr>
							<tr>
								<td>Type</td>
								<td><select name=prodtype>
										<option ${product.producttType == 'Fruits' ? 'selected' : ''}>Fruits</option>
										<option
											${product.producttType == 'Vegetables' ? 'selected' : ''}>Vegetables</option>
										<option
											${product.producttType == 'Milk/Dairy' ? 'selected' : ''}>Milk/Dairy</option>
										<option
											${product.producttType == 'Beverages' ? 'selected' : ''}>Beverages</option>
								</select></td>
							</tr>
							<tr>
								<td>Price($)</td>
								<td><input type="text" name="prodprice"
									value="${product.productPrice}" /></td>
							</tr>
							<tr>
								<td>Size</td>
								<td><input type="text" name="prodSize"
									value="${product.productSize}" /></td>
							</tr>
							<tr>
								<td>State</td>
								<td><select name=state>
										<option ${product.state == 'Illinois' ? 'selected' : ''}>Illinois</option>
										<option ${product.state == 'Texas' ? 'selected' : ''}>Texas</option>
										<option ${product.state == 'Colorado' ? 'selected' : ''}>Colorado</option>
										<option ${product.state == 'California' ? 'selected' : ''}>California</option>
										<option ${product.state == 'Maryland' ? 'selected' : ''}>Maryland</option>
										<option ${product.state == 'Michigan' ? 'selected' : ''}>Michigan</option>
										<option ${product.state == 'Florida' ? 'selected' : ''}>Florida</option>
										<option ${product.state == 'Arizona' ? 'selected' : ''}>Arizona</option>
								</select></td>
							</tr>
							<tr>
								<td>Quantity</td>
								<td><input type="text" name="prodQuantity"
									value="${product.quantity}" /></td>
							</tr>
							<tr>
								<td>Supplier</td>
								<td><select name=supplier>
										<c:forEach items="${supplierList}" var="supplier">
											<option
												${product.supplier == supplier.supplierID ? 'selected' : ''}
												value="${supplier.supplierID}">${supplier.supplierName}</option>
										</c:forEach>

								</select></td>
							</tr>
							<tr>
								<td>Supplier Price</td>
								<td><input type="text" name="supplierPrice"
									value="${product.supplierPrice}" /></td>
							</tr>
							<tr>
								<td>Warehouse</td>
								<td><select name=warehouse>
										<c:forEach items="${warehouseList}" var="warehouse">
											<option
												${product.warehouse == warehouse.warehouseName? 'selected' : ''}
												value="${warehouse.warehouseName}">${warehouse.warehouseName}</option>
										</c:forEach>

								</select></td>
							</tr>
							<tr>
								<td>Select image</td>
								<td><input type=file name=prodimg></td>
							</tr>
							<tr>
								<td colspan="2"><input type="submit" value="Submit" /> <a
									href="productList">Cancel</a></td>
							</tr>
						</table></td>
					<td style="vertical-align: top;"><img width='120' height='120'
						src="DisplayPhoto?id=${product.productID}"></img></td>
				</tr>
			</table>

		</form>
	</c:if>

	<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>