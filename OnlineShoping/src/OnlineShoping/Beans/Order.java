package OnlineShoping.Beans;

import java.util.List;

public class Order {
    
	private String orderID;
	private java.sql.Date issuedDate;
	private String status;
	private String CCNumber;
	
	private List<OrderDetail> orderDetails;
	
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public java.sql.Date getIssuedDate() {
		return issuedDate;
	}
	public void setIssuedDate(java.sql.Date issuedDate) {
		this.issuedDate = issuedDate;
	}
	public String getCCNumber() {
		return CCNumber;
	}
	public void setCCNumber(String cCNumber) {
		CCNumber = cCNumber;
	}
	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
}
