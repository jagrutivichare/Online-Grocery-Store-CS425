<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Supplier</title>
</head>
<body>

	<jsp:include page="_header.jsp"></jsp:include>
	<jsp:include page="_menu.jsp"></jsp:include>

	<h3>Add Supplier</h3>

	<p style="color: red;">${errorString}</p>

	<form method="POST" 
		action="doAddSupplier">
		<table border="0">
		    <tr>
				<td>Supplier ID</td>
				<td><input type="text" required="required" name="supplierID" value="${supplier.supplierID}" /></td>
			</tr>
			<tr>
				<td>Supplier Name</td>
				<td><input type="text" required="required" name="supplierName" value="${supplier.supplierName}" /></td>
			</tr>
			<tr>
				<td colspan="2">Address</td>
			</tr>

			<tr>
				<td>BuildingNo</td>
				<td><input type=text name=bn required="required"
					value="${supplier.address.buildingNum}"></td>
			</tr>
			<tr>
				<td>Street</td>
				<td><input type=text required="required" name=street value="${supplier.address.street}"></td>
			</tr>
			<tr>
				<td>City</td>
				<td><input type=text required="required" name=city value="${supplier.address.city}"></td>
			</tr>
			<tr>
				<td>State</td>
				<td><input type=text required="required" name=state value="${supplier.address.state}"></td>
			</tr>
			<tr>
				<td>Zip</td>
				<td><input type=text required="required" name=zip value="${supplier.address.zip}"></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Submit" /> <a
					href="supplierList">Cancel</a></td>
			</tr>
		</table>
	</form>

	<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>