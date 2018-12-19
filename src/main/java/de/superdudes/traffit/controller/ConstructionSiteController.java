package de.superdudes.traffit.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.superdudes.traffit.dto.ConstructionSite;
import de.superdudes.traffit.dto.SimulationObject;
import de.superdudes.traffit.dto.StartingGrid;
import de.superdudes.traffit.dto.Street;

public class ConstructionSiteController extends AbstractController<ConstructionSite> {

	private static class Singletons {

		private static final ConstructionSiteController INSTANCE = new ConstructionSiteController();
	}

	public static ConstructionSiteController instance() {
		return Singletons.INSTANCE;
	}

	@Override
	public void save(ConstructionSite object) throws SQLException {
		Connection myConn = null;
		try {
			if (object.getId() != null) {
				myConn = DriverManager.getConnection(url, user, pw);

				Statement myStmt = myConn.createStatement();

				String sql = "UPDATE ConstructionSite SET" + " cs_id = '" + object.getId() + "'," + " nr = '"
						+ object.getNr() + "'," + " sg_id = '" + object.getLength() + "' " + " WHERE sg_id = 1";

				myStmt.executeUpdate(sql);
			}

			else {
				myConn = DriverManager.getConnection(url, user, pw);

				Statement myStmt = myConn.createStatement();

				String sql = " INSERT INTO ConstructionSite (nr, name) " + " VALUES ('" + object.getNr() + "','"
						+ object.getLength() + "')";

				myStmt.executeUpdate(sql);

			}
		}

		catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Eintragen der Daten fehlgeschlagen!!!");
		}

		finally {
			myConn.close();
		}
	}

	@Override

	public ConstructionSite load(Integer Id) throws SQLException {
		Connection myConn = null;
		try {
			myConn = DriverManager.getConnection(url, user, pw);

			Statement myStmt = myConn.createStatement();

			String sql = "SELECT cs_id , nr , cs_length ConstructionSite WHERE sg_id = '" + Id + "' ";

			ResultSet result = myStmt.executeQuery(sql);

			while (result.next()) {
				Integer sg_id = result.getInt(1);
				Integer nr = result.getInt(2);
				Integer length = result.getInt(3);

				ConstructionSite object = new ConstructionSite(length);

				object.setId(sg_id);
				object.setNr(nr);
				object.setLength(length);

				return object;

			}

		}

		catch (SQLException ex) {

			ex.printStackTrace();
			System.out.print("Laden der Daten nicht m�glich!!!");
		} finally {
			myConn.close();
		}
		return null;

	}


}
