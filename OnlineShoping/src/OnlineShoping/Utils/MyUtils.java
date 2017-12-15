package OnlineShoping.Utils;

import java.sql.Connection;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import OnlineShoping.Beans.StaffMember;
import OnlineShoping.Beans.UserAccount;

public class MyUtils {

	public static final String ATT_NAME_CONNECTION = "ATTRIBUTE_FOR_CONNECTION";

	private static final String ATT_NAME_USER_NAME = "ATTRIBUTE_FOR_STORE_USER_NAME_IN_COOKIE";
	private static final String ATT_NAME_USER_STAFF_NAME = "ATTRIBUTE_FOR_STORE_USER_STAFF_NAME_IN_COOKIE";

	// Store Connection in request attribute.
	// (Information stored only exist during requests)
	public static void storeConnection(ServletRequest request, Connection conn) {
		request.setAttribute(ATT_NAME_CONNECTION, conn);
	}

	// Get the Connection object has been stored in one attribute of the
	// request.
	public static Connection getStoredConnection(ServletRequest request) {
		Connection conn = (Connection) request.getAttribute(ATT_NAME_CONNECTION);
		return conn;
	}

	// *********************this is for
	// CustomerLogin************************************

	// Store user info in Session.
	public static void storeLoginedUser(HttpSession session, UserAccount loginedUser) {

		// On the JSP can access ${loginedUser}
		session.setAttribute("loginedUser", loginedUser);
		
	}

	// Get the user information stored in the session.
	public static UserAccount getLoginedUser(HttpSession session) {
		UserAccount loginedUser = (UserAccount) session.getAttribute("loginedUser");
		return loginedUser;
	}

	// Store info in Cookie
	public static void storeUserCookie(HttpServletResponse response, UserAccount user) {
		System.out.println("Store user cookie");
		Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, user.getUserID());

		// 1 day (Convert to seconds)
		cookieUserName.setMaxAge(24 * 60 * 60);
		response.addCookie(cookieUserName);
	}

	public static String getUserNameInCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (ATT_NAME_USER_NAME.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	// Delete cookie.
	public static void deleteUserCookie(HttpServletResponse response) {
		Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, null);

		// 0 seconds (Expires immediately)
		cookieUserName.setMaxAge(0);
		response.addCookie(cookieUserName);
	}

	// ************************** Customer Login code ends
	// here*******************************

	// ****************************************This is for staff
	// login*************************************

	// Store user info in Session.
	public static void storeStaffLoginedUser(HttpSession session, StaffMember loginedUser) {

		// On the JSP can access ${loginedUser}
		session.setAttribute("loginedUserStaff", loginedUser);
	}

	public static StaffMember getLoginedForStaffUser(HttpSession session) {
		StaffMember loginedUserStaff = (StaffMember) session.getAttribute("loginedUserStaff");
		return loginedUserStaff;
	}

	// Store info in Cookie
	public static void storeStaffUserCookie(HttpServletResponse response, StaffMember user) {
		System.out.println("Store user cookie");
		Cookie cookieUserName = new Cookie(ATT_NAME_USER_STAFF_NAME, user.getUserID());

		// 1 day (Convert to seconds)
		cookieUserName.setMaxAge(24 * 60 * 60);
		response.addCookie(cookieUserName);
	}

	public static String getStaffUserNameInCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (ATT_NAME_USER_STAFF_NAME.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	// Delete cookie.
	public static void deleteStaffUserCookie(HttpServletResponse response) {
		Cookie cookieUserName = new Cookie(ATT_NAME_USER_STAFF_NAME, null);

		// 0 seconds (Expires immediately)
		cookieUserName.setMaxAge(0);
		response.addCookie(cookieUserName);
	}
	// ****************************************************************************************************

	public static String maskCardNumber(String cardNumber) {
		String existingCCNmbr = "cardNumber";
	    int i = 0;
	    StringBuffer temp = new StringBuffer();
	    while(i < (existingCCNmbr .length())){
	        if(i > existingCCNmbr .length() -2){
	            temp.append(existingCCNmbr.charAt(i));
	        } else {
	            temp.append("X");
	        }
	        i++;
	    }
	      return temp.toString();
	}

}