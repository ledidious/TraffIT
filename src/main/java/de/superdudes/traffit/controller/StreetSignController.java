package de.superdudes.traffit.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.superdudes.traffit.dto.SimulationObject;
import de.superdudes.traffit.dto.StreetSign;

public class StreetSignController extends AbstractController<StreetSign> {

	private static class Singletons {

		private static final StreetSignController INSTANCE = new StreetSignController();
	}

	public static StreetSignController instance() {
		return Singletons.INSTANCE;
	}
  
	@Override
	public void save(StreetSign object) 
	{
		  Connection myConn = null;
		 try 
			{
			     myConn = DriverManager.getConnection(url);
				
				Statement myStmt = myConn.createStatement();
				
			    String sql = "UPDATE STREET_SIGN SET"
			    		  +   " ss_id = ('"+ object.getId() +"')"
			    		  +   " nr = ('"+ object.getNr() +"')"
			    		  +   " speedLimit = ('"+ object.getSpeedLimit() +"')";
				
				myStmt.executeUpdate(sql);
				
				
			}
			
	        catch (SQLException ex) 
			{
	          ex.printStackTrace();
		      System.out.println("Eintragen der Daten fehlgeschlagen!!!");
		    }
		 
		 finally 
			{
			  try 
			  {
				myConn.close();
			  } 
			  catch (SQLException e)
			  {
				System.out.println("Verbindung konnte nicht beeendet werden !!!!!");
				e.printStackTrace();
	          }
			}
	}
	
	
	@Override
	public void load(StreetSign object) 
	{
	
		Connection myConn = null;
		try 
		{
			  
			myConn = DriverManager.getConnection(url);
			
			Statement myStmt = myConn.createStatement();
			    
		    String sql  = "SELECT * FROM STREET_SIGN";
		    
		    ResultSet  result = myStmt.executeQuery(sql);
		    
		       while(result.next())
		       {
		    	Integer ss_id = result.getInt(1);
		    	Integer nr   = result.getInt(2);
		        Integer speedLimit  = result.getInt(3);
		    	
		    	  object.setId(ss_id);
			      object.setNr(nr);
			      object.setSpeedLimit(speedLimit);
			   }
		  
	    } 
		catch (SQLException ex) 
		{
			ex.printStackTrace();
			System.out.print("Laden der Daten nicht möglich!!!");
		}
		finally 
		{
		  try 
		  {
			myConn.close();
		  } 
		  catch (SQLException e)
		  {
			System.out.println("Verbindung konnte nicht beeendet werden !!!!!");
			e.printStackTrace();
          }
		}
		
	 }
}
