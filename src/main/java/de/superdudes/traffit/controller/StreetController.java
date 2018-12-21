package de.superdudes.traffit.controller;

import de.superdudes.traffit.DbManager;
import de.superdudes.traffit.dto.Lane;
import de.superdudes.traffit.dto.StartingGrid;
import de.superdudes.traffit.dto.Street;
import de.superdudes.traffit.exception.ObjectNotPersistedException;
import lombok.NonNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StreetController extends AbstractController<Street> {

    private static class Singletons {

        private static final StreetController INSTANCE = new StreetController();
    }

    public static StreetController instance() {
        return Singletons.INSTANCE;
    }

    void save(@NonNull Street object) {
        if (object.getStartingGrid().getId() == null) {
            throw new ObjectNotPersistedException(object.getStartingGrid());
        }
        try {
            // language=sql
            final String sql;

            if (object.getId() != null) {
                sql = "UPDATE STREET SET " + "s_id = '" + object.getId() + "', " + "nr = '" + object.getNr()
                        + "', " + "s_length ='" + object.getLength() + "'," + "where sg_id = '" + object.getId() + "'";
            } else {
                sql = "INSERT INTO STREET(nr, s_length, startinggrid_id) VALUES (" + "'" + object.getNr() + "', "
                        + "'" + object.getLength() + "'," + "'" + object.getStartingGrid().getId() + "')";
            }

            // First insert id, then save dependencies
            insertOrUpdate(sql, object);

            for (Lane lane : object.getLanes()) {
                LaneController.instance().save(lane);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Eintragen der Daten fehlgeschlagen!!!");
        }
    }

    /*
     * s_id INTEGER(10) AUTO_INCREMENT, nr INTEGER(10), s_length INTEGER(10),
     * startinggrid_id INTEGER(10),
     */
    Street load(@NonNull StartingGrid startingGrid) {

        if (startingGrid.getId() == null) {
            throw new ObjectNotPersistedException(startingGrid, "Street needs a startingGrid in db");
        }

        try {
            final Connection myConn = DbManager.instance().getConnection();
            final ResultSet result = myConn.createStatement().executeQuery(
                    "SELECT *, (select COUNT(*) from LANE where street_id = s_id) as laneCount FROM STREET where startinggrid_id = '" + startingGrid.getId() + "'");

            while (result.next()) {
                final Integer sg_id = result.getInt("s_id");
                final Integer nr = result.getInt("nr");
                final Integer sLength = result.getInt("s_length");
                final int laneCount = result.getInt("laneCount");

                final Street object = new Street(sLength, laneCount, startingGrid);
                object.setId(sg_id);
                object.setNr(nr);

                object.setLanes(LaneController.instance().load(object));

                return object;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.print("Laden der Daten nicht m�glich!!!");
        }
        return null;
    }
}
