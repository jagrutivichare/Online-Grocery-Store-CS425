<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Warehouse</title>
</head>
<body>

	<jsp:include page="_header.jsp"></jsp:include>
	<jsp:include page="_menu.jsp"></jsp:include>

	<h3>Add Warehouse</h3>

	<p style="color: red;">${errorString}</p>

	<form method="POST" 
		action="doAddWareHouse">
		<table border="0">
		    <tr>
				<td>Warehouse Name</td>
				<td><input type="text" required="required" name="warehouseName" value="${warehouse.warehouseName}" /></td>
			</tr>
			<tr>
				<td>Capacity</td>
				<td><input type="text" required="required" name="capacity" value="${warehouse.capacity}" /></td>
			</tr>
			<tr>
				<td colspan="2">Address</td>
			</tr>

			<tr>
				<td>BuildingNo</td>
				<td><input type=text name=bn required="required"
					value="${warehouse.address.buildingNum}"></td>
			</tr>
			<tr>
				<td>Street</td>
				<td><input type=text required="required" name=street value="${warehouse.address.street}"></td>
			</tr>
			<tr>
				<td>City</td>
				<td><input type=text required="required" name=city value="${warehouse.address.city}"></td>
			</tr>
			<tr>
				<td>State</td>
				<td><input type=text required="required" name=state value="${warehouse.address.state}"></td>
			</tr>
			<tr>
				<td>Zip</td>
				<td><input type=text required="required" name=zip value="${warehouse.address.zip}"></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Submit" /> <a
					href="warehouseList">Cancel</a></td>
			</tr>
		</table>
	</form>

	<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>