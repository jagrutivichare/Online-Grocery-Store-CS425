package OnlineShoping.Utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import OnlineShoping.Beans.Address;
import OnlineShoping.Beans.CartItems;
import OnlineShoping.Beans.CreditCard;
import OnlineShoping.Beans.CustDeliveryAddress;
import OnlineShoping.Beans.CustPaymentAddress;
import OnlineShoping.Beans.Order;
import OnlineShoping.Beans.OrderDetail;
import OnlineShoping.Beans.Product;
import OnlineShoping.Beans.StaffMember;
import OnlineShoping.Beans.Supplier;
import OnlineShoping.Beans.UserAccount;
import OnlineShoping.Beans.Warehouse;

public class DBUtils {

	public static UserAccount findUser(Connection conn, String userName, String password) throws SQLException {

		String sql = "Select a.custID as userID, a.Password, a.balance,a.custName from customer a "
				+ " where a.custID = ? and a.password= ?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);
		pstm.setString(2, password);
		ResultSet rs = pstm.executeQuery();

		if (rs.next()) {
			UserAccount user = new UserAccount();
			user.setUserID(userName);
			user.setName(rs.getString("custName"));
			user.setBalance(rs.getFloat("balance"));
			user.setCustDeliveryAddresses(getCustDeliveryAddress(conn, userName));
			user.setCustPaymentAddresses(getCustPaymentAddress(conn, userName));
			return user;
		}
		return null;
	}

	public static UserAccount findUser(Connection conn, String userName) throws SQLException {

		String sql = "Select a.custID as userID, a.Password, a.balance,a.custName from customer a "
				+ " where a.custID = ?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);

		ResultSet rs = pstm.executeQuery();

		if (rs.next()) {
			UserAccount user = new UserAccount();
			user.setUserID(userName);
			user.setName(rs.getString("custName"));
			user.setBalance(rs.getFloat("balance"));
			user.setCustDeliveryAddresses(getCustDeliveryAddress(conn, userName));
			user.setCustPaymentAddresses(getCustPaymentAddress(conn, userName));
			return user;
		}
		return null;
	}

	public static void deleteUser(Connection conn, UserAccount userAccount) throws SQLException {
		String sql = "Delete From creditCard where custID= ? ";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userAccount.getUserID());
		pstm.executeUpdate();

		sql = "Delete From custAddress where custID= ?";

		pstm = conn.prepareStatement(sql);

		pstm.setString(1, userAccount.getUserID());
		pstm.executeUpdate();

