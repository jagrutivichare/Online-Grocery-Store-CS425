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

import OnlineShoping.Beans.Address;
import OnlineShoping.Beans.CreditCard;
import OnlineShoping.Beans.CustDeliveryAddress;
import OnlineShoping.Beans.CustPaymentAddress;
import OnlineShoping.Beans.StaffMember;
import OnlineShoping.Beans.UserAccount;
import OnlineShoping.Utils.DBUtils;
import OnlineShoping.Utils.MyUtils;

@WebServlet(urlPatterns = { "/doRegisterServlet", "/doRegisterStaffServlet" })
public class DoRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DoRegisterServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);

		if (request.getServletPath().toString().equals("/doRegisterServlet")) {
			String userID = (String) request.getParameter("uid");

			String password = (String) request.getParameter("pwd");
			String name = (String) request.getParameter("custname");

			String pBuildingNo = (String) request.getParameter("pbn");
			String pStreet = (String) request.getParameter("pstreet");
			String pCity = (String) request.getParameter("pcity");
			String pState = (String) request.getParameter("pstate");
			String pZip = (String) request.getParameter("pzip");

			String dBuildingNo = (String) request.getParameter("dbn");
			String dStreet = (String) request.getParameter("dstreet");
			String dCity = (String) request.getParameter("dcity");
			String dState = (String) request.getParameter("dstate");
			String dZip = (String) request.getParameter("dzip");

			String CCName = (String) request.getParameter("ccdnm");
			String CCNumber = (String) request.getParameter("ccdno");

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			String pAddressID = userID + "P" + timestamp.getTime();
			timestamp = new Timestamp(System.currentTimeMillis());
			String dAddressID = userID + "D" + timestamp.getTime();

			CreditCard creditCard = new CreditCard();
			creditCard.setCCNumber(CCNumber);
			creditCard.setCCUsername(CCName);
			creditCard.setUserID(userID);
			creditCard.setAddressID(pAddressID);

			Address delivaryAddress = new Address();
			delivaryAddress.setAddressID(dAddressID);
			delivaryAddress.setBuildingNum(dBuildingNo);
			delivaryAddress.setCity(dCity);
			delivaryAddress.setState(dState);
			delivaryAddress.setStreet(dStreet);
			delivaryAddress.setZip(dZip);

			Address paymentAddress = new Address();
			paymentAddress.setAddressID(pAddressID);
			paymentAddress.setBuildingNum(pBuildingNo);
			paymentAddress.setCity(pCity);
			paymentAddress.setState(pState);
			paymentAddress.setStreet(pStreet);
			paymentAddress.setZip(pZip);

			CustDeliveryAddress custDeliveryAddress = new CustDeliveryAddress();
			custDeliveryAddress.setAddress(delivaryAddress);
			custDeliveryAddress.setUserID(userID);

			CustPaymentAddress custPaymentAddress = new CustPaymentAddress();
			custPaymentAddress.setAddress(paymentAddress);
			custPaymentAddress.setCreditCard(creditCard);
			custPaymentAddress.setUserID(userID);

			List<CustDeliveryAddress> custDeliveryAddresses = new ArrayList<CustDeliveryAddress>();
			custDeliveryAddresses.add(custDeliveryAddress);
			List<CustPaymentAddress> custPaymentAddresses = new ArrayList<CustPaymentAddress>();
			custPaymentAddresses.add(custPaymentAddress);

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
					DBUtils.AddCustomer(conn, userAccount);
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
						.getRequestDispatcher("/WEB-INF/views/registerView.jsp");
				dispatcher.forward(request, response);
			}

			// If everything nice.
			// Redirect to the product listing page.
			else {
				response.sendRedirect(request.getContextPath() + "/login");
			}
		}
		else if (request.getServletPath().toString().equals("/doRegisterStaffServlet")) {
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
			
			StaffMember staffMember=new StaffMember();
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
