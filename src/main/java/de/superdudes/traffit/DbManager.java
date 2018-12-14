package de.superdudes.traffit;

import java.sql.*;


public class DbManager{

	private static class Singletons {

		private static final DbManager INSTANCE = new DbManager();
	}

	public static DbManager instance() {
		return Singletons.INSTANCE;
	}
	
	public static Connection myConn = null;
		
	public static Connection getConnection()
	{
		String url = "jdbc:mariadb://localhost/TraffIT";
		
	try
	{
          myConn = DriverManager.getConnection(url);
          return myConn;
	}
	catch (SQLException ex)
	{
		ex.printStackTrace();
		System.out.println("Verbindung konnte nicht aufgebaut werden!!!");
	}
	
	return myConn;
	
	}
	
}           
