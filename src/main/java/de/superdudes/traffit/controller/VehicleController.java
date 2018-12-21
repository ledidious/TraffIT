package de.superdudes.traffit.controller;

import de.superdudes.traffit.DbManager;
import de.superdudes.traffit.dto.Cell;
import de.superdudes.traffit.dto.StartingGrid;
import de.superdudes.traffit.dto.Vehicle;
import de.superdudes.traffit.exception.ObjectNotPersistedException;
import lombok.Getter;
import lombok.NonNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class VehicleController extends AbstractController<Vehicle> {

    private static class Singletons {

        private static final VehicleController INSTANCE = new VehicleController();
    }

    public static VehicleController instance() {
        return Singletons.INSTANCE;
    }

    // ===============================================
    // Database
    // ===============================================

    void save(@NonNull Vehicle object) {

        if (object.getStartingGrid().getId() == null) {
            throw new ObjectNotPersistedException(object.getStartingGrid());
        }
        if (object.getBackCell().getId() == null) {
            throw new ObjectNotPersistedException(object.getBackCell());
        }

        try {
            // language=sql
            final String sql;

            if (object.getId() != null) {
                sql = "UPDATE VEHICLE set v_id = '" + object.getId() + "', type = '"
                        + object.getType() + "', nr = '', tailCell_id = '" + object.getBackCell().getId()
                        + "', currentspeed = '" + object.getCurrentSpeed() + "', maxspeed = '"
                        + object.getMaxSpeed() + "', startinggrid_id = '" + object.getStartingGrid().getId() + "'";
            } else {
                sql = " INSERT INTO VEHICLE (type, nr, tailCell_id, currentspeed, maxspeed, startinggrid_id) "
                        + " VALUES ('" + object.getType().name() + "', '" + object.getNr() + "', '"
                        + object.getBackCell().getId() + "', " + object.getCurrentSpeed() + ", "
                        + object.getMaxSpeed() + ", '" + object.getStartingGrid().getId() + "')";
            }

            insertOrUpdate(sql, object);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Eintragen der Daten fehlgeschlagen!!!");
        }
    }

    Set<Vehicle> load(@NonNull StartingGrid startingGrid, @NonNull Cell tailCell) {

        if (startingGrid.getId() == null) {
            throw new ObjectNotPersistedException(startingGrid);
        }

        try {
            final Connection connection = DbManager.instance().getConnection();
            final ResultSet resultSet = connection.createStatement().executeQuery(
                    "SELECT * FROM VEHICLE v, CELL c"
                            + " WHERE v.tailCell_id = c.c_id "
                            + " AND c.c_id = " + tailCell.getId()
                            + " AND v.startinggrid_id = " + startingGrid.getId()
            );

            final Set<Vehicle> results = new HashSet<>();
            while (resultSet.next()) {
                final Integer id = resultSet.getInt("v_id");
                final Vehicle.Type type = Vehicle.Type.valueOf(resultSet.getString("type"));
                final Integer nr = resultSet.getInt("nr");
                final Integer currentSpeed = resultSet.getInt("currentspeed");
                final Integer maxSpeed = resultSet.getInt("maxspeed");

                final Vehicle object = new Vehicle(type, tailCell, false);
                object.setId(id);
                object.setNr(nr);
                object.setCurrentSpeed(currentSpeed);
                object.setMaxSpeed(maxSpeed);

                results.add(object);
            }

            return results;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.print("Laden der Daten nicht m�glich!!!");
        }
        return null;
    }

    // ===============================================
    // Simulation Logic
    // ===============================================

    @Override
    public void render(Vehicle object) {

        final Vehicle vehicle = object; // To rename fitting

        boolean vehicleForeseeableAhead = render_byOtherVehicles(vehicle);

        if (!vehicleForeseeableAhead) {
            render_bySpeedLimits(vehicle);
        }

        render_moveForward(vehicle);
    }

    /**
     * @param vehicle
     * @return whether a car is ahead on the same line in a forseeable distance
     */
    private boolean render_byOtherVehicles(Vehicle vehicle) {

        // Distance -> Vehicle
        DistanceToAnotherVehicle ancestor = null;
        DistanceToAnotherVehicle successor = null;
        DistanceToAnotherVehicle overtakenVehicle = null;
        DistanceToAnotherVehicle overtakingVehicle = null;

        // When ancestor in short distance
        if ((ancestor = findAncestor(vehicle)) != null
                && ancestor.getDistance() < Vehicle.ANCESTOR_DISTANCE_TO_RECOGNIZE_SPEED
                && vehicle.getCurrentSpeed() > ancestor.getVehicle().getCurrentSpeed()) {

            overtakingVehicle = findOvertaker(vehicle);
            if (overtakingVehicle.getDistance() > Vehicle.ANCESTOR_DISTANCE_TO_RECOGNIZE_SPEED) {
                turnLeft(vehicle);
            } else {
                // Cannot overtake
                vehicle.brake();
                return true;
            }
        }
        // Turn back to right lane
        else if ((overtakenVehicle = findOvertaken(vehicle)) != null
                && ancestor.getDistance() > Vehicle.ANCESTOR_DISTANCE_MIN) {
            turnRight(vehicle);
        }

        return ancestor != null && ancestor.getDistance() < Vehicle.ANCESTOR_DISTANCE_TO_RECOGNIZE_SPEED;
    }

    // Preamble: No car forseeable ahead, see in render()
    private void render_bySpeedLimits(Vehicle vehicle) {

        final Cell currentCell = vehicle.getFrontCell();

        int speedLimit = Integer.MAX_VALUE;
        if (currentCell.getStreetSign() != null) {
            speedLimit = currentCell.getStreetSign().getSpeedLimit();
        }

        // Brake if too fast
        if (speedLimit < vehicle.getCurrentSpeed()) {
            vehicle.brake();
        } // or accelerate if possible
        else if (vehicle.getCurrentSpeed() < vehicle.getMaxSpeed()) {
            vehicle.accelerate();
        }
    }

    private DistanceToAnotherVehicle findAncestor(Vehicle vehicle) {
        return findVehicle(vehicle.getFrontCell(), false);
    }

    private DistanceToAnotherVehicle findSuccessor(Vehicle vehicle) {
        return findVehicle(vehicle.getFrontCell(), true);
    }

    private DistanceToAnotherVehicle findOvertaker(Vehicle overtaken) {

        if (overtaken.getBackCell().getLane().isTopLeftLane()) {
            return null;
        }

        final Cell rightNeighbourCell = overtaken.getBackCell().getRightNeighbour();
        return findVehicle(rightNeighbourCell, true);
    }

    private DistanceToAnotherVehicle findOvertaken(Vehicle overtaker) {

        if (overtaker.getBackCell().getLane().isTopLeftLane()) {
            return null;
        }

        final Cell rightNeighbourCell = overtaker.getBackCell().getRightNeighbour();
        return findVehicle(rightNeighbourCell, true);
    }

    /**
     * The central algorithm to find vehicles by searching cell for cell.
     *
     * @param fromCell
     * @param backwards
     * @return entry consisting of distance and vehicle or {@code null} if no
     * vehicle can be found until end of street
     */
    private DistanceToAnotherVehicle findVehicle(Cell fromCell, boolean backwards) {

        Cell currentCell = fromCell;
        Vehicle ancestorOrSuccessor = null;

        int distance = 0;
        do {
            distance++;
        } while ((currentCell = (backwards ? currentCell.getAncestor() : currentCell.getSuccessor())) != null
                && (ancestorOrSuccessor = currentCell.getBlockingVehicle()) == null);

        return ancestorOrSuccessor != null ? new DistanceToAnotherVehicle(distance, ancestorOrSuccessor) : null;
    }

    private void turnLeft(Vehicle vehicle) {

        if (vehicle.getLane().isTopLeftLane()) {
            throw new RuntimeException("Cannot overtake on top left lane"); // todo replace due to SimulationException
        }
        vehicle.turnLeft();
    }

    private void turnRight(Vehicle vehicle) {

        if (vehicle.getLane().isTopRightLane()) {
            throw new RuntimeException("Cannot overtake on top left lane"); // todo replace due to SimulationException
        }
        vehicle.turnRight();
    }

    private void render_moveForward(Vehicle vehicle) {

        if (vehicle.getCurrentSpeed() - vehicle.getGensSinceLastDrive() <= 0) {
            vehicle.drive();
        } else {
            vehicle.dontDrive();
        }
    }

    @Getter
    private class DistanceToAnotherVehicle {
        private final int distance;
        private final Vehicle vehicle;

        public DistanceToAnotherVehicle(int distance, Vehicle vehicle) {
            super();
            this.distance = distance;
            this.vehicle = vehicle;
        }
    }
}
