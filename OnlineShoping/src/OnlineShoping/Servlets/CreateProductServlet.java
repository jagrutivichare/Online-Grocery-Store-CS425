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

import OnlineShoping.Beans.StaffMember;
import OnlineShoping.Beans.Warehouse;
import OnlineShoping.Beans.Supplier;
import OnlineShoping.Utils.DBUtils;
import OnlineShoping.Utils.MyUtils;

@WebServlet(urlPatterns = { "/createProduct", })
public class CreateProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CreateProductServlet() {
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
        
        List<Warehouse> warehouse=new ArrayList<Warehouse>();
        List<Supplier> supplier=new ArrayList<Supplier>();
        Connection conn = MyUtils.getStoredConnection(request);
        String errorString = null;
        
        if (errorString == null) {
			try {
				warehouse=DBUtils.queryWarehouse(conn);
				supplier=DBUtils.querySupplier(conn);
			} catch (SQLException e) {
				e.printStackTrace();
				errorString = e.getMessage();
			}
		}
    
    	request.setAttribute("errorString", errorString);
        request.setAttribute("supplierList", supplier);
    	request.setAttribute("warehouseList", warehouse);
		
		RequestDispatcher dispatcher = request.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/createProductView.jsp");
		dispatcher.forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}