package de.superdudes.traffit.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.superdudes.traffit.dto.Cell;
import de.superdudes.traffit.dto.SimulationObject;

public class CellController extends AbstractController <Cell> {

	private static class Singletons {

		private static final CellController INSTANCE = new CellController();
	}

	public static CellController instance() {
		return Singletons.INSTANCE;
	}

	
	@Override
	public void save(Cell object) 
	{
		  Connection myConn = null;
		 try 
			{
			     myConn = DriverManager.getConnection(url);
				
				Statement myStmt = myConn.createStatement();
				
			    String sql = "UPDATE CELL SET"
			    		+     " c_id = ('"+ object.getId() +"')"
			    		+     " nr =  ('"+ object.getNr() +"')"
			    		+     " index = ('"+ object.getIndex() +"')";                    
				 
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
	public void load(Cell object) 
	{
	
		Connection myConn = null;
		try 
		{
			  
			myConn = DriverManager.getConnection(url);
			
			Statement myStmt = myConn.createStatement();
			    
		    String sql  = "SELECT * FROM CELL";
		    
		    ResultSet  result = myStmt.executeQuery(sql);
		    
		       while(result.next())
		       {
		    	Integer c_id = result.getInt(1);
		    	Integer nr   = result.getInt(2);
		        Integer index  = result.getInt(3);
		    ;
		        
		    	  object.setId(c_id);
			      object.setNr(nr);
			      object.setIndex(index);
			      
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
