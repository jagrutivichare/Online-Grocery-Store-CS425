package OnlineShoping.Conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLServerConnUtils_SQLJDBC {

	// Connect to SQLServer
	// (Using JDBC Driver: SQLJDBC)
	public static Connection getSQLServerConnection_SQLJDBC() throws ClassNotFoundException, SQLException {

		// Note: Change the connection parameters accordingly.

		String hostName = "localhost";
		String sqlInstanceName = "SQLEXPRESS";
		String database = "mytest";
		String userName = "sa";
		String password = "12345";

		return getSQLServerConnection_SQLJDBC(hostName, sqlInstanceName, database, userName, password);
	}

	// Connect to SQLServer, using SQLJDBC Library.
	private static Connection getSQLServerConnection_SQLJDBC(String hostName, String sqlInstanceName, String database,
			String userName, String password) throws ClassNotFoundException, SQLException {

		// Declare the class Driver for SQLServer DB
		// This is necessary with Java 5 (or older)
		// Java6 (or newer) automatically find the appropriate driver.
		// If you use Java> 5, then this line is not needed.
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

		// jdbc:sqlserver://ServerIp:1433/SQLEXPRESS;databaseName=simplehr
		String connectionURL = "jdbc:sqlserver://" + hostName + ":1433" + ";instance=" + sqlInstanceName
				+ ";databaseName=" + database;

		Connection conn = DriverManager.getConnection(connectionURL, userName, password);
		return conn;
	}

}
