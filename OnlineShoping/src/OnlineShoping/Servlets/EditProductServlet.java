package OnlineShoping.Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
import OnlineShoping.Beans.Supplier;
import OnlineShoping.Beans.Warehouse;
import OnlineShoping.Utils.DBUtils;
import OnlineShoping.Utils.MyUtils;

@WebServlet(urlPatterns = { "/editProduct" })
public class EditProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EditProductServlet() {
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

		String productID = (String) request.getParameter("productID");

		Product product = null;

		String errorString = null;

		try {
			product = DBUtils.findProduct(conn, productID);
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}

		// If no error.
		// The product does not exist to edit.
		// Redirect to productList page.
		if (errorString != null && product == null) {
			RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/editProductView.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		List<Warehouse> warehouse = new ArrayList<Warehouse>();
		List<Supplier> supplier = new ArrayList<Supplier>();

		try {
			warehouse = DBUtils.queryWarehouse(conn);
			supplier = DBUtils.querySupplier(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}


		// Store errorString in request attribute, before forward to views.
		request.setAttribute("supplierList", supplier);
		request.setAttribute("warehouseList", warehouse);
		request.setAttribute("errorString", errorString);
		request.setAttribute("product", product);

		RequestDispatcher dispatcher = request.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/editProductView.jsp");
		dispatcher.forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
