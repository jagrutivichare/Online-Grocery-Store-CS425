package OnlineShoping.Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import OnlineShoping.Beans.CartItems;

import OnlineShoping.Beans.Order;
import OnlineShoping.Beans.OrderDetail;
import OnlineShoping.Beans.Product;
import OnlineShoping.Beans.UserAccount;
import OnlineShoping.Utils.DBUtils;
import OnlineShoping.Utils.MyUtils;

@WebServlet(urlPatterns = { "/checkout" })
public class Checkout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Checkout() {
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

		String CCNumber = (String) request.getParameter("ddlCreditCardno");

		List<CartItems> cartItems = loginedUser.getCartItem();
		if (cartItems == null) {
			// Redirect to login page.
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		java.util.Date date = Calendar.getInstance().getTime();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Order order = new Order();
		order.setCCNumber(CCNumber);
		order.setStatus("issued");
		order.setIssuedDate(new java.sql.Date(date.getTime()));
		order.setOrderID(loginedUser.getUserID() + "C" + timestamp.getTime());
		List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
		OrderDetail orderDetail =null;
		for (CartItems cartItem : cartItems) {
			orderDetail = new OrderDetail();
			orderDetail.setProductID(cartItem.getProductID());
			orderDetail.setQuantity(cartItem.getQuantity());
			orderDetails.add(orderDetail);
		}
		order.setOrderDetails(orderDetails);

		List<Order> ordersItem = loginedUser.getOrder();
		if (ordersItem == null)
			ordersItem = new ArrayList<Order>();
		ordersItem.add(order);

		loginedUser.setOrder(ordersItem);
		MyUtils.storeLoginedUser(session, loginedUser);

		String errorString = null;

		try {
			DBUtils.PlaceOrder(conn, order, loginedUser.getUserID());

			List<CartItems> cart = null;
			try {
				cart = DBUtils.getCart(conn, loginedUser.getUserID());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (cart != null) {
				loginedUser.setCartItem(cart);
			}
			MyUtils.storeLoginedUser(session, DBUtils.findUser(conn, loginedUser.getUserID()));

		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}

		List<Product> product = null;
		try {
			product = DBUtils.queryOrderedProduct(conn, loginedUser.getUserID());
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

		RequestDispatcher dispatcher = request.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/OrderDetailView.jsp");
		dispatcher.forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
