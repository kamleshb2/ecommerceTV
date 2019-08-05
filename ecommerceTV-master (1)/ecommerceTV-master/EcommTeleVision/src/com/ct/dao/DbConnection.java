package com.ct.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.ct.exception.UserException;

public class DbConnection {
	static Connection conn = null;
	
	public static Connection getConnection() throws UserException {

		try {
			conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Television","sa","123");
		} catch (SQLException e) {
			throw new UserException("Internal Error: Database connectivity issue. Try after some time.");
		}

		return conn;
	}

}
