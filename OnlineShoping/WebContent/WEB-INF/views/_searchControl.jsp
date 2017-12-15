<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<div style="display: block; width: 100%" align=center>
	<form method="POST" action="productList">
		<table border="0">
			<tr>
				<td><input name="searchParam" width="400px" value="${searchParam}" /></td>
				<td><input type="submit" value="Search"  /></td>
			</tr>
		</table>
	</form>
</div>