package OnlineShoping.Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import OnlineShoping.Beans.StaffMember;
import OnlineShoping.Beans.Supplier;
import OnlineShoping.Utils.DBUtils;
import OnlineShoping.Utils.MyUtils;


@WebServlet(urlPatterns = { "/editSupplier" })
public class EditSupplierServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EditSupplierServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		 // Check User has logged on
        StaffMember loginedUser = MyUtils.getLoginedForStaffUser(session);
        // Not logged in
        if (loginedUser == null) {
            // Redirect to login page.
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
		
		Connection conn = MyUtils.getStoredConnection(request);

		String supplierID = (String) request.getParameter("supplierID");

		Supplier supplier = null;

		String errorString = null;

		try {
			supplier = DBUtils.querySupplier(conn, supplierID);
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}

		// If no error.
		// The product does not exist to edit.
		// Redirect to productList page.
		if (errorString != null && supplier == null) {
			response.sendRedirect(request.getServletPath() + "/supplierList");
			return;
		}

		// Store errorString in request attribute, before forward to views.
		request.setAttribute("errorString", errorString);
		request.setAttribute("supplier", supplier);

		RequestDispatcher dispatcher = request.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/editSupplierView.jsp");
		dispatcher.forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}


