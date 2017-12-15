<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Info</title>
</head>
<body>

	<jsp:include page="_header.jsp"></jsp:include>
	<jsp:include page="_menu.jsp"></jsp:include>
    <jsp:include page="_searchControl.jsp"></jsp:include>
	<h3>Hello: ${user.name}</h3>

	User Name:
	<b>${user.userID}</b>
	<br /> Designation: ${user.jobTitle }
	
	<br /> Address

	<pre>${user.address.buildingNum}</pre>
	<pre>${user.address.street}</pre>
	<pre>${user.address.city}</pre>
	<pre>${user.address.state}</pre>
	<pre>${user.address.zip}</pre>


	<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>