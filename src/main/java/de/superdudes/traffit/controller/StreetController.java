package de.superdudes.traffit.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.superdudes.traffit.dto.SimulationObject;
import de.superdudes.traffit.dto.StartingGrid;
import de.superdudes.traffit.dto.Street;

public class StreetController extends AbstractController<Street> {

	private static class Singletons {

		private static final StreetController INSTANCE = new StreetController();
	}

	public static StreetController instance() {
		return Singletons.INSTANCE;
	}

	@Override
	public void save(Street object) {
	    
		if (object.getId() != null)
		{
			try {
				Statement myStmt = myConn.createStatement();

				String sql = "UPDATE STREET SET" + " s_id = ('" + object.getId() + "')" + " nr =  ('"
						+ object.getNr() + "')" + " s_length = ('" + object.getLength() + "') " + " WHERE sg_id = 1";

				myStmt.executeUpdate(sql);

			}

			catch (SQLException ex) {
				ex.printStackTrace();
				System.out.println("Eintragen der Daten fehlgeschlagen!!!");
			}
		}
		else
		{
		try 
		{
			Statement myStmt = myConn.createStatement();

			String sql = " INSERT INTO STREET(s_id, nr, s_length) " + " VALUES ('" + object.getId() + object.getNr()
					+ object.getLength() + "')";

			myStmt.executeUpdate(sql);

		}

		catch (SQLException ex)
		 {
			ex.printStackTrace();
			System.out.println("Eintragen der Daten fehlgeschlagen!!!");
		 }
	   }
	}

	@Override
	public Street load(Integer Id)
	{

		try {
			Statement myStmt = myConn.createStatement();

		    String sql = "SELECT s_id , nr , s_length FROM STREET WHERE sg_id = '" + Id + "' ";

			ResultSet result = myStmt.executeQuery(sql);

			while (result.next())
			{
				Integer sg_id = result.getInt(1);
				Integer nr = result.getInt(2);
				Integer sLength = result.getInt(3);

	      Street object = new Street(sLength, new Street().getLaneCount());
	      
	     
	        object.setId(sg_id);
			object.setNr(nr);
			object.setLength(sLength);
		
			
			 return object;
		      
		   }
		
		} 
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.out.print("Laden der Daten nicht möglich!!!");
		}

	}

}
