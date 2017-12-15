<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Warehouse List</title>
</head>
<body>

	<jsp:include page="_header.jsp"></jsp:include>
	<jsp:include page="_menu.jsp"></jsp:include>
	<jsp:include page="_searchControl.jsp"></jsp:include>

	<h3>Warehouse List</h3>
	<p style="color: red;">${errorString}</p>
	<table>
		<tr>
			<td style="vertical-align: top;">


				<table border="1" cellpadding="5" cellspacing="1">
					<tr>
						
						<th>Supplier ID</th>
						<th>Supplier Name</th>
						<th>Address</th>
						<th></th>
						<th></th>
					</tr>
					<c:forEach items="${supplierList}" var="supplier">
						<tr>
							<td>${supplier.supplierID}</td>
							<td>${supplier.supplierName}</td>
							<td>${supplier.address.buildingNum},
								${supplier.address.street} <br /> ${supplier.address.city}<br />
								${supplier.address.state}<br /> ${supplier.address.zip}<br />
							</td>
                            <td><a href="editSupplier?supplierID=${supplier.supplierID}">Edit</a></td>
                            <td><a href="productList?supplier=${supplier.supplierID}">View Products</a></td>
						</tr>
					</c:forEach>
				</table>
			</td>
			<td style="vertical-align: top;"></td>
		</tr>
		<tr><td colspan="2"><a href="addSupplier">Add Supplier</a></td></tr>
	</table>
	<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>