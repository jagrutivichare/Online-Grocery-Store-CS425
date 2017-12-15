package OnlineShoping.Beans;

import java.util.List;

public class UserAccount {

	private String userID;
	private String password;
	private String name;
	private float balance; 
	private List<CustDeliveryAddress> custDeliveryAddresses;
	private List<CustPaymentAddress> custPaymentAddresses;
	
	private List<CartItems> cartItem;
	
	private List<Order> order;
	
	
	
	public UserAccount() {

	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userid) {
		this.userID = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public List<CustDeliveryAddress> getCustDeliveryAddresses() {
		return custDeliveryAddresses;
	}

	public void setCustDeliveryAddresses(List<CustDeliveryAddress> custDeliveryAddresses) {
		this.custDeliveryAddresses = custDeliveryAddresses;
	}

	public List<CustPaymentAddress> getCustPaymentAddresses() {
		return custPaymentAddresses;
	}

	public void setCustPaymentAddresses(List<CustPaymentAddress> custPaymentAddresses) {
		this.custPaymentAddresses = custPaymentAddresses;
	}

	public List<CartItems> getCartItem() {
		return cartItem;
	}

	public void setCartItem(List<CartItems> cartItem) {
		this.cartItem = cartItem;
	}

	public List<Order> getOrder() {
		return order;
	}

	public void setOrder(List<Order> order) {
		this.order = order;
	}

	

	

}