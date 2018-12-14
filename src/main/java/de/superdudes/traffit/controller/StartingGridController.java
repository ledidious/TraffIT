package de.superdudes.traffit.controller;

import java.sql.*;
import de.superdudes.traffit.dto.StartingGrid;

public class StartingGridController extends AbstractController<StartingGrid> {

	private static class Singletons {

		private static final StartingGridController INSTANCE = new StartingGridController();
	}

	public static StartingGridController instance() {
		return Singletons.INSTANCE;
	}

	@Override
	public void save(StartingGrid object) {
		Connection myConn = null;

		try {
			myConn = DriverManager.getConnection(url);

			Statement myStmt = myConn.createStatement();

			String sql = "UPDATE STRARTING_GRID SET" + " sg_id = ('" + object.getId() + "')" + " nr =  ('"
					+ object.getNr() + "')" + " name = ('" + object.getName() + "') ";

			myStmt.executeUpdate(sql);

		}

		catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Eintragen der Daten fehlgeschlagen!!!");
		}

		finally {
			try {
				myConn.close();
			} catch (SQLException e) {
				System.out.println("Verbindung konnte nicht beeendet werden !!!!!");
				e.printStackTrace();
			}

		}
	}

	@Override
	public void load(StartingGrid object) {

		Connection myConn = null;
		try {

			myConn = DriverManager.getConnection(url);

			Statement myStmt = myConn.createStatement();

			String sql = "SELECT sg_id , nr , name FROM STARTING_GRID";

			ResultSet result = myStmt.executeQuery(sql);

			while (result.next()) {
				Integer sg_id = result.getInt(1);
				Integer nr = result.getInt(2);
				String name = result.getString(3);

				object.setId(sg_id);
				object.setNr(nr);
				object.setName(name);

			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.print("Laden der Daten nicht m�glich!!!");
		} finally {
			try {
				myConn.close();
			} catch (SQLException e) {
				System.out.println("Verbindung konnte nicht beeendet werden !!!!!");
				e.printStackTrace();
			}
		}

	}
}