		List<CustDeliveryAddress> custDeliveryAddresses = userAccount.getCustDeliveryAddresses();
		if (custDeliveryAddresses != null) {
			for (CustDeliveryAddress custDeliveryAddress : custDeliveryAddresses) {
				sql = "Delete From address where addressID= ?";
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, custDeliveryAddress.getAddress().getAddressID());
				pstm.executeUpdate();
			}
		}

		List<CustPaymentAddress> custPaymentAddresses = userAccount.getCustPaymentAddresses();
		if (custPaymentAddresses != null) {
			for (CustPaymentAddress custPaymentAddress : custPaymentAddresses) {
				sql = "Delete From address where addressID= ?";
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, custPaymentAddress.getAddress().getAddressID());
				pstm.executeUpdate();
			}
		}

		sql = "Delete From cartItems where custID= ? ";
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, userAccount.getUserID());
		pstm.executeUpdate();

		sql = "Delete From customer where custID= ?";

		pstm = conn.prepareStatement(sql);
		pstm.setString(1, userAccount.getUserID());
		pstm.executeUpdate();

	}

	public static List<CustDeliveryAddress> getCustDeliveryAddress(Connection conn, String userID) throws SQLException {
		String sql = "Select a.custID , a.addressID  from custAddress a " + " where a.type='delivery' and a.custID = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userID);
		ResultSet rs = pstm.executeQuery();
		boolean TRUE = false;
		List<CustDeliveryAddress> CustDeliveryAddresses = new ArrayList<CustDeliveryAddress>();
		CustDeliveryAddress custDeliveryAddress = null;
		while (rs.next()) {
			custDeliveryAddress = new CustDeliveryAddress();
			custDeliveryAddress.setAddress(getAddress(conn, rs.getString("addressID")));
			custDeliveryAddress.setUserID(userID);
			CustDeliveryAddresses.add(custDeliveryAddress);
			TRUE = true;
		}
		if (TRUE)
			return CustDeliveryAddresses;
		else
			return null;
	}

	public static List<CustPaymentAddress> getCustPaymentAddress(Connection conn, String userID) throws SQLException {
		String sql = "Select a.custID , a.addressID  from custAddress a " + " where a.type='payment' and a.custID = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userID);
		ResultSet rs = pstm.executeQuery();
		boolean TRUE = false;
		List<CustPaymentAddress> CustPaymentAddresses = new ArrayList<CustPaymentAddress>();
		CustPaymentAddress custPaymentAddress = null;
		while (rs.next()) {
			custPaymentAddress = new CustPaymentAddress();
			custPaymentAddress.setAddress(getAddress(conn, rs.getString("addressID")));
			custPaymentAddress.setUserID(userID);
			custPaymentAddress.setCreditCard(getCreditCard(conn, rs.getString("addressID"), userID));
			CustPaymentAddresses.add(custPaymentAddress);
			TRUE = true;
		}
		if (TRUE)
			return CustPaymentAddresses;
		else
			return null;
	}

	public static CreditCard getCreditCard(Connection conn, String addressID, String userID) throws SQLException {
		String sql = "Select a.CCNumber,a.balance , a.CCUsername, a.custID,a.addressID from creditCard a "
				+ " where a.addressID = ? and a.custID=?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, addressID);
		pstm.setString(2, userID);
		ResultSet rs = pstm.executeQuery();

		if (rs.next()) {
			CreditCard creditCard = new CreditCard();
			creditCard.setCCNumber(rs.getString("CCNumber"));
			creditCard.setCCUsername(rs.getString("CCUsername"));
			creditCard.setUserID(rs.getString("custID"));
			creditCard.setAddressID(rs.getString("addressID"));
			creditCard.setBalance(rs.getFloat("balance"));
			return creditCard;
		}
		return null;
	}

	public static Address getAddress(Connection conn, String addressID) throws SQLException {
		String sql = "Select a.addressID , a.buildingNum, a.street,a.city,a.state,a.zip from address a "
				+ " where a.addressID = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, addressID);
		ResultSet rs = pstm.executeQuery();

		if (rs.next()) {
			Address address = new Address();
			address.setAddressID(rs.getString("addressID"));
			address.setBuildingNum(rs.getString("buildingNum"));
			address.setCity(rs.getString("city"));
			address.setState(rs.getString("state"));
			address.setStreet(rs.getString("street"));
			address.setZip(rs.getString("zip"));
			return address;
		}
		return null;
	}

	public static StaffMember findStaffUser(Connection conn, String userName, String password) throws SQLException {

		String sql = "Select a.staffID as userID, a.addressID, a.salary,a.jobTitle,a.staffName from staff a "
				+ " where a.staffID = ? and a.password= ?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);
		pstm.setString(2, password);
		ResultSet rs = pstm.executeQuery();

		if (rs.next()) {
			StaffMember staffMember = new StaffMember();
			staffMember.setName(rs.getString("staffName"));
			staffMember.setPassword(password);
			staffMember.setUserID(rs.getString("userID"));
			staffMember.setJobTitle(rs.getString("jobTitle"));
			staffMember.setSalary(rs.getString("salary"));
			staffMember.setAddress(getAddress(conn, rs.getString("addressID")));
			return staffMember;
		}
		return null;
	}

	public static StaffMember findStaffUser(Connection conn, String userName) throws SQLException {

		String sql = "Select a.staffID as userID, a.addressID, a.salary,a.jobTitle,a.staffName from staff a "
				+ " where a.staffID = ? ";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);

		ResultSet rs = pstm.executeQuery();

		if (rs.next()) {
			StaffMember staffMember = new StaffMember();
			staffMember.setName(rs.getString("staffName"));
			staffMember.setUserID(rs.getString("userID"));
			staffMember.setJobTitle(rs.getString("jobTitle"));
			staffMember.setSalary(rs.getString("salary"));
			staffMember.setAddress(getAddress(conn, rs.getString("addressID")));
			return staffMember;
		}
		return null;
	}

	public static void AddCustomer(Connection conn, UserAccount account) throws SQLException {
		String sql = "Insert into customer(custID, custName,balance,PASSWORD) values (?,?,?,?)";

		PreparedStatement pstm = conn.prepareStatement(sql);

		pstm.setString(1, account.getUserID());
		pstm.setString(2, account.getName());
		pstm.setFloat(3, account.getBalance());
		pstm.setString(4, account.getPassword());
		pstm.executeUpdate();

		List<CustDeliveryAddress> custDeliveryAddresses = account.getCustDeliveryAddresses();
		List<CustPaymentAddress> custPaymentAddresses = account.getCustPaymentAddresses();

		if (custDeliveryAddresses != null)
			AddCustDeliveryAddresses(conn, custDeliveryAddresses);
		if (custPaymentAddresses != null)
			AddCustPayementAddresses(conn, custPaymentAddresses);
	}

	public static void AddCustDeliveryAddresses(Connection conn, List<CustDeliveryAddress> custDeliveryAddresses)
			throws SQLException {
		if (custDeliveryAddresses != null) {
			for (CustDeliveryAddress custDeliveryAddress : custDeliveryAddresses) {
				AddAddress(conn, custDeliveryAddress.getAddress());
				String sql = "Insert into custAddress(custID, addressID,type) values (?,?,?)";
				PreparedStatement pstm = conn.prepareStatement(sql);
				pstm.setString(1, custDeliveryAddress.getUserID());
				pstm.setString(2, custDeliveryAddress.getAddress().getAddressID());
				pstm.setString(3, custDeliveryAddress.getType());
				pstm.executeUpdate();
			}
		}
	}

	public static void AddCustPayementAddresses(Connection conn, List<CustPaymentAddress> custPaymentAddresses)
			throws SQLException {
		if (custPaymentAddresses != null) {
			for (CustPaymentAddress custPaymentAddress : custPaymentAddresses) {
				AddAddress(conn, custPaymentAddress.getAddress());
				AddCreditCard(conn, custPaymentAddress.getCreditCard());
				String sql = "Insert into custAddress(custID, addressID,type) values (?,?,?)";
				PreparedStatement pstm = conn.prepareStatement(sql);
				pstm.setString(1, custPaymentAddress.getUserID());
				pstm.setString(2, custPaymentAddress.getAddress().getAddressID());
				pstm.setString(3, custPaymentAddress.getType());
				pstm.executeUpdate();
			}
		}
	}

	public static void AddCreditCard(Connection conn, CreditCard creditCard) throws SQLException {
		String sql = "Insert into creditCard(CCNumber, CCUsername,custID,addressID,balance) values (?,?,?,?,1000)";

		PreparedStatement pstm = conn.prepareStatement(sql);

		pstm.setString(1, creditCard.getCCNumber());
		pstm.setString(2, creditCard.getCCUsername());
		pstm.setString(3, creditCard.getUserID());
		pstm.setString(4, creditCard.getAddressID());
		pstm.executeUpdate();
	}

	public static void AddAddress(Connection conn, Address address) throws SQLException {
		String sql = "Insert into address(addressID, buildingNum,street,city,state,zip) values (?,?,?,?,?,?)";

		PreparedStatement pstm = conn.prepareStatement(sql);

		pstm.setString(1, address.getAddressID());
		pstm.setInt(2, Integer.parseInt(address.getBuildingNum()));
		pstm.setString(3, address.getStreet());
		pstm.setString(4, address.getCity());
		pstm.setString(5, address.getState());
		pstm.setString(6, address.getZip());
		pstm.executeUpdate();
	}

	public static void AddStaffMember(Connection conn, StaffMember staffMember) throws SQLException {
		AddAddress(conn, staffMember.getAddress());

		String sql = "Insert into staff(staffID, staffName,addressID,salary,jobTitle,PASSWORD) values (?,?,?,?,?,?)";

		PreparedStatement pstm = conn.prepareStatement(sql);

		pstm.setString(1, staffMember.getUserID());
		pstm.setString(2, staffMember.getName());
		pstm.setString(3, staffMember.getAddress().getAddressID());
		pstm.setString(4, staffMember.getSalary());
		pstm.setString(5, staffMember.getJobTitle());
		pstm.setString(6, staffMember.getPassword());
		pstm.executeUpdate();

	}

	public static void deleteAdmin(Connection conn, StaffMember userAccount) throws SQLException {
		String sql = "Delete From staff where staffID= ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userAccount.getUserID());
		pstm.executeUpdate();

		sql = "Delete From address where addressID= ? ";

		pstm = conn.prepareStatement(sql);
		pstm.setString(1, userAccount.getAddress().getAddressID());
		pstm.executeUpdate();
	}

	// Product Operations

	public static List<Product> queryProduct(Connection conn, String UserType, String userID) throws SQLException {
		String sql = "";
		if (UserType == "NormalUser") {
			if (userID != "") {
				sql = "Select c.warehouseName,sd.supplierID,sd.price as sup_price,a.productID,NVL2(c.numberOfItems,c.numberOfItems,0) as "
						+ "numberOfItems,NVL2(b.price,b.price,0.00) as price,NVL2(b.state,b.state,'') as state, "
						+ "a.productImage, a.productName,a.type,a.description,a.productSize from Product a  JOIN "
						+ "productPrice b  "
						+ "ON (a.productID=b.productID) LEFT JOIN stock c ON (a.productID=c.productID) LEFT JOIN supplyDetails sd ON (a.productID=sd.productID) WHERE b.state IN ( SELECT state FROM address WHERE addressID IN( SELECT addressID FROM custAddress WHERE custID='"+userID+"' ))";
			} else {
				sql = "Select c.warehouseName,sd.supplierID,sd.price as sup_price,a.productID,NVL2(c.numberOfItems,c.numberOfItems,0) as "
						+ "numberOfItems,NVL2(b.price,b.price,0.00) as price,NVL2(b.state,b.state,'') as state, "
						+ "a.productImage, a.productName,a.type,a.description,a.productSize from Product a LEFT JOIN "
						+ "productPrice b  "
						+ "ON (a.productID=b.productID) LEFT JOIN stock c ON (a.productID=c.productID) LEFT JOIN supplyDetails sd ON (a.productID=sd.productID)";
			}
		} else {
			sql = "Select c.warehouseName,sd.supplierID,sd.price as sup_price,a.productID,NVL2(c.numberOfItems,c.numberOfItems,0) as "
					+ "numberOfItems,NVL2(b.price,b.price,0.00) as price,NVL2(b.state,b.state,'') as state, "
					+ "a.productImage, a.productName,a.type,a.description,a.productSize from Product a LEFT JOIN "
					+ "productPrice b  "
					+ "ON (a.productID=b.productID) LEFT JOIN stock c ON (a.productID=c.productID) LEFT JOIN supplyDetails sd ON (a.productID=sd.productID)";
		}

		PreparedStatement pstm = conn.prepareStatement(sql);

		ResultSet rs = pstm.executeQuery();
		List<Product> list = new ArrayList<Product>();
		while (rs.next()) {

			Product product = new Product();
			product.setDescription(rs.getString("description"));
			product.setProductID(rs.getString("productID"));
			// product.setBytes(rs.getBytes("productImage"));
			product.setProductPrice(rs.getFloat("price"));
			product.setProductSize(rs.getInt("productSize"));
			product.setProducttType(rs.getString("type"));
			product.setProductName(rs.getString("productName"));
			product.setState(rs.getString("state"));
			product.setQuantity(rs.getInt("numberOfItems"));
			product.setSupplier(rs.getString("supplierID"));
			product.setSupplierPrice(rs.getString("sup_price"));
			product.setWarehouse(rs.getString("warehouseName"));
			list.add(product);
		}
		return list;
	}

	public static List<Product> queryProduct(Connection conn, String searchText, String UserType, String userID)
			throws SQLException {

		String sql = "";
		if (UserType == "NormalUser") {
			if (userID != "") {
				sql = "Select c.warehouseName,sd.supplierID,sd.price as sup_price,a.productID,NVL2(c.numberOfItems,c.numberOfItems,0) as numberOfItems,NVL2(b.price,b.price,0.00) as price,NVL2(b.state,b.state,'') as state, a.productImage, a.productName,a.type,a.description,a.productSize from Product a  JOIN productPrice b  ON (a.productID=b.productID) LEFT JOIN stock c ON (a.productID=c.productID) LEFT JOIN supplyDetails sd ON (a.productID=sd.productID) WHERE "
						+ "a.productName LIKE '%" + searchText + "%' OR a.productID LIKE '%" + searchText
						+ "%' OR b.state LIKE '%" + searchText + "%' OR a.description LIKE '%" + searchText
						+ "%' OR a.type LIKE '%" + searchText + "%' AND b.state IN ( SELECT state FROM address WHERE addressID IN( SELECT addressID FROM custAddress WHERE custID='"+userID+"' )) ";
			} else {
				sql = "Select c.warehouseName,sd.supplierID,sd.price as sup_price,a.productID,NVL2(c.numberOfItems,c.numberOfItems,0) as numberOfItems,NVL2(b.price,b.price,0.00) as price,NVL2(b.state,b.state,'') as state, a.productImage, a.productName,a.type,a.description,a.productSize from Product a LEFT JOIN productPrice b  ON (a.productID=b.productID) LEFT JOIN stock c ON (a.productID=c.productID) LEFT JOIN supplyDetails sd ON (a.productID=sd.productID) WHERE "
						+ "a.productName LIKE '%" + searchText + "%' OR a.productID LIKE '%" + searchText
						+ "%' OR b.state LIKE '%" + searchText + "%' OR a.description LIKE '%" + searchText
						+ "%' OR a.type LIKE '%" + searchText + "%'  ";
			}
		} else {
			sql = "Select c.warehouseName,sd.supplierID,sd.price as sup_price,a.productID,NVL2(c.numberOfItems,c.numberOfItems,0) as numberOfItems,NVL2(b.price,b.price,0.00) as price,NVL2(b.state,b.state,'') as state, a.productImage, a.productName,a.type,a.description,a.productSize from Product a LEFT JOIN productPrice b  ON (a.productID=b.productID) LEFT JOIN stock c ON (a.productID=c.productID) LEFT JOIN supplyDetails sd ON (a.productID=sd.productID) WHERE "
					+ "a.productName LIKE '%" + searchText + "%' OR a.productID LIKE '%" + searchText
					+ "%' OR b.state LIKE '%" + searchText + "%' OR a.description LIKE '%" + searchText
					+ "%' OR a.type LIKE '%" + searchText + "%'  ";
		}

		PreparedStatement pstm = conn.prepareStatement(sql);

		ResultSet rs = pstm.executeQuery();
		List<Product> list = new ArrayList<Product>();
		while (rs.next()) {

			Product product = new Product();
			product.setDescription(rs.getString("description"));
			product.setProductID(rs.getString("productID"));
			// product.setBytes(rs.getBytes("productImage"));
			product.setProductPrice(rs.getFloat("price"));
			product.setProductSize(rs.getInt("productSize"));
			product.setProducttType(rs.getString("type"));
			product.setProductName(rs.getString("productName"));
			product.setState(rs.getString("state"));
			product.setQuantity(rs.getInt("numberOfItems"));
			product.setSupplier(rs.getString("supplierID"));
			product.setSupplierPrice(rs.getString("sup_price"));
			product.setWarehouse(rs.getString("warehouseName"));
			list.add(product);
		}
		return list;
	}

	public static List<Product> queryProductWithSuplierOrWarehouse(Connection conn, String Type, String Value)
			throws SQLException {

		String sql = "";

		if (Type == "warehouse") {
			sql = "Select c.warehouseName,sd.supplierID,sd.price as sup_price,a.productID,NVL2(c.numberOfItems,c.numberOfItems,0) as "
					+ "numberOfItems,NVL2(b.price,b.price,0.00) as price,NVL2(b.state,b.state,'') as state, "
					+ "a.productImage, a.productName,a.type,a.description,a.productSize from Product a LEFT JOIN "
					+ "productPrice b  "
					+ "ON (a.productID=b.productID) LEFT JOIN stock c ON (a.productID=c.productID) LEFT JOIN supplyDetails sd ON (a.productID=sd.productID) WHERE c.warehouseName=?";

		} else if (Type == "supplier") {
			sql = "Select c.warehouseName,sd.supplierID,sd.price as sup_price,a.productID,NVL2(c.numberOfItems,c.numberOfItems,0) as "
					+ "numberOfItems,NVL2(b.price,b.price,0.00) as price,NVL2(b.state,b.state,'') as state, "
					+ "a.productImage, a.productName,a.type,a.description,a.productSize from Product a LEFT JOIN "
					+ "productPrice b  "
					+ "ON (a.productID=b.productID) LEFT JOIN stock c ON (a.productID=c.productID) LEFT JOIN supplyDetails sd ON (a.productID=sd.productID) WHERE sd.supplierID=?";
		}
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, Value);
		ResultSet rs = pstm.executeQuery();
		List<Product> list = new ArrayList<Product>();
		while (rs.next()) {

			Product product = new Product();
			product.setDescription(rs.getString("description"));
			product.setProductID(rs.getString("productID"));
			// product.setBytes(rs.getBytes("productImage"));
			product.setProductPrice(rs.getFloat("price"));
			product.setProductSize(rs.getInt("productSize"));
			product.setProducttType(rs.getString("type"));
			product.setProductName(rs.getString("productName"));
			product.setState(rs.getString("state"));
			product.setQuantity(rs.getInt("numberOfItems"));
			product.setSupplier(rs.getString("supplierID"));
			product.setSupplierPrice(rs.getString("sup_price"));
			product.setWarehouse(rs.getString("warehouseName"));
			list.add(product);
		}
		return list;
	}

	public static List<Product> queryOrderedProduct(Connection conn, String userID) throws SQLException {
		String sql = "Select o.status,o.issued,a.productID,NVL2(c.quantity,c.quantity,0) as numberOfItems,NVL2(b.price,b.price,0.00) as price,NVL2(b.state,b.state,'') as state, a.productImage, a.productName,a.type,a.description,a.productSize from Product a LEFT JOIN productPrice b  ON (a.productID=b.productID) LEFT JOIN orderDetails c ON (a.productID=c.productID) JOIN orders o ON (o.orderID=c.orderID)  WHERE o.custID=?  ";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userID);
		ResultSet rs = pstm.executeQuery();
		List<Product> list = new ArrayList<Product>();
		while (rs.next()) {

			Product product = new Product();
			product.setDescription(rs.getString("description"));
			product.setProductID(rs.getString("productID"));
			// product.setBytes(rs.getBytes("productImage"));
			product.setProductPrice(rs.getFloat("price"));
			product.setProductSize(rs.getInt("productSize"));
			product.setProducttType(rs.getString("type"));
			product.setProductName(rs.getString("productName"));
			product.setState(rs.getString("state"));
			product.setQuantity(rs.getInt("numberOfItems"));
			product.setOrderStatus(rs.getString("status"));
			product.setOrderIssueDate(rs.getString("issued"));
			list.add(product);
		}
		return list;
	}

	public static List<Product> queryCartProduct(Connection conn, String userID) throws SQLException {
		String sql = "Select a.productID,NVL2(c.quantity,c.quantity,0) as "
				+ "numberOfItems,NVL2(s.numberOfItems,s.numberOfItems,0) as availableQuantity,NVL2(b.price,b.price,0.00) as price,NVL2(b.state,b.state,'') as state, "
				+ "a.productImage, a.productName,a.type,a.description,a.productSize from Product a LEFT JOIN "
				+ "productPrice b  "
				+ "ON (a.productID=b.productID)  JOIN cartItems c ON (a.productID=c.productID)  JOIN stock s ON (a.productID=s.productID) WHERE c.custID=?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userID);
		ResultSet rs = pstm.executeQuery();
		List<Product> list = new ArrayList<Product>();
		while (rs.next()) {

			Product product = new Product();
			product.setDescription(rs.getString("description"));
			product.setProductID(rs.getString("productID"));
			// product.setBytes(rs.getBytes("productImage"));
			product.setProductPrice(rs.getFloat("price"));
			product.setProductSize(rs.getInt("productSize"));
			product.setProducttType(rs.getString("type"));
			product.setProductName(rs.getString("productName"));
			product.setState(rs.getString("state"));
			product.setQuantity(rs.getInt("numberOfItems"));
			product.setAvailableQuantity(rs.getInt("availableQuantity"));
			list.add(product);
		}
		return list;
	}

	public static Product findProduct(Connection conn, String productID) throws SQLException {
		String sql = "Select c.warehouseName,sd.supplierID,sd.price as sup_price,a.productID,NVL2(c.numberOfItems,c.numberOfItems,0) as numberOfItems,NVL2(b.price,b.price,0.00) as price,NVL2(b.state,b.state,'') as state, a.productImage, a.productName,a.type,a.description,a.productSize from Product a LEFT JOIN productPrice b  ON (a.productID=b.productID) LEFT JOIN stock c ON (a.productID=c.productID) LEFT JOIN supplyDetails sd ON (a.productID=sd.productID) WHERE  a.productID=? ";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, productID);
		ResultSet rs = pstm.executeQuery();
		Product product = null;
		while (rs.next()) {

			product = new Product();
			product.setDescription(rs.getString("description"));
			product.setProductID(rs.getString("productID"));
			// product.setBytes(rs.getBytes("productImage"));
			product.setProductPrice(rs.getFloat("price"));
			product.setProductSize(rs.getInt("productSize"));
			product.setProducttType(rs.getString("type"));
			product.setProductName(rs.getString("productName"));
			product.setState(rs.getString("state"));
			product.setQuantity(rs.getInt("numberOfItems"));
			product.setSupplier(rs.getString("supplierID"));
			product.setSupplierPrice(rs.getString("sup_price"));
			product.setWarehouse(rs.getString("warehouseName"));
		}
		return product;
	}

	// public static void updateProduct(Connection conn, Product product) throws
	// SQLException {
	// String sql = "Update Product set Name =?, Price=? where Code=? ";
	//
	// PreparedStatement pstm = conn.prepareStatement(sql);
	//
	// pstm.setString(1, product.getName());
	// pstm.setFloat(2, product.getPrice());
	// pstm.setString(3, product.getCode());
	// pstm.executeUpdate();
	// }

	public static void insertProduct(Connection conn, Product product, Boolean isImageAvailable)
			throws SQLException, IOException {
		String sql = "Insert into product(productID, productImage,productName,type,description,productSize) values (?,?,?,?,?,?)";

		PreparedStatement pstm = conn.prepareStatement(sql);

		pstm.setString(1, product.getProductID());
		if (isImageAvailable)
			pstm.setBinaryStream(2, product.getProductImage().getInputStream(),
					(int) product.getProductImage().getSize());
		else
			pstm.setBlob(2, product.getpImage());
		pstm.setString(3, product.getProductName());
		pstm.setString(4, product.getProducttType());
		pstm.setString(5, product.getDescription());
		pstm.setInt(6, product.getProductSize());
		pstm.executeUpdate();

		sql = "Insert into productPrice(productID, state,price) values (?,?,?)";

		pstm = conn.prepareStatement(sql);

		pstm.setString(1, product.getProductID());
		pstm.setString(2, product.getState());
		pstm.setFloat(3, product.getProductPrice());
		pstm.executeUpdate();

		sql = "Insert into stock(productID, numberOfItems,warehouseName) values (?,?,?)";
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, product.getProductID());
		pstm.setInt(2, product.getQuantity());
		pstm.setString(3, product.getWarehouse());
		pstm.executeUpdate();

		sql = "Insert into supplyDetails(supplierID, productID,price) values (?,?,?)";
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, product.getSupplier());
		pstm.setString(2, product.getProductID());
		pstm.setInt(3, Integer.parseInt(product.getSupplierPrice()));
		pstm.executeUpdate();

	}

	public static void deleteProduct(Connection conn, String productID, String state) throws SQLException {

		String sql = "Delete From supplyDetails where  productID=?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, productID);
		pstm.executeUpdate();

		sql = "Delete From productPrice where productID= ? and state=?";
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, productID);
		pstm.setString(2, state);
		pstm.executeUpdate();

		sql = "Delete From cartItems where productID= ? ";
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, productID);
		pstm.executeUpdate();

		sql = "Delete From stock where productID= ? ";
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, productID);
		pstm.executeUpdate();

		sql = "Delete From Product where productID= ?";

		pstm = conn.prepareStatement(sql);

		pstm.setString(1, productID);

		pstm.executeUpdate();

	}

	public static void AddToCart(Connection conn, CartItems cartItems, String userID) throws SQLException {
		String sql = "Insert into cartItems(custID, productID,quantity) values (?,?,?)";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userID);
		pstm.setString(2, cartItems.getProductID());
		pstm.setInt(3, cartItems.getQuantity());
		pstm.executeUpdate();
	}

	public static List<CartItems> getCart(Connection conn, String userID) throws SQLException {
		String sql = "Select a.custID,a.productID ,a.quantity from cartItems a WHERE a.custID=? ";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userID);
		ResultSet rs = pstm.executeQuery();
		CartItems cartItem = null;
		List<CartItems> cartItems = new ArrayList<CartItems>();
		while (rs.next()) {
			cartItem = new CartItems();
			cartItem.setProductID(rs.getString("productID"));
			cartItem.setQuantity(rs.getInt("quantity"));
			cartItems.add(cartItem);
		}
		return cartItems;
	}

	public static void UpdateCart(Connection conn, CartItems cartItems, String userID) throws SQLException {
		String sql = "UPDATE cartItems SET quantity=? WHERE custID=? AND productID=?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, cartItems.getQuantity());
		pstm.setString(2, userID);
		pstm.setString(3, cartItems.getProductID());
		pstm.executeUpdate();
	}

	public static void PlaceOrder(Connection conn, Order order, String userID) throws SQLException {
		String sql = "Insert into orders(orderID, custID,issued,status,CCNumber) values (?,?,?,?,?)";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, order.getOrderID());
		pstm.setString(2, userID);
		pstm.setDate(3, order.getIssuedDate());
		pstm.setString(4, order.getStatus());
		pstm.setString(5, order.getCCNumber());
		pstm.executeUpdate();

		for (OrderDetail orderdetail : order.getOrderDetails()) {

			sql = "UPDATE creditCard SET balance=balance-(SELECT price FROM productPrice WHERE productID=?)*(SELECT quantity FROM cartItems WHERE productID=? AND custID=?)  WHERE  CCNumber=? ";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, orderdetail.getProductID());
			pstm.setString(2, orderdetail.getProductID());
			pstm.setString(3, userID);
			pstm.setInt(4, Integer.parseInt(order.getCCNumber()));
			pstm.executeUpdate();

			sql = "UPDATE stock SET numberOfItems=numberOfItems-(SELECT SUM(quantity) from  cartItems where productID=? and custID=?) WHERE productID=? ";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, orderdetail.getProductID());
			pstm.setString(2, userID);
			pstm.setString(3, orderdetail.getProductID());
			pstm.executeUpdate();

			sql = "Insert into orderDetails(orderID, productID,quantity) values (?,?,?)";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, order.getOrderID());
			pstm.setString(2, orderdetail.getProductID());
			pstm.setInt(3, orderdetail.getQuantity());
			pstm.executeUpdate();

			sql = "Delete From cartItems where custID= ? and productID=?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userID);
			pstm.setString(2, orderdetail.getProductID());
			pstm.executeUpdate();

		}

	}

	public static void AddWareHouse(Connection conn, Warehouse warehouse) throws SQLException, IOException {

		AddAddress(conn, warehouse.getAddress());
		String sql = "Insert into warehouse(warehouseName, addressID,capacity) values (?,?,?)";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, warehouse.getWarehouseName());
		pstm.setString(2, warehouse.getAddress().getAddressID());
		pstm.setFloat(3, warehouse.getCapacity());
		pstm.executeUpdate();
	}

	public static List<Warehouse> queryWarehouse(Connection conn) throws SQLException {
		String sql = "Select warehouseName,addressID,capacity from warehouse ";

		PreparedStatement pstm = conn.prepareStatement(sql);

		ResultSet rs = pstm.executeQuery();
		List<Warehouse> list = new ArrayList<Warehouse>();
		while (rs.next()) {

			Warehouse warehouse = new Warehouse();
			warehouse.setWarehouseName(rs.getString("warehouseName"));
			warehouse.setCapacity(rs.getFloat("capacity"));
			warehouse.setAddress(getAddress(conn, rs.getString("addressID")));
			list.add(warehouse);
		}
		return list;
	}

	public static Warehouse queryWarehouse(Connection conn, String warehouseName) throws SQLException {

		String sql = "Select warehouseName,addressID,capacity from warehouse where warehouseName=?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, warehouseName);

		ResultSet rs = pstm.executeQuery();

		Warehouse warehouse = null;
		rs.next();
		warehouse = new Warehouse();
		warehouse.setWarehouseName(rs.getString("warehouseName"));
		warehouse.setCapacity(rs.getFloat("capacity"));
		warehouse.setAddress(getAddress(conn, rs.getString("addressID")));

		return warehouse;
	}

	public static void updateWarehouse(Connection conn, Warehouse warehouse) throws SQLException {

		String sql = "UPDATE warehouse SET capacity=?  where warehouseName=? ";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setFloat(1, warehouse.getCapacity());
		pstm.setString(2, warehouse.getWarehouseName());
		pstm.executeUpdate();

		updateAddress(conn, warehouse.getAddress());

	}

	public static void updateAddress(Connection conn, Address address) throws SQLException {

		String sql = "UPDATE address SET buildingNum=?,street=?,city=?,state=?,zip=?  where addressID=? ";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, Integer.parseInt(address.getBuildingNum()));
		pstm.setString(2, address.getStreet());
		pstm.setString(3, address.getCity());
		pstm.setString(4, address.getState());
		pstm.setString(5, address.getZip());
		pstm.setString(6, address.getAddressID());
		pstm.executeUpdate();

	}

	public static void AddSupplier(Connection conn, Supplier supplier) throws SQLException, IOException {

		AddAddress(conn, supplier.getAddress());
		String sql = "Insert into supplier(supplierID, supplierName,addressID) values (?,?,?)";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, supplier.getSupplierID());
		pstm.setString(2, supplier.getSupplierName());
		pstm.setString(3, supplier.getAddress().getAddressID());
		pstm.executeUpdate();
	}

	public static List<Supplier> querySupplier(Connection conn) throws SQLException {
		String sql = "Select supplierID,supplierName,addressID from supplier ";

		PreparedStatement pstm = conn.prepareStatement(sql);

		ResultSet rs = pstm.executeQuery();
		List<Supplier> list = new ArrayList<Supplier>();
		while (rs.next()) {

			Supplier supplier = new Supplier();
			supplier.setSupplierID(rs.getString("supplierID"));
			supplier.setSupplierName(rs.getString("supplierName"));
			supplier.setAddress(getAddress(conn, rs.getString("addressID")));
			list.add(supplier);
		}
		return list;
	}

	public static Supplier querySupplier(Connection conn, String supplierID) throws SQLException {

		String sql = "Select supplierID,supplierName,addressID  from supplier where supplierID=?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, supplierID);

		ResultSet rs = pstm.executeQuery();

		Supplier supplier = null;
		rs.next();
		supplier = new Supplier();
		supplier.setSupplierID(rs.getString("supplierID"));
		supplier.setSupplierName(rs.getString("supplierName"));
		supplier.setAddress(getAddress(conn, rs.getString("addressID")));
		return supplier;
	}

	public static void updateSupplier(Connection conn, Supplier supplier) throws SQLException {

		String sql = "UPDATE supplier SET supplierName=?  where supplierID=? ";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, supplier.getSupplierName());
		pstm.setString(2, supplier.getSupplierID());
		pstm.executeUpdate();
		updateAddress(conn, supplier.getAddress());

	}

	public static void deleteCart(Connection conn, String userID, String ProductId) throws SQLException {

		String sql = "DELETE FROM cartItems WHERE custID=? and productID=?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userID);
		pstm.setString(2, ProductId);
		pstm.executeUpdate();
	}

	public static int CountProductWithSuplierOrWarehouse(Connection conn, String Type, String Value)
			throws SQLException {

		String sql = "";

		if (Type == "warehouse") {
			sql = " Select SUM(c.numberOfItems) as Totat FROM Product a LEFT JOIN " + "productPrice b  "
					+ "ON (a.productID=b.productID)  JOIN stock c ON (a.productID=c.productID)  JOIN supplyDetails sd ON (a.productID=sd.productID) WHERE c.warehouseName=?";

		} else if (Type == "supplier") {
			sql = "Select SUM(c.numberOfItems) as Totat from Product a LEFT JOIN " + "productPrice b  "
					+ "ON (a.productID=b.productID)  JOIN stock c ON (a.productID=c.productID)  JOIN supplyDetails sd ON (a.productID=sd.productID) WHERE sd.supplierID=?";
		}
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, Value);
		ResultSet rs = pstm.executeQuery();
		int Count = 0;
		if (rs.next()) {

			Count = rs.getInt("Totat");
		}
		return Count;
	}

}
