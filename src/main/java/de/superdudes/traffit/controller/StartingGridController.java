package de.superdudes.traffit.controller;

import java.sql.*;
import de.superdudes.traffit.dto.StartingGrid;
import de.superdudes.traffit.dto.Street;

public class StartingGridController extends AbstractController<StartingGrid> {

	private static class Singletons {

		private static final StartingGridController INSTANCE = new StartingGridController();
	}

	public static StartingGridController instance() {
		return Singletons.INSTANCE;
	}

	@Override
	public void save(StartingGrid object) {

		if (object.getId() != null) {
			try {
				Statement myStmt = myConn.createStatement();

				String sql = "UPDATE STRARTING_GRID SET" + " sg_id = ('" + object.getId() + "')" + " nr =  ('"
						+ object.getNr() + "')" + " name = ('" + object.getName() + "') " + " WHERE sg_id = 1";

				myStmt.executeUpdate(sql);

			}

			catch (SQLException ex) {
				ex.printStackTrace();
				System.out.println("Eintragen der Daten fehlgeschlagen!!!");
			}
		} else {
			Statement myStmt = myConn.createStatement();

			String sql = " INSERT INTO STARTING_GRID (sg_id, nr, name) " + " VALUES ('" + object.getId()
					+ object.getNr() + object.getName() + "')";

			myStmt.executeUpdate(sql);

		}

	}

	@Override
	public StartingGrid load(Integer Id) {

		try {
			Statement myStmt = myConn.createStatement();

			String sql = "SELECT sg_id , nr , name FROM STARTING_GRID WHERE sg_id = '" + Id + "' ";

			ResultSet result = myStmt.executeQuery(sql);

			while (result.next()) {
				Integer sg_id = result.getInt(1);
				Integer nr = result.getInt(2);
				String name = result.getString(3);

				StartingGrid object = new StartingGrid(name, new StreetController().load(Id));

				object.setId(sg_id);
				object.setNr(nr);
				object.setName(name);

				return object;

			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.print("Laden der Daten nicht m�glich!!!");
		}

	}
}
