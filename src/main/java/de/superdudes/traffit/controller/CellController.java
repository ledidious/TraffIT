package de.superdudes.traffit.controller;

import de.superdudes.traffit.DbManager;
import de.superdudes.traffit.dto.*;
import de.superdudes.traffit.exception.ObjectNotPersistedException;
import lombok.NonNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CellController extends AbstractController<Cell> {

    private static class Singletons {

        private static final CellController INSTANCE = new CellController();
    }

    public static CellController instance() {
        return Singletons.INSTANCE;
    }

    void save(@NonNull Cell object) {

        if (object.getLane().getId() == null) {
            throw new ObjectNotPersistedException(object.getLane());
        }
        try {
            // language=sql
            final String sql;

            if (object.getId() != null) {

                sql = "UPDATE CELL SET c_id = '" + object.getId() + "', nr = '" + object.getNr() + "', " +
                        "c_index = '" + object.getIndex() + "', lane_id = '" + object.getLane().getId() + "'";
            } else {
                sql = "INSERT INTO CELL (nr, c_index, lane_id) " + " " +
                        "VALUES ('" + object.getNr() + "', '" + object.getIndex() + "', '" + object.getLane().getId() + "')";
            }

            insertOrUpdate(sql, object);

            // I know, multiple cells have the same construction site, so this means multiple updates with same data
            final ConstructionSite constructionSite = object.getBlockingConstructionSite();
            if (constructionSite != null && constructionSite.getId() == null) {
                ConstructionSiteController.instance().save(constructionSite);
            }
			// I know, multiple cells have the same streetSign, so this means multiple updates with same data
			final StreetSign streetSign = object.getStreetSign();
            if (streetSign != null && streetSign.getId() == null) {
                StreetSignController.instance().save(streetSign);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Eintragen der Daten fehlgeschlagen!!!");
        }
    }

    List<Cell> load(@NonNull Lane lane) {

        if (lane.getId() == null) {
            throw new ObjectNotPersistedException(lane);
        }

        try {
            final Connection connection = DbManager.instance().getConnection();
            final ResultSet resultSet = connection.createStatement().executeQuery(
                    "SELECT * FROM CELL WHERE lane_id = '" + lane.getId() + "' ORDER BY c_index ASC"
            );

            final List<Cell> results = new LinkedList<>();
            final Set<ConstructionSite> constructionSites = new HashSet<>();
            final Set<Vehicle> vehicles = new HashSet<>();
            final Set<StreetSign> streetSigns = new HashSet<>();

            while (resultSet.next()) {
                final Integer id = resultSet.getInt("c_id");
                final Integer nr = resultSet.getInt("nr");
                final Integer index = resultSet.getInt("c_index");

                final Cell cell = new Cell(index, lane);
                cell.setId(id);
                cell.setNr(nr);

                lane.setCell(cell);

                // Load dependencies
                final ConstructionSite constructionSite = ConstructionSiteController.instance().load(cell);
                if (constructionSite != null) {
                    constructionSites.add(constructionSite);
                }
                final StreetSign streetSign = StreetSignController.instance().load(cell);
                if (streetSign != null) {
                    streetSigns.add(streetSign);
                }
                final Set<Vehicle> loadedVehicles = VehicleController.instance().load(cell.getLane().getStreet().getStartingGrid(), cell);
                if (loadedVehicles != null) {
                    vehicles.addAll(loadedVehicles);
                }

                // Add cell
                results.add(cell);
            }

            // Remove null values
            streetSigns.removeIf(Objects::isNull);
            constructionSites.removeIf(Objects::isNull);
            vehicles.removeIf(Objects::isNull);

            // Connect other cells to constructionSite, vehicles and streetSigns (only tailCell has foreign key in db)
            for (ConstructionSite constructionSite : constructionSites) {
                constructionSite.connectCells(constructionSite.getTailCell(), Cell::setBlockingConstructionSite);
            }
            for (Vehicle vehicle : vehicles) {
                vehicle.connectCells(vehicle.getTailCell(), Cell::setBlockingVehicle);
            }
            for (StreetSign streetSign : streetSigns) {
                streetSign.connectCells(streetSign.getTailCell(), Cell::setStreetSign);
            }

            return results;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.print("Laden der Daten nicht mögch!!!");
        }
        return null;
    }
}
