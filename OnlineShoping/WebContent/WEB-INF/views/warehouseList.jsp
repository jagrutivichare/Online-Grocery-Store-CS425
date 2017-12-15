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
						
						<th>Warehouse Name</th>
						<th>Capacity</th>
						<th>Address</th>
						<th></th>
						<th></th>
					</tr>
					<c:forEach items="${warehouseList}" var="warehouse">
						<tr>
							<td>${warehouse.warehouseName}</td>
							<td>${warehouse.capacity}</td>
							<td>${warehouse.address.buildingNum},
								${warehouse.address.street} <br /> ${warehouse.address.city}<br />
								${warehouse.address.state}<br /> ${warehouse.address.zip}<br />
							</td>
                            <td><a href="editWarehouse?warehouseName=${warehouse.warehouseName}">Edit</a></td>
                            <td><a href="productList?warehouse=${warehouse.warehouseName}">View Products</a></td>
						</tr>
					</c:forEach>
				</table>
			</td>
			<td style="vertical-align: top;"></td>
		</tr>
		<tr><td colspan="2"><a href="addWarehouse">Add Warehouse</a></td></tr>
	</table>
	<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>