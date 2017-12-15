package OnlineShoping.Servlets;

import java.io.IOException;
import java.sql.Blob;
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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import OnlineShoping.Beans.Product;
import OnlineShoping.Beans.StaffMember;
import OnlineShoping.Beans.Supplier;
import OnlineShoping.Beans.Warehouse;
import OnlineShoping.Utils.DBUtils;
import OnlineShoping.Utils.MyUtils;

@WebServlet(urlPatterns = { "/doEditProduct" })
public class DoEditProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DoEditProductServlet() {
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

		try {
			response.setContentType("text/html;charset=UTF-8");
			Connection conn = MyUtils.getStoredConnection(request);

			// Apache Commons-Fileupload library classes
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload sfu = new ServletFileUpload(factory);

			if (!ServletFileUpload.isMultipartContent(request)) {
				System.out.println("sorry. No file uploaded");
				return;
			}

			// parse request
			List items = sfu.parseRequest(request);
			FileItem id = (FileItem) items.get(0);
			String productID = id.getString();

			FileItem name = (FileItem) items.get(1);
			String productName = name.getString();

			FileItem desc = (FileItem) items.get(2);
			String description = desc.getString();

			FileItem type = (FileItem) items.get(3);
			String Type = type.getString();

			FileItem pprice = (FileItem) items.get(4);
			String prodprice = pprice.getString();

			FileItem ps = (FileItem) items.get(6);
			String state = ps.getString();

			FileItem st = (FileItem) items.get(5);
			String prodSize = st.getString();

			FileItem quan = (FileItem) items.get(7);
			String quant = quan.getString();

			FileItem sup = (FileItem) items.get(8);
			String supplier = sup.getString();

			FileItem sp = (FileItem) items.get(9);
			String supplierPrice = sp.getString();

			FileItem whouse = (FileItem) items.get(10);
			String warehouse = whouse.getString();

			// get uploaded file
			FileItem file = (FileItem) items.get(11);

			float price = 0;
			try {
				price = Float.parseFloat(prodprice);
			} catch (Exception e) {
			}

			int size = 0;
			try {
				size = Integer.parseInt(prodSize);
			} catch (Exception e) {
			}

			int quantity = 0;
			try {
				quantity = Integer.parseInt(quant);
			} catch (Exception e) {
			}

			Product product = new Product();
			product.setDescription(description);
			product.setProductID(productID);
			product.setProductImage(file);
			product.setProductPrice(price);
			product.setProductSize(size);
			product.setProducttType(Type);
			product.setProductName(productName);
			product.setState(state);
			product.setQuantity(quantity);
			product.setSupplier(supplier);
			product.setWarehouse(warehouse);
			product.setSupplierPrice(supplierPrice);
			Boolean isfileUploaded = false;
			if (file.getSize() == 0)
				product.setpImage((Blob) session.getAttribute("productImage"));
			else {
				product.setProductImage(file);
				isfileUploaded=true;
			}
			String errorString = null;

			List<Warehouse> warehouseList = new ArrayList<Warehouse>();
			List<Supplier> supplierList = new ArrayList<Supplier>();

			try {
				warehouseList = DBUtils.queryWarehouse(conn);
				supplierList = DBUtils.querySupplier(conn);
			} catch (SQLException e) {
				e.printStackTrace();
				errorString = e.getMessage();
			}

			for (Warehouse wh : warehouseList) {
				if (wh.getWarehouseName().equals(warehouse)) {
					Product pdt = DBUtils.findProduct(conn, productID);
					float count = wh.getCapacity()
							- (DBUtils.CountProductWithSuplierOrWarehouse(conn, "warehouse", warehouse)
									- pdt.getQuantity());
					if (count < (float) quantity) {
						errorString = "The total available capacity for " + warehouse + " is " + count;
					}
					break;
				}
			}

			// Product ID is the string literal [a-zA-Z_0-9]
			// with at least 1 character
			String regex = "\\w+";

			if (productID == null || !productID.matches(regex)) {
				errorString = "Product Code invalid!";
			}

			if (errorString == null) {
				try {
					DBUtils.deleteProduct(conn, productID, state);
					DBUtils.insertProduct(conn, product, isfileUploaded);
				} catch (SQLException e) {
					e.printStackTrace();
					errorString = e.getMessage();
				}
			}

			// Store infomation to request attribute, before forward to views.
			request.setAttribute("supplierList", supplierList);
			request.setAttribute("warehouseList", warehouseList);
			request.setAttribute("errorString", errorString);
			request.setAttribute("product", product);

			// If error, forward to Edit page.
			if (errorString != null) {
				RequestDispatcher dispatcher = request.getServletContext()
						.getRequestDispatcher("/WEB-INF/views/editProductView.jsp");
				dispatcher.forward(request, response);
			}

			// If everything nice.
			// Redirect to the product listing page.
			else {
				response.sendRedirect(request.getContextPath() + "/productList");
			}
		} catch (Exception ex) {
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
