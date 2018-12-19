package de.superdudes.traffit;

import java.sql.*;


public class DbManager{

	private static class Singletons {

		private static final DbManager INSTANCE = new DbManager();
	}

	public static DbManager instance() {
		return Singletons.INSTANCE;
	}

	String  url = "jdbc:mariadb://localhost/TraffIT";
	String  user = "TraffIT";
	String  pw  = "pw";
	
	public  void getConnection()
	{
		 try
		    {
		      Class.forName( "org.mariadb.jdbc" );
		    }
		    catch ( ClassNotFoundException e )
		    {
		      System.err.println( "Keine Treiber-Klasse!" );
		      return;
		    }

		
		try
		{
			Connection myConn = DriverManager.getConnection(url, user, pw);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	
}           
