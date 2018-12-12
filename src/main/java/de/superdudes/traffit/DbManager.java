package de.superdudes.traffit;


public class DbManager{

	private static class Singletons {

		private static final DbManager INSTANCE = new DbManager();
	}

	public static DbManager instance() {
		return Singletons.INSTANCE;
	}
	
	String driver = "org.mariadb.jdbc";
	
	
	public void connection()
	{
	   try
	   {
		Class.forName(driver);
	   } 
	   catch (ClassNotFoundException ex)
	   {
	     ex.printStackTrace();
	   }	
	
	 }



	
}           
