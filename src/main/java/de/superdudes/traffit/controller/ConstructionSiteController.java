package de.superdudes.traffit.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.superdudes.traffit.dto.ConstructionSite;
import de.superdudes.traffit.dto.SimulationObject;

public class ConstructionSiteController extends AbstractController <ConstructionSite> {

	private static class Singletons {

		private static final ConstructionSiteController INSTANCE = new ConstructionSiteController();
	}

	public static ConstructionSiteController instance() {
		return Singletons.INSTANCE;
	}
	
	@Override
	public void save(ConstructionSite object) 
	{
		  Connection myConn = null;  
		 try 
			{
			     myConn = DriverManager.getConnection(url);
				
				Statement myStmt = myConn.createStatement();
				
			    String sql = "UPDATE CONSTRUCTIONSITE SET"
			    		  +   "cs_id = ('"+ object.getId() +"')"
			    		  +   " nr = ('"+ object.getNr() +"')"
			    		  +   " cs_length = ('"+ object.getLength() +"')";
				
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
	public void load(ConstructionSite object) 
	{
	
		Connection myConn = null;
		try 
		{
			  
			myConn = DriverManager.getConnection(url);
			
			Statement myStmt = myConn.createStatement();
			    
		    String sql  = "SELECT * FROM CONSTRUCTIONSITE";
		    
		    ResultSet  result = myStmt.executeQuery(sql);
		    
		       while(result.next())
		       {
		    	Integer cs_id = result.getInt(1);
		    	Integer nr   = result.getInt(2);
		        Integer cs_length  = result.getInt(3);
		    	
		    	  object.setId(cs_id);
			      object.setNr(nr);
			      object.setLength(cs_length);
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
