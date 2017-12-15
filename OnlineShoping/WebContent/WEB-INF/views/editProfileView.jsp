<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Profile</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.css">

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">
	$(function() {
		$("#divDelAddress").dialog({
			autoOpen : false,
			height : 400,
			title : "Add Delivery Address",
			width : 350,
			modal : true,
			buttons : {
				"Add" : function() {
					$("#delAddressForm").submit();
				},
				Cancel : function() {
					$(this).dialog("close");
				}
			}

		});

		$("#divPayAddress").dialog({
			autoOpen : false,
			height : 400,
			title : "Add Payment Address",
			width : 350,
			modal : true,
			buttons : {
				"Add" : function() {
					$("#payAddressForm").submit();
				},
				Cancel : function() {
					$(this).dialog("close");
				}
			}

		});

		$("#btnAddMoreDel").on("click", function() {
			$("#divDelAddress").dialog("open");
		});

		$("#btnAddMorePay").on("click", function() {
			$("#divPayAddress").dialog("open");
		});
	});
</script>
</head>
<body>

	<jsp:include page="_header.jsp"></jsp:include>
	<jsp:include page="_menu.jsp"></jsp:include>

	<h3>Edit Profile</h3>

	<p style="color: red;">${errorString}</p>

	<form method="POST" action="doEditServlet">
		<table style="border: none">
			<tr>
				<td colspan="2">
					<table style="border: none">
						<tr>
							<td>User Id</td>
							<td><input type=text name=uid readonly="readonly"
								value="${user.userID}"></td>
						</tr>
						<tr>
							<td>Password</td>
							<td><input type="password" required="required" name="pwd"
								value="${user.password}" /></td>
						</tr>
						<tr>
							<td>Name</td>
							<td><input type=text name=custname required="required"
								value="${user.name}"></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td style="vertical-align: top; width: 300px"><table>
						<tr>
							<td>
						<tr>
							<td colspan="2">Delivery Address <input type="button"
								Value="Add More" id="btnAddMoreDel" /></td>

						</tr>
						<tr>
							<td colspan="2"><c:set var="totaldelAdd" value="${0}" /></td>
						</tr>
						<c:forEach items="${user.custDeliveryAddresses}" var="delAddress">
							<c:set var="totaldelAdd" value="${totaldelAdd + 1}" />
							<tr>
								<td>BuildingNo</td>
								<td><input type=text name="dbn${totaldelAdd}"
									required="required" value="${delAddress.address.buildingNum}"></td>
							</tr>
							<tr>
								<td>Street</td>
								<td><input type=text name="dstreet${totaldelAdd}"
									required="required" value="${delAddress.address.street}"></td>
							</tr>
							<tr>
								<td>City</td>
								<td><input type=text name="dcity${totaldelAdd}"
									required="required" value="${delAddress.address.city}"></td>
							</tr>
							<tr>
								<td>State</td>
								<td><input type=text name="dstate${totaldelAdd}"
									required="required" value="${delAddress.address.state}"></td>
							</tr>
							<tr>
								<td>Zip</td>
								<td><input type=text name="dzip${totaldelAdd}"
									required="required" value="${delAddress.address.zip}"></td>
							</tr>
							<tr>
								<td colspan="2"><br /> <br /> <br /></td>
							</tr>
						</c:forEach>
					</table>
					<input type=text name="delTotal" style="display: none"
					required="required" value="${totaldelAdd}" /></td>
				<td style="vertical-align: top"><table>
						<tr>
							<td colspan="2">Payment Address <input type="button"
								Value="Add More" id="btnAddMorePay" /></td>

						</tr>
						<tr>
							<td colspan="2"><c:set var="totalPayAdd" value="${0}" /></td>
						</tr>
						<c:forEach items="${user.custPaymentAddresses}"
							var="paymentAddress">
							<c:set var="totalPayAdd" value="${totalPayAdd + 1}" />
							<tr>
								<td>BuildingNo</td>
								<td><input type=text name="pbn${totalPayAdd}"
									required="required"
									value="${paymentAddress.address.buildingNum}"></td>
							</tr>
							<tr>
								<td>Street</td>
								<td><input type=text name="pstreet${totalPayAdd}"
									required="required" value="${paymentAddress.address.street}"></td>
							</tr>
							<tr>
								<td>City</td>
								<td><input type=text name="pcity${totalPayAdd}"
									required="required" value="${paymentAddress.address.city}"></td>
							</tr>
							<tr>
								<td>State</td>
								<td><input type=text name="pstate${totalPayAdd}"
									required="required" value="${paymentAddress.address.state}"></td>
							</tr>
							<tr>
								<td>Zip</td>
								<td><input type=text name="pzip${totalPayAdd}"
									required="required" value="${paymentAddress.address.zip}"></td>
							</tr>
							<tr>
								<td colspan="2">&nbsp;</td>
							</tr>
							<tr>
								<td>Credit card name</td>
								<td><input type=text name="ccdnm${totalPayAdd}"
									required="required"
									value="${paymentAddress.creditCard.CCUsername}"></td>
							</tr>
							<tr>
								<td>Credit card No</td>
								<td><input type=text name="ccdno${totalPayAdd}"
									required="required"
									value="${paymentAddress.creditCard.CCNumber}"></td>
							</tr>
							<tr>
								<td colspan="2"><br /> <br /> <br /></td>
							</tr>

						</c:forEach>


					</table> <input type=text name="paymentTotal" style="display: none"
					required="required" value="${totalPayAdd}" /></td>
			</tr>
			<tr>
				<td colspan="2"><table>
						<tr>
							<td colspan="2"><input type="submit" value="Submit" /> <a
								href="${pageContext.request.contextPath}/">Cancel</a></td>
						</tr>
					</table></td>
			</tr>
		</table>


	</form>
	<jsp:include page="_footer.jsp"></jsp:include>


	<div id="divDelAddress" style="display: none">
		<form method="POST" action="addDelAddress" id="delAddressForm">
			<table>
				<tr>
					<td>BuildingNo</td>
					<td><input type=text name="dbn" required="required" value=""></td>
				</tr>
				<tr>
					<td>Street</td>
					<td><input type=text name="dstreet" required="required"
						value=""></td>
				</tr>
				<tr>
					<td>City</td>
					<td><input type=text name="dcity" required="required" value=""></td>
				</tr>
				<tr>
					<td>State</td>
					<td><input type=text name="dstate" required="required"
						value=""></td>
				</tr>
				<tr>
					<td>Zip</td>
					<td><input type=text name="dzip" required="required" value=""></td>
				</tr>

			</table>
		</form>
	</div>



	<div id="divPayAddress" style="display: none">
		<form method="POST" action="addPayAddress" id="payAddressForm">
			<table>
				<tr>
					<td>BuildingNo</td>
					<td><input type=text name="pbn" required="required" value=""></td>
				</tr>
				<tr>
					<td>Street</td>
					<td><input type=text name="pstreet" required="required"
						value=""></td>
				</tr>
				<tr>
					<td>City</td>
					<td><input type=text name="pcity" required="required" value=""></td>
				</tr>
				<tr>
					<td>State</td>
					<td><input type=text name="pstate" required="required"
						value=""></td>
				</tr>
				<tr>
					<td>Zip</td>
					<td><input type=text name="pzip" required="required" value=""></td>
				</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td>Credit card name</td>
					<td><input type=text name="ccdnm" required="required" value=""></td>
				</tr>
				<tr>
					<td>Credit card No</td>
					<td><input type=text name="ccdno" required="required" value=""></td>
				</tr>
			</table>
		</form>
	</div>