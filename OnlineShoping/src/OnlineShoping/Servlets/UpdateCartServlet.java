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
import OnlineShoping.Beans.Product;
import OnlineShoping.Beans.UserAccount;
import OnlineShoping.Utils.DBUtils;
import OnlineShoping.Utils.MyUtils;

@WebServlet(urlPatterns = { "/updateCart" })
public class UpdateCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UpdateCartServlet() {
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

		String productID = (String) request.getParameter("hdnProductId");
		
		int quantity = Integer.parseInt((String) request.getParameter("numberOFItem"));

		CartItems cartItem = new CartItems();
		cartItem.setProductID(productID);
		cartItem.setQuantity(quantity);
		String errorString = null;

		try {
			DBUtils.UpdateCart(conn, cartItem,loginedUser.getUserID());
			List<CartItems> cartItems = DBUtils.getCart(conn, loginedUser.getUserID());
			loginedUser.setCartItem(cartItems);
			MyUtils.storeLoginedUser(session, loginedUser);

		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}

		List<Product> product = null;
		try {
			product = DBUtils.queryCartProduct(conn, loginedUser.getUserID());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		request.setAttribute("errorString", errorString);
		request.setAttribute("productList", product);

		// Forward to /WEB-INF/views/productListView.jsp
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/CartView.jsp");
		dispatcher.forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
