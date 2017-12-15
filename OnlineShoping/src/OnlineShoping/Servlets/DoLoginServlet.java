package OnlineShoping.Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import OnlineShoping.Beans.CartItems;
import OnlineShoping.Beans.StaffMember;
import OnlineShoping.Beans.UserAccount;
import OnlineShoping.Utils.DBUtils;
import OnlineShoping.Utils.MyUtils;

@WebServlet(urlPatterns = { "/doLogin" })
public class DoLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DoLoginServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String rememberMeStr = request.getParameter("rememberMe");
		boolean remember = "Y".equals(rememberMeStr);

		UserAccount user = null;
		StaffMember staffMember = null;
		boolean hasError = false;
		String errorString = null;

		if (userName == null || password == null || userName.length() == 0 || password.length() == 0) {
			hasError = true;
			errorString = "Required username and password!";
		} else {
			Connection conn = MyUtils.getStoredConnection(request);
			try {

				user = DBUtils.findUser(conn, userName, password);

				staffMember = DBUtils.findStaffUser(conn, userName, password);

				if (user == null && staffMember == null) {
					hasError = true;
					errorString = "User Name or password invalid";
				}
			} catch (SQLException e) {
				e.printStackTrace();
				hasError = true;
				errorString = e.getMessage();
			}
		}

		// If error, forward to /WEB-INF/views/login.jsp
		if (hasError) {
			user = new UserAccount();
			user.setUserID(userName);
			user.setPassword(password);

			// Store information in request attribute, before forward.
			request.setAttribute("errorString", errorString);
			request.setAttribute("user", user);

			// Forward to /WEB-INF/views/login.jsp
			RequestDispatcher dispatcher //
					= this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");

			dispatcher.forward(request, response);
		}

		// If no error
		// Store user information in Session
		// And redirect to userInfo page.
		else {
			if (user != null) {
				HttpSession session = request.getSession();
				Connection conn = MyUtils.getStoredConnection(request);
				List<CartItems> cartItems=null;
				try {
					cartItems = DBUtils.getCart(conn, user.getUserID());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (cartItems != null) {
					user.setCartItem(cartItems);
				}
				MyUtils.storeLoginedUser(session, user);

				// If user checked "Remember me".
				if (remember) {
					MyUtils.storeUserCookie(response, user);
				}

				// Else delete cookie.
				else {
					MyUtils.deleteUserCookie(response);
				}

				// Redirect to userInfo page.
				response.sendRedirect(request.getContextPath() + "/productList");
			} else if (staffMember != null) {
				HttpSession session = request.getSession();
				MyUtils.storeStaffLoginedUser(session, staffMember);

				// If user checked "Remember me".
				if (remember) {
					MyUtils.storeStaffUserCookie(response, staffMember);
				}

				// Else delete cookie.
				else {
					MyUtils.deleteStaffUserCookie(response);
				}

				// Redirect to userInfo page.
				response.sendRedirect(request.getContextPath() + "/productList");
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
