package OnlineShoping.Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import OnlineShoping.Beans.Address;
import OnlineShoping.Beans.CreditCard;
import OnlineShoping.Beans.CustDeliveryAddress;
import OnlineShoping.Beans.CustPaymentAddress;
import OnlineShoping.Beans.StaffMember;
import OnlineShoping.Beans.UserAccount;
import OnlineShoping.Utils.DBUtils;
import OnlineShoping.Utils.MyUtils;

@WebServlet(urlPatterns = { "/doEditServlet", "/doEditServletStaff" })
public class DoEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DoEditServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		if (request.getServletPath().toString().equals("/doEditServlet")) {

			HttpSession session = request.getSession();
			UserAccount loginedUser = MyUtils.getLoginedUser(session);

			// Not logged in
			if (loginedUser == null) {

				// Redirect to login page.
				response.sendRedirect(request.getContextPath() + "/login");
				return;
			}

			List<CustDeliveryAddress> custDeliveryAddresses = new ArrayList<CustDeliveryAddress>();

			List<CustPaymentAddress> custPaymentAddresses = new ArrayList<CustPaymentAddress>();

			int delTotal = Integer.parseInt((String) request.getParameter("delTotal"));
			int paymentTotal = Integer.parseInt((String) request.getParameter("paymentTotal"));

			String dBuildingNo = "";
			String dStreet = "";
			String dCity = "";
			String dState = "";
			String dZip = "";
			String dAddressID = "";
			Address delivaryAddress = new Address();
			CustDeliveryAddress custDeliveryAddress = new CustDeliveryAddress();

			String userID = (String) request.getParameter("uid");

			String password = (String) request.getParameter("pwd");
			String name = (String) request.getParameter("custname");
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			for (int i = 1; i <= delTotal; i++) {
				timestamp = new Timestamp(System.currentTimeMillis());
				dAddressID = userID + "d" + i + timestamp.getTime();
				dBuildingNo = (String) request.getParameter("dbn" + i);
				dStreet = (String) request.getParameter("dstreet" + i);
				dCity = (String) request.getParameter("dcity" + i);
				dState = (String) request.getParameter("dstate" + i);
				dZip = (String) request.getParameter("dzip" + i);

				delivaryAddress = new Address();
				delivaryAddress.setAddressID(dAddressID);
				delivaryAddress.setBuildingNum(dBuildingNo);
				delivaryAddress.setCity(dCity);
				delivaryAddress.setState(dState);
				delivaryAddress.setStreet(dStreet);
				delivaryAddress.setZip(dZip);

				custDeliveryAddress = new CustDeliveryAddress();
				custDeliveryAddress.setAddress(delivaryAddress);
				custDeliveryAddress.setUserID(userID);

				custDeliveryAddresses.add(custDeliveryAddress);

			}

			for (int i = 1; i <= paymentTotal; i++) {
				timestamp = new Timestamp(System.currentTimeMillis());
				String pAddressID = userID + "p" + i + timestamp.getTime();
				String pBuildingNo = (String) request.getParameter("pbn" + i);
				String pStreet = (String) request.getParameter("pstreet" + i);
				String pCity = (String) request.getParameter("pcity" + i);
				String pState = (String) request.getParameter("pstate" + i);
				String pZip = (String) request.getParameter("pzip" + i);

				String CCName = (String) request.getParameter("ccdnm" + i);
				String CCNumber = (String) request.getParameter("ccdno" + i);

				CreditCard creditCard = new CreditCard();
				creditCard.setCCNumber(CCNumber);
				creditCard.setCCUsername(CCName);
				creditCard.setUserID(userID);
				creditCard.setAddressID(pAddressID);

				Address paymentAddress = new Address();
				paymentAddress.setAddressID(pAddressID);
				paymentAddress.setBuildingNum(pBuildingNo);
				paymentAddress.setCity(pCity);
				paymentAddress.setState(pState);
				paymentAddress.setStreet(pStreet);
				paymentAddress.setZip(pZip);

				CustPaymentAddress custPaymentAddress = new CustPaymentAddress();
				custPaymentAddress.setAddress(paymentAddress);
				custPaymentAddress.setCreditCard(creditCard);
				custPaymentAddress.setUserID(userID);

				custPaymentAddresses.add(custPaymentAddress);

			}

