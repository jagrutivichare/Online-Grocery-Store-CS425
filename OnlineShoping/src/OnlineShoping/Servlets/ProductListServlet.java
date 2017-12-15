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

import OnlineShoping.Beans.Product;
import OnlineShoping.Beans.StaffMember;
import OnlineShoping.Beans.UserAccount;
import OnlineShoping.Utils.DBUtils;
import OnlineShoping.Utils.MyUtils;

@WebServlet(urlPatterns = { "/productList" })
public class ProductListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ProductListServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		HttpSession session = request.getSession();

		String checkUserLogin = "NormalUser";

		// Check User has logged on
		StaffMember StaffloginedUser = MyUtils.getLoginedForStaffUser(session);
		// Not logged in
		if (StaffloginedUser != null) {
			// Redirect to login page.
			checkUserLogin = "Admin";
		}
		String userID = "";
		try {
			UserAccount loggedinUser = MyUtils.getLoginedUser(session);
			if (loggedinUser != null) {
				userID = loggedinUser.getUserID();
			}
		} catch (Exception ex) {
		}

		String errorString = null;
		List<Product> list = null;
		session.setAttribute("searchParam", "");
		if (request.getParameter("warehouse") != null) {
			try {
				list = DBUtils.queryProductWithSuplierOrWarehouse(conn, "warehouse",
						(String) request.getParameter("warehouse"));
			} catch (SQLException e) {
				e.printStackTrace();
				errorString = e.getMessage();
			}
		} else if (request.getParameter("supplier") != null) {
			try {
				list = DBUtils.queryProductWithSuplierOrWarehouse(conn, "supplier",
						(String) request.getParameter("supplier"));
			} catch (SQLException e) {
				e.printStackTrace();
				errorString = e.getMessage();
			}
		} else if (request.getParameter("searchParam") == null) {
			try {
				list = DBUtils.queryProduct(conn, checkUserLogin, userID);
			} catch (SQLException e) {
				e.printStackTrace();
				errorString = e.getMessage();
			}
		}

		else if (((String) request.getParameter("searchParam")).trim() == "") {
			try {
				list = DBUtils.queryProduct(conn, checkUserLogin, userID);
			} catch (SQLException e) {
				e.printStackTrace();
				errorString = e.getMessage();
			}
		} else {
			try {
				session.setAttribute("searchParam", ((String) request.getParameter("searchParam")).trim());
				list = DBUtils.queryProduct(conn, ((String) request.getParameter("searchParam")).trim(), checkUserLogin,
						userID);
			} catch (SQLException e) {
				e.printStackTrace();
				errorString = e.getMessage();
			}
		}

		// Store info in request attribute, before forward to views
		request.setAttribute("checkUserLogin", checkUserLogin);
		request.setAttribute("errorString", errorString);
		request.setAttribute("productList", list);

		// Forward to /WEB-INF/views/productListView.jsp
		RequestDispatcher dispatcher = request.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/productListView.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
