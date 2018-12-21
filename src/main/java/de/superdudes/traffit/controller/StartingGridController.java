package de.superdudes.traffit.controller;

import de.superdudes.traffit.DbManager;
import de.superdudes.traffit.dto.Cell;
import de.superdudes.traffit.dto.StartingGrid;
import de.superdudes.traffit.dto.Street;
import de.superdudes.traffit.dto.Vehicle;
import lombok.NonNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Stream;

public class StartingGridController extends AbstractController<StartingGrid> {

    private static class Singletons {

        private static final StartingGridController INSTANCE = new StartingGridController();
    }

    public static StartingGridController instance() {
        return Singletons.INSTANCE;
    }

    /*
     * sg_id INTEGER(10) AUTO_INCREMENT, nr INTEGER(10), name VARCHAR(255),
     */

    public void save(@NonNull StartingGrid object) {
        final Connection myConn = DbManager.instance().getConnection();

        try {
            // language=sql
            final String sql;

            if (object.getId() != null) {
                sql = "UPDATE STARTING_GRID SET sg_id = " + " sg_id = '" + object.getId() + "'," + " nr =  '"
                        + object.getNr() + "'," + " name = '" + object.getName() + "' " + " WHERE sg_id = "
                        + object.getId();
            } else {
                // Delete other startingGrids, until now we only provide one in parallel
                //   !!! Therefore all foreign keys must be on update cascade on delete cascade
                //       because everything is existence-dependent from startingGrid
                myConn.createStatement().executeUpdate("delete from STARTING_GRID");

                sql = "INSERT INTO STARTING_GRID (nr, name) " + " VALUES ('" + object.getNr() + "','"
                        + object.getName() + "')";
            }

            // First insert, then save dependencies
            insertOrUpdate(sql, object);

            StreetController.instance().save(object.getStreet());

            for (Vehicle vehicle : object.getVehicles()) {
                VehicleController.instance().save(vehicle);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Eintragen der Daten fehlgeschlagen!!!");
        }
    }

    public StartingGrid load() {

        final Connection connection = DbManager.instance().getConnection();
        try {
            // Until now we only provide one simulation to save and load
            ResultSet result = connection.createStatement().executeQuery(
                    "SELECT sg_id , nr , name FROM STARTING_GRID LIMIT 1"
            );

            while (result.next()) {
                final Integer sg_id = result.getInt("sg_id");
                final Integer nr = result.getInt("nr");
                final String name = result.getString("name");

                final StartingGrid object = new StartingGrid(name);

                object.setId(sg_id);
                object.setNr(nr);
                object.setName(name);

                StreetController.instance().load(object);

                return object;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.print("Laden der Daten nicht m�glich!!!");
        }
        return null;
    }
}
