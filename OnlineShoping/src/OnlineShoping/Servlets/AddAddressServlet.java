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
import OnlineShoping.Beans.CartItems;
import OnlineShoping.Beans.CreditCard;
import OnlineShoping.Beans.CustDeliveryAddress;
import OnlineShoping.Beans.CustPaymentAddress;
import OnlineShoping.Beans.Product;
import OnlineShoping.Beans.UserAccount;
import OnlineShoping.Utils.DBUtils;
import OnlineShoping.Utils.MyUtils;

@WebServlet(urlPatterns = { "/addDelAddress", "/addPayAddress" })
public class AddAddressServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddAddressServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		UserAccount loginedUser = MyUtils.getLoginedUser(session);

		// Not logged in
		if (loginedUser == null) {

			// Redirect to login page.
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		Connection conn = MyUtils.getStoredConnection(request);
	
		if (request.getServletPath().toString().equals("/addDelAddress")) {

			String dBuildingNo = (String) request.getParameter("dbn");
			String dStreet = (String) request.getParameter("dstreet");
			String dCity = (String) request.getParameter("dcity");
			String dState = (String) request.getParameter("dstate");
			String dZip = (String) request.getParameter("dzip");
			String userID = loginedUser.getUserID();

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			String dAddressID = userID + "D" + timestamp.getTime();

			Address delivaryAddress = new Address();
			delivaryAddress.setAddressID(dAddressID);
			delivaryAddress.setBuildingNum(dBuildingNo);
			delivaryAddress.setCity(dCity);
			delivaryAddress.setState(dState);
			delivaryAddress.setStreet(dStreet);
			delivaryAddress.setZip(dZip);

			CustDeliveryAddress custDeliveryAddress = new CustDeliveryAddress();
			custDeliveryAddress.setAddress(delivaryAddress);
			custDeliveryAddress.setUserID(userID);
			List<CustDeliveryAddress> custDeliveryAddresses = new ArrayList<CustDeliveryAddress>();
			custDeliveryAddresses.add(custDeliveryAddress);
            
			try {
				DBUtils.AddCustDeliveryAddresses(conn, custDeliveryAddresses);
				UserAccount user = DBUtils.findUser(conn, loginedUser.getUserID());
				MyUtils.storeLoginedUser(session, user);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/editProfile");
			dispatcher.forward(request, response);
			return;

		} else if (request.getServletPath().toString().equals("/addPayAddress")) {
   
			String pBuildingNo = (String) request.getParameter("pbn");
			String pStreet = (String) request.getParameter("pstreet");
			String pCity = (String) request.getParameter("pcity");
			String pState = (String) request.getParameter("pstate");
			String pZip = (String) request.getParameter("pzip");
			
			String CCName = (String) request.getParameter("ccdnm");
			String CCNumber = (String) request.getParameter("ccdno");
			String userID = loginedUser.getUserID();
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			String pAddressID = userID + "P" + timestamp.getTime();
		
			
			
			
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
			
			List<CustPaymentAddress> custPaymentAddresses = new ArrayList<CustPaymentAddress>();
			custPaymentAddresses.add(custPaymentAddress);
			
			try {
				DBUtils.AddCustPayementAddresses(conn, custPaymentAddresses);
				UserAccount user = DBUtils.findUser(conn, loginedUser.getUserID());
				MyUtils.storeLoginedUser(session, user);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/editProfile");
			dispatcher.forward(request, response);
			
		} else {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
