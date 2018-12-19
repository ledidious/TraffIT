package de.superdudes.traffit.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.superdudes.traffit.dto.Cell;
import de.superdudes.traffit.dto.Lane;
import de.superdudes.traffit.dto.SimulationObject;
import de.superdudes.traffit.dto.StartingGrid;
import de.superdudes.traffit.dto.Street;

public class CellController extends AbstractController<Cell> {

	private static class Singletons {

		private static final CellController INSTANCE = new CellController();
	}

	public static CellController instance() {
		return Singletons.INSTANCE;
	}

	@Override
	public void save(Cell object) throws SQLException {
		Connection myConn = null;
		try {
			if (object.getId() != null) {
				myConn = DriverManager.getConnection(url, user, pw);

				Statement myStmt = myConn.createStatement();

				String sql = "UPDATE CELL SET" + " c_id = '" + object.getId() + "'," + " nr = '" + object.getNr() + "',"
						+ " index = '" + object.getIndex() + "' " + " WHERE sg_id = 1";

				myStmt.executeUpdate(sql);
			} else {
				myConn = DriverManager.getConnection(url, user, pw);

				Statement myStmt = myConn.createStatement();

				String sql = " INSERT INTO Cell (nr, index) " + " VALUES ('" + object.getNr() + "','"
						+ object.getIndex() + "')";

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
	public Cell load(Integer Id) throws SQLException {
		Connection myConn = null;

		try {
			myConn = DriverManager.getConnection(url, user, pw);

			Statement myStmt = myConn.createStatement();

			String sql = "SELECT c_id, nr, index FROM CELL WHERE c_id = '" + Id + "' ";

			ResultSet result = myStmt.executeQuery(sql);

			while (result.next()) {
				Integer c_id = result.getInt(1);
				Integer nr = result.getInt(2);
				Integer index = result.getInt(3);

				Cell object = new Cell(index, new Lane(new Street(2, 6), index));

				object.setId(c_id);
				object.setNr(nr);
				object.setIndex(index);

				return object;

			}

		} catch (

		SQLException ex) {

			ex.printStackTrace();
			System.out.print("Laden der Daten nicht m�glich!!!");
		} finally {
			myConn.close();
		}
		return null;

	}

}
