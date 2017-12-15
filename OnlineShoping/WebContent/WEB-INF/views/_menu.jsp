<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div style="padding: 5px;display:block" align=center>

	<a href="${pageContext.request.contextPath}/">Home</a> | <a
		href="${pageContext.request.contextPath}/productList">Product List</a>
	|
	<%
		if (session.getAttribute("loginedUser") != null) {
	%>
	<a href="${pageContext.request.contextPath}/userInfo">My Account
		Info </a>
	<%
		}
		if (session.getAttribute("loginedUserStaff") != null) {
	%>
	<a href="${pageContext.request.contextPath}/staffInfo">My Account
		Info </a>
	<%
		}
	%>
	<%
		if (session.getAttribute("loginedUser") != null) {
	%>
|	<a href="${pageContext.request.contextPath}/editProfile">Edit
		Profile </a> | <a href="${pageContext.request.contextPath}/orderView">My Orders</a>
	<%
		}
		if (session.getAttribute("loginedUserStaff") != null) {
	%>
 |	<a href="${pageContext.request.contextPath}/editProfileStaff">Edit
		Profile  </a> 
	<%
		}
	%>
	<%
		if (session.getAttribute("loginedUser") == null && session.getAttribute("loginedUserStaff") == null) {
	%>
	 <a href="${pageContext.request.contextPath}/login">Login</a> | <a
		href="${pageContext.request.contextPath}/register">Sign Up</a> 
	<%
		} else {
	%>
 |	<a href="${pageContext.request.contextPath}/logout">Logout</a>
	<%
		}
	%>

</div>
