package de.superdudes.traffit.controller;

import java.sql.*;
import de.superdudes.traffit.dto.Cell;

import java.util.AbstractMap;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import de.superdudes.traffit.dto.Vehicle;
import de.superdudes.traffit.dto.Vehicle.Type;
import de.superdudes.traffit.dto.Cell;
import de.superdudes.traffit.dto.Lane;
import de.superdudes.traffit.dto.Street;
import de.superdudes.traffit.dto.StreetSign;

public class VehicleController extends AbstractController<Vehicle> {

	private static class Singletons {

		private static final VehicleController INSTANCE = new VehicleController();
	}

	public static VehicleController instance() {
		return Singletons.INSTANCE;
	}

	@Override
	public void save(Vehicle object) 
	{
		
		if (object.getId() != null)
		{
			try {
				Statement myStmt = myConn.createStatement();

				String sql = "UPDATE VEHICLE" + " v_id = ('" + object.getId() + "')" + " nr =  ('" + object.getNr() + "')"  
				           +  " currentSpeed = ('" + object.getCurrentSpeed() + " maxSpeed = ('" + object.getMaxSpeed() + "')" + " speedLimit = ('" + object.getSpeedLimit() + "')" +  " WHERE sg_id = 1";

				myStmt.executeUpdate(sql);

			}

			catch (SQLException ex) {
				ex.printStackTrace();
				System.out.println("Eintragen der Daten fehlgeschlagen!!!");
			}
		}
		else {
			Statement myStmt = myConn.createStatement();

			String sql = " INSERT INTO VEHICLE (v_id, nr, currentSpeed, maxSpeed, speedLimit) " + " VALUES ('" + object.getId()
					+ object.getNr() + object.getCurrentSpeed() + object.getMaxSpeed() + object.getSpeedLimit() + "')";

			myStmt.executeUpdate(sql);

		}

	}

	@Override
	public Vehicle load(Integer Id)
	{

		try {
			Statement myStmt = myConn.createStatement();

		
			String sql = "SELECT v_id , nr , currentSpeed, maxSpeed, speedLimit FROM VEHICLE WHERE sg_id = '" + Id + "' ";

			ResultSet result = myStmt.executeQuery(sql);

			while (result.next())
			{
				Integer v_id = result.getInt(1);
				Integer nr = result.getInt(2);
				Integer currentSpeed = result.getInt(3);
				Integer maxSpeed = result.getInt(4);
				Integer speedLimit = result.getInt(5);

				Vehicle object = new Vehicle(Type.CAR);
						
			    object.setId(v_id);
			    object.setNr(nr);
			    object.setCurrentSpeed(currentSpeed);
			    object.setMaxSpeed(maxSpeed);
			    object.setSpeedLimit(speedLimit);
			
	           return object;
		      
		   }
		
		} 
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.out.print("Laden der Daten nicht möglich!!!");
		}

	}

	public void render(Vehicle object) {

		final Vehicle vehicle = object; // To rename fitting
		final Map.Entry<Integer, Vehicle> distance2Vehicle = findAncestor(vehicle);

		final int distanceToAncestor = distance2Vehicle.getKey();
		final Vehicle ancestor = distance2Vehicle.getValue();

		if (distanceToAncestor < Vehicle.ANCESTOR_DISTANCE_TO_RECOGNIZE_SPEED) {

			// If faster
			if (vehicle.getCurrentSpeed() > ancestor.getCurrentSpeed()) {
				final Map.Entry<Integer, Vehicle> distance2Overtaker = findOvertakingVehicle(vehicle);
				final Integer distanceToOvertaker = distance2Overtaker.getKey();
				final Vehicle overtaker = distance2Overtaker.getValue();

				// Overtake?
				if (distance2Overtaker.getKey() > Vehicle.ANCESTOR_DISTANCE_TO_RECOGNIZE_SPEED) {
					overtake(vehicle);
				}
			}
		}

		// todo adjust speed
		// can turn to right lane? Then do!
	}

	public Map.Entry<Integer, Vehicle> findAncestor(Vehicle vehicle) {

		Cell currentCell = vehicle.getLatestCell();
		Vehicle ancestor = null;

		int distance = 0;
		do {
			distance++;
		} while ((currentCell = currentCell.getSuccessor()) != null
				&& (ancestor = currentCell.getBlockingVehicle()) == null);

		return ancestor != null ? new AbstractMap.SimpleEntry<>(distance, ancestor) : null;
	}

	public Map.Entry<Integer, Vehicle> findOvertakingVehicle(Vehicle vehicle) {

		final Lane vehicleLane = vehicle.getLatestCell().getLane();
		final Street street = vehicleLane.getStreet();

		if (vehicleLane.isTopLeftLane()) {
			return null;
		}

		final Cell vehicleCell = vehicle.getEarliestCell();
		final Cell leftNeighbourCell = vehicleCell.getLeftNeighbour();

		Cell currentCell = leftNeighbourCell;
		Vehicle overtaker = null;

		int distance = 0;
		do {
			distance++;
		} while ((currentCell = currentCell.getAncestor()) != null
				&& (overtaker = currentCell.getBlockingVehicle()) == null);

		return overtaker != null ? new AbstractMap.SimpleEntry(distance, overtaker) : null;
	}

	public void overtake(Vehicle vehicle) {

		if (vehicle.getLane().isTopLeftLane()) {
			throw new RuntimeException("Cannot overtake on top left lane"); // todo replace due to SimulationException
		}
		final Deque<Cell> vehicleCells = vehicle.getBlockedCells();

		final Deque<Cell> newCells = new LinkedList<>();
		for (Cell cell : vehicleCells) {
			newCells.addFirst(cell.getLeftNeighbour());
		}

		vehicle.setBlockedCells(newCells);
	}
}
