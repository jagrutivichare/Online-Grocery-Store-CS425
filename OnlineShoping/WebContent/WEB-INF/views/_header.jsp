<%@page import="OnlineShoping.Utils.DBUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div style="background: #E0E0E0; padding: 5px;">
	<table border="0" width="100%">
		<tr>
			<td><div>
					<h1>Online Grocery Store</h1>
				</div></td>
			<td><div style="float: right; padding: 10px; text-align: right;">

					<!-- User store in session with attribute: loginedUser -->
					<%
						if (session.getAttribute("loginedUser") != null) {
							
					%>
					Hello <b>${loginedUser.name}<c:set var="total" value="${0}" /> | 
						<c:forEach items="${loginedUser.cartItem}" var="cart">
							<c:set var="total" value="${total + cart.quantity}" />
						</c:forEach>
						<a href="cartView" > Cart(<c:out value="${total}" />) </a> | 
						<c:set var="totalBalance" value="${0}" />
						<c:forEach items="${loginedUser.custPaymentAddresses}" var="cardBalance">
							<c:set var="totalBalance" value="${totalBalance + cardBalance.creditCard.balance}" />
						</c:forEach>
						Balance(<c:out value="${totalBalance}" />)
					</b>
					<%
						}
					%>

					<%
						if (session.getAttribute("loginedUserStaff") != null) {
					%>
					Hello <b>${loginedUserStaff.name}</b>
					<%
						}
					%>




				</div></td>
		</tr>
	</table>



</div>

