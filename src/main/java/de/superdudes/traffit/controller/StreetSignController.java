package de.superdudes.traffit.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.superdudes.traffit.dto.ConstructionSite;
import de.superdudes.traffit.dto.SimulationObject;
import de.superdudes.traffit.dto.StreetSign;

public class StreetSignController extends AbstractController<StreetSign> {

	private static class Singletons {

		private static final StreetSignController INSTANCE = new StreetSignController();
	}

	public static StreetSignController instance() {
		return Singletons.INSTANCE;
	}

	@Override
	public void save(StreetSign object) throws SQLException {
		Connection myConn = null;
		try {
			if (object.getId() != null) {
				myConn = DriverManager.getConnection(url, user, pw);

				Statement myStmt = myConn.createStatement();

				String sql = "UPDATE STREET_SIGN SET" + " ss_id = '" + object.getId() + "'," + " nr = '"
						+ object.getNr() + "'," + " speedLimit = '" + object.getSpeedLimit() + "' "
						+ " WHERE sg_id = 1";

				myStmt.executeUpdate(sql);
			} else {
				myConn = DriverManager.getConnection(url, user, pw);

				Statement myStmt = myConn.createStatement();

				String sql = " INSERT INTO STREET_SIGN (nr, speedLimit) " + " VALUES ('" + object.getNr() + "','"
						+ object.getSpeedLimit() + "')";

				myStmt.executeUpdate(sql);

			}
		}

		catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Eintragen der Daten fehlgeschlagen!!!");
		} finally {
			myConn.close();
		}

	}

	@Override

	public StreetSign load(Integer Id) throws SQLException {
		Connection myConn = null;
		try {
			myConn = DriverManager.getConnection(url, user, pw);

			Statement myStmt = myConn.createStatement();

			String sql = "SELECT ss_id , nr , speedLimit STREEET_SIGN WHERE sg_id = '" + Id + "' ";

			ResultSet result = myStmt.executeQuery(sql);

			while (result.next()) {
				Integer sg_id = result.getInt(1);
				Integer nr = result.getInt(2);
				Integer speedLimit = result.getInt(3);

				StreetSign object = new StreetSign(speedLimit);

				object.setId(sg_id);
				object.setNr(nr);
				object.setSpeedLimit(speedLimit);

				return object;

			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.print("Laden der Daten nicht m�glich!!!");
		} finally {
			myConn.close();
		}
		return null;

	}
}
