<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>

	<jsp:include page="_header.jsp"></jsp:include>
	<jsp:include page="_menu.jsp"></jsp:include>

	<h3>Create Account</h3>
	<p style="color: red;">${errorString}</p>

	<form method="POST" action="doRegisterStaffServlet">
		<table border="0">
			<tr>
				<td>User Id</td>
				<td><input type=text name=uid></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><input type="password" name="pwd" /></td>
			</tr>
			<tr>
				<td>Name</td>
				<td><input type=text name=custname></td>
			</tr>
			<tr>
				<td>Salary</td>
				<td><input type=text name=salary></td>
			</tr>
			<tr>
				<td>Job Title</td>
				<td><input type=text name=jobTitle></td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2">Address</td>
			</tr>
			<tr>
				<td>BuildingNo</td>
				<td><input type=text name=bn></td>
			</tr>
			<tr>
				<td>Street</td>
				<td><input type=text name=street></td>
			</tr>
			<tr>
				<td>City</td>
				<td><input type=text name=city></td>
			</tr>
			<tr>
				<td>State</td>
				<td><input type=text name=state></td>
			</tr>
			<tr>
				<td>Zip</td>
				<td><input type=text name=zip></td>
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