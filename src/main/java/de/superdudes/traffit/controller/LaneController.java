package de.superdudes.traffit.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.superdudes.traffit.dto.Lane;
import de.superdudes.traffit.dto.SimulationObject;
import de.superdudes.traffit.dto.StartingGrid;
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
		
		if (object.getId() != null)
		{
			try {
				Statement myStmt = myConn.createStatement();

				String sql = "UPDATE LANE SET" + " l_id = ('" + object.getId() + "')" + " nr =  ('"+ object.getNr() + "')" +  " WHERE sg_id = 1";

				myStmt.executeUpdate(sql);

			}

			catch (SQLException ex) {
				ex.printStackTrace();
				System.out.println("Eintragen der Daten fehlgeschlagen!!!");
			}
		}
		else 
		{
			Connection myConn = DriverManager.getConnection(url);
			
			Statement myStmt = myConn.createStatement();

			String sql = " INSERT INTO LANE (l_id, nr) " + " VALUES ('" + object.getId() + object.getNr() +  "')";

			 myStmt.executeUpdate(sql);

		}

	}

	@Override
	public Lane load(Integer Id)
	{

		try 
		{
			
			Statement myStmt = myConn.createStatement();

		
			String sql = "SELECT l_id , nr , name FROM LANE WHERE sg_id = '" + Id + "' ";

			ResultSet result = myStmt.executeQuery(sql);

			while (result.next())
			{
				Integer l_id = result.getInt(1);
				Integer nr = result.getInt(2);
			

	      Lane object = new Lane(new StreetController().load(Id), new Lane().getIndex());

			object.setId(l_id);
			object.setNr(nr);
		
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
