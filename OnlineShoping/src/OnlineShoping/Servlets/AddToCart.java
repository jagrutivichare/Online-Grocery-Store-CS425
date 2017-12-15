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

import OnlineShoping.Beans.CartItems;
import OnlineShoping.Beans.Product;
import OnlineShoping.Beans.UserAccount;
import OnlineShoping.Utils.DBUtils;
import OnlineShoping.Utils.MyUtils;

@WebServlet(urlPatterns = { "/addToCart" })
public class AddToCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddToCart() {
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
		String numberOFItem = (String) request.getParameter("numberOFItem");

		CartItems cartItem = new CartItems();
		cartItem.setProductID(productID);
		cartItem.setQuantity(Integer.parseInt(numberOFItem));

		List<CartItems> cartItems = loginedUser.getCartItem();
		if (cartItems == null)
			cartItems = new ArrayList<CartItems>();
		cartItems.add(cartItem);
		loginedUser.setCartItem(cartItems);
		MyUtils.storeLoginedUser(session, loginedUser);

		
		String errorString = null;

		try {
			DBUtils.AddToCart(conn, cartItem, loginedUser.getUserID());
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}
		
		List<Product> product = null;
		try {
			if (session.getAttribute("searchParam") != null)
				product = DBUtils.queryProduct(conn, ((String) session.getAttribute("searchParam")).trim(),"NormalUser",loginedUser.getUserID());
			else
				product = DBUtils.queryProduct(conn,"NormalUser",loginedUser.getUserID());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		// If no error.
		// The product does not exist to edit.
		// Redirect to productList page.
		if (errorString != null && product == null) {
			response.sendRedirect(request.getServletPath() + "/productList");
			return;
		}

		// Store errorString in request attribute, before forward to views.
		request.setAttribute("errorString", errorString);
		request.setAttribute("productList", product);
		request.setAttribute("checkUserLogin", "NormalUser");

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
