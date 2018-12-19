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
	public void save(StartingGrid object) throws SQLException {
		Connection myConn = null;

		try {
			if (object.getId() != null) {
				myConn = DriverManager.getConnection(url, user, pw);

				Statement myStmt = myConn.createStatement();

				String sql = "UPDATE STARTING_GRID SET" + " sg_id = '" + object.getId() + "'," + " nr =  '"
						+ object.getNr() + "'," + " name = '" + object.getName() + "' " + " WHERE sg_id = 1";

				System.out.println(sql);

				myStmt.executeUpdate(sql);
			} else {
				myConn = DriverManager.getConnection(url, user, pw);

				Statement myStmt = myConn.createStatement();

				String sql = " INSERT INTO STARTING_GRID (nr, name) " + " VALUES ('" + object.getNr() + "','"
						+ object.getName() + "')";

				System.out.println(sql);

				myStmt.executeUpdate(sql);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Eintragen der Daten fehlgeschlagen!!!");
		} finally {
			myConn.close();
		}

	}

	@Override
	public StartingGrid load(Integer Id) throws SQLException {
		Connection myConn = null;
		try {
			myConn = DriverManager.getConnection(url, user, pw);

			Statement myStmt = myConn.createStatement();

			String sql = "SELECT sg_id , nr , name FROM STARTING_GRID WHERE sg_id = '" + Id + "' ";

			ResultSet result = myStmt.executeQuery(sql);

			while (result.next()) {

				Integer sg_id = result.getInt(1);
				Integer nr = result.getInt(2);
				String name = result.getString(3);

				StartingGrid object = new StartingGrid(name, new Street(nr, nr));

				object.setId(sg_id);
				object.setNr(nr);
				object.setName(name);

				return object;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.print("Laden der Daten nicht möglich!!!");

		} finally {
			myConn.close();
		}
		return null;
	}
}
