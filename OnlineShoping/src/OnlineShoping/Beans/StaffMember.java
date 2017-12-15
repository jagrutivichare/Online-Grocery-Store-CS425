package OnlineShoping.Beans;

public class StaffMember {
	
	private String userID;
	private String name;
	private Address address;
	private String salary;
	private String jobTitle;
	private String password;
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
//	public String getSalary() {
//		return salary;
//	}
//	public void setSalary(String salary) {
//		this.salary = salary;
//	}
//	public String getJobTitle() {
//		return jobTitle;
//	}
//	public void setJobTitle(String jobTitle) {
//		this.jobTitle = jobTitle;
//	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

}
