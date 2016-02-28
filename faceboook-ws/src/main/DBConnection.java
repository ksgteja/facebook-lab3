package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private Connection conn = null;

	// JDBC driver name and database URL
	private static final String	DRIVER	= "com.mysql.jdbc.Driver";
	private static final String	DB_URL	= "jdbc:mysql://localhost/facebook";

	// Database credentials
	static final String	USER	= "root";
	static final String	PASS	= "";

	public Connection getConnection () {
		try {
			// STEP 2: Register JDBC driver
			Class.forName(DRIVER);

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

		} catch ( SQLException se ) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch ( Exception e ) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}
		return conn;
	}

	public void close () {
		try {
			if ( conn != null )
				conn.close();
		} catch ( SQLException se ) {
			se.printStackTrace();
		}
	}
}
