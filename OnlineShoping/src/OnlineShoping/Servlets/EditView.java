package OnlineShoping.Servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import OnlineShoping.Beans.StaffMember;
import OnlineShoping.Beans.UserAccount;
import OnlineShoping.Utils.MyUtils;

@WebServlet(urlPatterns = { "/editProfile", "/editProfileStaff" })
public class EditView extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EditView() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		
		// Forward to /WEB-INF/views/loginView.jsp
		// (Users can not access directly into JSP pages placed in WEB-INF)
		String JSPPath = "";
		if (request.getServletPath().toString().equals("/editProfileStaff"))
		{
			HttpSession session = request.getSession();
			StaffMember staffMember = MyUtils.getLoginedForStaffUser(session);
			// Not logged in
			if (staffMember == null) {

				// Redirect to login page.
				response.sendRedirect(request.getContextPath() + "/login");
				return;
			}
			// Store info in request attribute
			request.setAttribute("user", staffMember);
			JSPPath = "/WEB-INF/views/editProfileStaffView.jsp";
		}
		else {

			HttpSession session = request.getSession();
			UserAccount loginedUser = MyUtils.getLoginedUser(session);
			// Not logged in
			if (loginedUser == null) {

				// Redirect to login page.
				response.sendRedirect(request.getContextPath() + "/login");
				return;
			}
			// Store info in request attribute
			request.setAttribute("user", loginedUser);

			JSPPath = "/WEB-INF/views/editProfileView.jsp";

		}
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(JSPPath);
		dispatcher.forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
