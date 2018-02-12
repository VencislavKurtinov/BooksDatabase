package com.booksDB.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static DBConnection connectionInstance = null;
	private static Connection con = null;

	private static final String DB_PASSWORD = "123456";
	private static final String DB_USER = "root";
	private static final String DATABASE = "booksdatabase";
	private static final String DB_HOST = "localhost";
	private static final String DB_PORT = "3306";
	private static final String DB_URL = DB_HOST + ":" + DB_PORT;

	private DBConnection() {
		try {
			 Class.forName("com.mysql.jdbc.Driver");
			DBConnection.con = DriverManager.getConnection("jdbc:mysql://" + DB_URL + "/" + DATABASE
					+ "?verifyServerCertificate=false" + "&useSSL=false" + "&requireSSL=false", DB_USER, DB_PASSWORD);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return con;
	}

	public static DBConnection getInstance() {
		synchronized (DBConnection.class) {

			if (connectionInstance == null) {
				connectionInstance = new DBConnection();
			}
		}

		return connectionInstance;
	}

	public void closeConnection() {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
