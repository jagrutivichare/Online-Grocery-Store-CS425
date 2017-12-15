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

	<form method="POST" action="doRegisterServlet">
		<table border="0">
			<tr>
				<td>User Id</td>
				<td><input type=text required="required" name=uid></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><input type="password" required="required" name="pwd" /></td>
			</tr>
			<tr>
				<td>Name</td>
				<td><input type=text required="required" name=custname></td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2">Delivery Address</td>

			</tr>
			<tr>
				<td>BuildingNo</td>
				<td><input type=text required="required" name=dbn></td>
			</tr>
			<tr>
				<td>Street</td>
				<td><input type=text required="required" name=dstreet></td>
			</tr>
			<tr>
				<td>City</td>
				<td><input type=text required="required" name=dcity></td>
			</tr>
			<tr>
				<td>State</td>
				<td><input type=text required="required" name=dstate></td>
			</tr>
			<tr>
				<td>Zip</td>
				<td><input type=text required="required" name=dzip></td>
			</tr>

			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2">Payment Address</td>

			</tr>
			<tr>
				<td>BuildingNo</td>
				<td><input type=text required="required" name=pbn></td>
			</tr>
			<tr>
				<td>Street</td>
				<td><input type=text required="required" name=pstreet></td>
			</tr>
			<tr>
				<td>City</td>
				<td><input type=text required="required" name=pcity></td>
			</tr>
			<tr>
				<td>State</td>
				<td><input type=text required="required" name=pstate></td>
			</tr>
			<tr>
				<td>Zip</td>
				<td><input type=text required="required" name=pzip></td>
			</tr>

			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td>Credit card name</td>
				<td><input type=text required="required" name=ccdnm></td>
			</tr>
			<tr>
				<td>Credit card No</td>
				<td><input type=text required="required" name=ccdno></td>
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