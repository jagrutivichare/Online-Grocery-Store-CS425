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
import OnlineShoping.Beans.Warehouse;
import OnlineShoping.Utils.DBUtils;
import OnlineShoping.Utils.MyUtils;


@WebServlet(urlPatterns = { "/editWarehouse" })
public class EditWarehouseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EditWarehouseServlet() {
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

		String warehouseName = (String) request.getParameter("warehouseName");

		Warehouse warehouse = null;

		String errorString = null;

		try {
			warehouse = DBUtils.queryWarehouse(conn, warehouseName);
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}

		// If no error.
		// The product does not exist to edit.
		// Redirect to productList page.
		if (errorString != null && warehouse == null) {
			response.sendRedirect(request.getServletPath() + "/warehouseList");
			return;
		}

		// Store errorString in request attribute, before forward to views.
		request.setAttribute("errorString", errorString);
		request.setAttribute("warehouse", warehouse);

		RequestDispatcher dispatcher = request.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/editWarehouseView.jsp");
		dispatcher.forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