			UserAccount userAccount = new UserAccount();

			userAccount.setBalance(0.00f);
			userAccount.setName(name);
			userAccount.setUserID(userID);
			userAccount.setPassword(password);
			userAccount.setCustDeliveryAddresses(custDeliveryAddresses);
			userAccount.setCustPaymentAddresses(custPaymentAddresses);

			String errorString = null;

			// Product ID is the string literal [a-zA-Z_0-9]
			// with at least 1 character

			if (errorString == null) {
				try {
					DBUtils.deleteUser(conn, loginedUser);
					DBUtils.AddCustomer(conn, userAccount);
					MyUtils.storeLoginedUser(session, DBUtils.findUser(conn, userAccount.getUserID()));
				} catch (SQLException e) {
					e.printStackTrace();
					errorString = e.getMessage();
				}
			}

			// Store infomation to request attribute, before forward to views.
			request.setAttribute("errorString", errorString);

			// If error, forward to Edit page.
			if (errorString != null) {
				RequestDispatcher dispatcher = request.getServletContext()
						.getRequestDispatcher("/WEB-INF/views/editProfileView.jsp");
				dispatcher.forward(request, response);
			}

			// If everything nice.
			// Redirect to the product listing page.
			else {
				MyUtils.storeLoginedUser(session, userAccount);
				response.sendRedirect(request.getContextPath() + "/userInfo");
			}
		} else if (request.getServletPath().toString().equals("/doEditServletStaff"))

		{
			HttpSession session = request.getSession();
			StaffMember loginedUser = MyUtils.getLoginedForStaffUser(session);
			// Not logged in
			if (loginedUser == null) {

				// Redirect to login page.
				response.sendRedirect(request.getContextPath() + "/login");
				return;
			}
			String userID = (String) request.getParameter("uid");
			String password = (String) request.getParameter("pwd");
			String name = (String) request.getParameter("custname");

			String BuildingNo = (String) request.getParameter("bn");
			String Street = (String) request.getParameter("street");
			String City = (String) request.getParameter("city");
			String State = (String) request.getParameter("state");
			String Zip = (String) request.getParameter("zip");

			String salary = (String) request.getParameter("salary");
			String jobTitle = (String) request.getParameter("jobTitle");

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			String AddressID = userID + "Staff" + timestamp.getTime();

			Address address = new Address();
			address.setAddressID(AddressID);
			address.setBuildingNum(BuildingNo);
			address.setCity(City);
			address.setState(State);
			address.setStreet(Street);
			address.setZip(Zip);

			StaffMember staffMember = new StaffMember();
			staffMember.setName(name);
			staffMember.setPassword(password);
			staffMember.setUserID(userID);
			staffMember.setJobTitle(jobTitle);
			staffMember.setSalary(salary);
			staffMember.setAddress(address);

			String errorString = null;

			// Product ID is the string literal [a-zA-Z_0-9]
			// with at least 1 character

			if (errorString == null) {
				try {
					DBUtils.deleteAdmin(conn, loginedUser);
					DBUtils.AddStaffMember(conn, staffMember);
				} catch (SQLException e) {
					e.printStackTrace();
					errorString = e.getMessage();
				}
			}

			// Store infomation to request attribute, before forward to views.
			request.setAttribute("errorString", errorString);

			// If error, forward to Edit page.
			if (errorString != null) {
				RequestDispatcher dispatcher = request.getServletContext()
						.getRequestDispatcher("/WEB-INF/views/registerStaffView.jsp");
				dispatcher.forward(request, response);
			}

			// If everything nice.
			// Redirect to the product listing page.
			else {
				MyUtils.storeStaffLoginedUser(session, staffMember);
				response.sendRedirect(request.getContextPath() + "/login");
			}

		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
