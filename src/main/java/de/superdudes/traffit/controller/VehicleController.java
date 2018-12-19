package de.superdudes.traffit.controller;

import java.sql.*;
import de.superdudes.traffit.dto.Cell;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import de.superdudes.traffit.dto.Vehicle;
import de.superdudes.traffit.dto.Vehicle.Type;
import lombok.Getter;
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
	public void save(Vehicle object) throws SQLException 
	{
		Connection myConn = null;
		try {
				if (object.getId() != null)
				{
				 myConn = DriverManager.getConnection(url,user,pw);
					
				 Statement myStmt = myConn.createStatement();

				 String sql = "UPDATE VEHICLE" + " v_id = '" + object.getId() + "'," + " nr = '" + object.getNr() + "',"  
				           +  "currentSpeed = '" + object.getCurrentSpeed() + "'," + " maxSpeed ='" + object.getMaxSpeed() + "',"
						   + " speedLimit = '" + object.getSpeedLimit() + "'," +  " WHERE sg_id = 1";

				myStmt.executeUpdate(sql);
				}
				else
				{
					 myConn = DriverManager.getConnection(url,user,pw);
					
					Statement myStmt = myConn.createStatement();

					String sql = " INSERT INTO VEHICLE (nr, currentSpeed, maxSpeed, speedLimit) " + " VALUES ('"  + object.getNr() + "','"
					          + object.getCurrentSpeed() + "','" + object.getMaxSpeed() + "','" + object.getSpeedLimit() + "')";

					myStmt.executeUpdate(sql);

				}
			}

			catch (SQLException ex) {
				ex.printStackTrace();
				System.out.println("Eintragen der Daten fehlgeschlagen!!!");
			}

		   finally
		   {
			   myConn.close();
		   }
		
		

	}

	@Override

	public Vehicle load(Integer Id) throws SQLException
	{
		Connection myConn = null;
		try {
		     myConn = DriverManager.getConnection(url,user,pw);
			
			Statement myStmt = myConn.createStatement();

			String sql = "SELECT v_id , nr , currentSpeed, maxSpeed, speedLimit FROM VEHICLE WHERE sg_id = '" + Id
					+ "' ";

			ResultSet result = myStmt.executeQuery(sql);

			while (result.next()) {
				Integer v_id = result.getInt(1);
				Integer nr = result.getInt(2);
				Integer currentSpeed = result.getInt(3);
				Integer maxSpeed = result.getInt(4);
				Integer speedLimit = result.getInt(5);

				Vehicle object = new Vehicle(Type.CAR);                   // ??????????
						
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
			System.out.print("Laden der Daten nicht m�glich!!!");
		}
		finally
		{
			myConn.close();
		}
		return null;


	}

	
	public void render(Vehicle object) {

		final Vehicle vehicle = object; // To rename fitting

		boolean vehicleForeseeableAhead = render_byOtherVehicles(vehicle);

		if (!vehicleForeseeableAhead) {
			render_bySpeedLimits(vehicle);
		}

		render_moveForward(vehicle);
	}

	/**
	 * 
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
	 *         vehicle can be found until end of street
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
		final Deque<Cell> vehicleCells = vehicle.getBlockedCells();

		final Deque<Cell> newCells = new LinkedList<>();
		for (Cell cell : vehicleCells) {
			newCells.addFirst(cell.getLeftNeighbour());
		}

		vehicle.setBlockedCells(newCells);
	}

	private void turnRight(Vehicle vehicle) {

		if (vehicle.getLane().isTopRightLane()) {
			throw new RuntimeException("Cannot overtake on top left lane"); // todo replace due to SimulationException
		}
		final Deque<Cell> vehicleCells = vehicle.getBlockedCells();

		final Deque<Cell> newCells = new LinkedList<>();
		for (Cell cell : vehicleCells) {
			newCells.addFirst(cell.getRightNeighbour());
		}

		vehicle.setBlockedCells(newCells);
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
