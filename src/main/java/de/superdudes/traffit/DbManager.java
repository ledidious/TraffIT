package de.superdudes.traffit;

import java.sql.*;

public class DbManager {

	private static final String url = "jdbc:mariadb://localhost/TraffIT";
	private static final String user = "TraffIT";
	private static final String pw = "TraffIT";

	private static class Singletons {

		private static final DbManager INSTANCE = new DbManager();
	}

	public static DbManager instance() {
		return Singletons.INSTANCE;
	}

	private Connection connection;

	public Connection getConnection() {

		if (connection == null) {
			try {
				connection = DriverManager.getConnection(url, user, pw);
			} catch (SQLException e) {
				throw new InternalError("Database connection cannot be established", e);
			}
		}
		
		return connection;
	}

	@Override
	protected void finalize() throws Throwable {
		if (connection != null) {
			connection.close();
		}
		super.finalize();
	}
}
