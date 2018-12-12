package de.superdudes.traffit.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.superdudes.traffit.dto.Lane;
import de.superdudes.traffit.dto.SimulationObject;
import de.superdudes.traffit.dto.Street;

public class LaneController extends AbstractController<Lane> {

	private static class Singletons {

		private static final LaneController INSTANCE = new LaneController();
	}

	public static LaneController instance() {
		return Singletons.INSTANCE;
	}
	
	@Override
	public void save(Lane object) 
	{
		  Connection myConn = null;
		 try 
			{
			     myConn = DriverManager.getConnection(url);
				
				Statement myStmt = myConn.createStatement();
				
			    String sql = "UPDATE LANE SET"
			    		 +    " l_id = ('"+ object.getId() +"')"
			    		 +    " nr = ('"+ object.getNr() +"')";
				
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
	public void load(Lane object) 
	{
	
		Connection myConn = null;
		try 
		{
			  
			myConn = DriverManager.getConnection(url);
			
			Statement myStmt = myConn.createStatement();
			    
		    String sql  = "SELECT * FROM LANE";
		    
		    ResultSet  result = myStmt.executeQuery(sql);
		    
		       while(result.next())
		       {
		    	Integer l_id = result.getInt(1);
		    	Integer nr   = result.getInt(2);
		    
		    	
		    	  object.setId(l_id);
			      object.setNr(nr);
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
