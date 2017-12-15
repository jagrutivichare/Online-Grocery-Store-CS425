<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	<br />
	<c:set var="totalBalance" value="${0}" />
	<c:forEach items="${loginedUser.custPaymentAddresses}" var="cardBalance">
		<c:set var="totalBalance" value="${totalBalance + cardBalance.creditCard.balance}" />
	</c:forEach>
	Balance: <c:out value="${totalBalance}" />
	<br /> Delivery Address
	<c:forEach items="${user.custDeliveryAddresses}" var="delAddress">
		<pre>${delAddress.address.buildingNum}</pre>
		<pre>${delAddress.address.street}</pre>
		<pre>${delAddress.address.city}</pre>
		<pre>${delAddress.address.state}</pre>
		<pre>${delAddress.address.zip}</pre>
	</c:forEach>
	<br />
	<br /> Payment Address
	<c:forEach items="${user.custPaymentAddresses}" var="paymentAddress">
		<pre>${paymentAddress.address.buildingNum}</pre>
		<pre>${paymentAddress.address.street}</pre>
		<pre>${paymentAddress.address.city}</pre>
		<pre>${paymentAddress.address.state}</pre>
		<pre>${paymentAddress.address.zip}</pre>

		<br />
		<br />
	Credit Card Number
	    <pre>${paymentAddress.creditCard.makedCCNumber}</pre>
	    <pre>${paymentAddress.creditCard.CCUsername}</pre>
	</c:forEach>

	<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>