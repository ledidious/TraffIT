package de.superdudes.traffit.controller;

import de.superdudes.traffit.DbManager;
import de.superdudes.traffit.dto.Cell;
import de.superdudes.traffit.dto.Lane;
import de.superdudes.traffit.dto.Street;
import de.superdudes.traffit.exception.ObjectNotPersistedException;
import lombok.NonNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class LaneController extends AbstractController<Lane> {

    private static class Singletons {

        private static final LaneController INSTANCE = new LaneController();
    }

    public static LaneController instance() {
        return Singletons.INSTANCE;
    }

    void save(@NonNull Lane object) {

        if (object.getStreet().getId() == null) {
            throw new ObjectNotPersistedException(object.getStreet());
        }
        try {
            final String sql;

            if (object.getId() != null) {
                sql = "UPDATE LANE SET" + " l_id = '" + object.getId() + "'," + " nr = '" + object.getNr() + "' "
                        + " WHERE l_id = '" + object.getId() + "'";
            } else {
                sql = " INSERT INTO LANE (nr, street_id, l_index) " + " VALUES ('" + object.getNr() +
                        "', '" + object.getStreet().getId() + "', '" + object.getIndex() + "')";
            }

            insertOrUpdate(sql, object);

            for (Cell cell : object.getCells()) {
                CellController.instance().save(cell);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Eintragen der Daten fehlgeschlagen!!!");
        }
    }

    List<Lane> load(@NonNull Street street) {

        if (street.getId() == null) {
            throw new ObjectNotPersistedException(street, "Lane needs a street in db");
        }

        try {
            final Connection myConn = DbManager.instance().getConnection();
            final ResultSet resultSet = myConn.createStatement().executeQuery(
                    "SELECT * FROM LANE WHERE street_id = '" + street.getId() + "' ORDER BY l_index ASC"
            );

            final List<Lane> results = new LinkedList<>();
            while (resultSet.next()) {
                final Integer l_id = resultSet.getInt("l_id");
                final Integer nr = resultSet.getInt("nr");
                final Integer index = resultSet.getInt("l_index");

                final Lane object = new Lane(street, index);
                object.setId(l_id);
                object.setNr(nr);

                // Load cells
                final List<Cell> cellList = CellController.instance().load(object);
                final Cell[] cellsArray = new Cell[cellList.size()];
                cellList.toArray(cellsArray);
                object.setCells(cellsArray);

                results.add(object);
            }

            // Set on street
            street.setLanes(results);

            return results;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.print("Laden der Daten nicht m�glich!!!");
        }
        return null;
    }
}
