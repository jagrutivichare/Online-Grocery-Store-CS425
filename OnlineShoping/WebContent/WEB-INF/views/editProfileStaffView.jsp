<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Profile</title>
</head>
<body>

	<jsp:include page="_header.jsp"></jsp:include>
	<jsp:include page="_menu.jsp"></jsp:include>

	<h3>Edit Profile</h3>
	<p style="color: red;">${errorString}</p>

	<form method="POST" action="doEditServletStaff">
		<table border="0">
			<tr>
				<td>User Id</td>
				<td><input type=text readonly="readonly" name=uid
					value="${user.userID}"></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><input type="password" required="required" name="pwd"
					value="${user.password}" /></td>
			</tr>
			<tr>
				<td>Name</td>
				<td><input type=text required="required" name=custname value="${user.name}"></td>
			</tr>
			<tr>
				<td>Salary</td>
				<td><input type=text required="required" name=salary value="${user.salary}"></td>
			</tr>
			<tr>
				<td>Job Title</td>
				<td><input type=text required="required" name=jobTitle value="${user.jobTitle}"></td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2">Address</td>
			</tr>

			<tr>
				<td>BuildingNo</td>
				<td><input type=text name=bn required="required"
					value="${user.address.buildingNum}"></td>
			</tr>
			<tr>
				<td>Street</td>
				<td><input type=text required="required" name=street value="${user.address.street}"></td>
			</tr>
			<tr>
				<td>City</td>
				<td><input type=text required="required" name=city value="${user.address.city}"></td>
			</tr>
			<tr>
				<td>State</td>
				<td><input type=text required="required" name=state value="${user.address.state}"></td>
			</tr>
			<tr>
				<td>Zip</td>
				<td><input type=text required="required" name=zip value="${user.address.zip}"></td>
			</tr>

			<tr>
				<td colspan="2"><input type="submit" value="Submit" /> <a
					href="${pageContext.request.contextPath}/">Cancel</a></td>
			</tr>
		</table>
	</form>
	<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>