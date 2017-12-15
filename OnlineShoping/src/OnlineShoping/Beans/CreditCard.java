package OnlineShoping.Beans;

import OnlineShoping.Utils.MyUtils;

public class CreditCard {

	private String CCNumber;
	private String CCUsername;
	private String addressID;
	private String userID;
	private String makedCCNumber;
	
	private float balance;

	public String getCCNumber() {
		return CCNumber;
	}
	public void setCCNumber(String cCNumber) {
		CCNumber = cCNumber;
		setMakedCCNumber(MyUtils.maskCardNumber(CCNumber));
	}
	public String getCCUsername() {
		return CCUsername;
	}
	public void setCCUsername(String cCUsername) {
		CCUsername = cCUsername;
	}
	public String getAddressID() {
		return addressID;
	}
	public void setAddressID(String addressID) {
		this.addressID = addressID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getMakedCCNumber() {
		return makedCCNumber;
	}
	public void setMakedCCNumber(String makedCCNumber) {
		this.makedCCNumber = makedCCNumber;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}

}
