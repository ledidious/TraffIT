package de.superdudes.traffit.controller;

import java.sql.*;
import de.superdudes.traffit.dto.Cell;
import de.superdudes.traffit.dto.Vehicle;

public class VehicleController extends AbstractController <Vehicle> {

	private static class Singletons {

		private static final VehicleController INSTANCE = new VehicleController();
	}

	public static VehicleController instance() {
		return Singletons.INSTANCE;
	}

	@Override
	public void save(Vehicle object) 
	{
		  Connection myConn = null;
		 try 
			{
			     myConn = DriverManager.getConnection(url);
				
				Statement myStmt = myConn.createStatement();
				
			    String sql = "UPDATE VEHICLE SET"
			    		+     " v_id = ('"+ object.getId() +"')"
			    		+     " nr = ('"+ object.getNr() +"')"
			    		+     " currentSpeed = ('"+ object.getCurrentSpeed() +"')"
			    		+     " maxSpeed = ('"+ object.getMaxSpeed() +"')"
			    		+     " speedLimit = ('"+ object.getSpeedLimit() +"')";
			    
				
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
		public void load(Vehicle object) 
		{
		
			Connection myConn = null;
			try 
			{
				  
				myConn = DriverManager.getConnection(url);
				
				Statement myStmt = myConn.createStatement();
				    
			    String sql  = "SELECT * FROM STARTING_GRID";
			    
			    ResultSet  result = myStmt.executeQuery(sql);
			    
			       while(result.next())
			       {
			    	 	Integer v_id = result.getInt(1);
				    	Integer nr   = result.getInt(2);
				        Integer currentSpeed  = result.getInt(3);
				    	Integer maxSpeed  = result.getInt(4);
				    	Integer speedLimit  = result.getInt(5);
			    	
			    	    object.setId(v_id);
				        object.setNr(nr);
				        object.setCurrentSpeed(currentSpeed);
				        object.setMaxSpeed(maxSpeed);
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
