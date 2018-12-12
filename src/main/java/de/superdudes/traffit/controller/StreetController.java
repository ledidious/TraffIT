package de.superdudes.traffit.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.superdudes.traffit.dto.SimulationObject;
import de.superdudes.traffit.dto.Street;

public class StreetController extends AbstractController<Street>  {

	private static class Singletons {

		private static final StreetController INSTANCE = new StreetController();
	}

	public static StreetController instance() {
		return Singletons.INSTANCE;
	}
	
	
	@Override
	public void save(Street object) 
	{
		  Connection myConn = null;
		 try 
			{
			     myConn = DriverManager.getConnection(url);
				
				Statement myStmt = myConn.createStatement();
				
			    String sql = " INSERT INTO STREET(s_id, nr, s_length) "
			    		   + " VALUES ('"+ object.getId() + object.getNr() + object.getLength() +"')" ;
			    
				
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
			    System.out.println("Verbindung konnte nicht beeendet werden !!!!!");
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
	public void load(Street object) 
	{
	
		Connection myConn = null;
		try 
		{
			  
			myConn = DriverManager.getConnection(url);
			
			Statement myStmt = myConn.createStatement();
			    
		    String sql  = "SELECT * FROM STREET";
		    
		    ResultSet  result = myStmt.executeQuery(sql);
		    
		       while(result.next())
		       {
		    	Integer s_id = result.getInt(1);
		    	Integer nr   = result.getInt(2);
		        Integer s_length  = result.getInt(3);
		    	
		    	  object.setId(s_id);
			      object.setNr(nr);
			      object.setLength(s_length);
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
			System.out.println("Verbindung konnte nicht beeendet werden !!!!!");
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
