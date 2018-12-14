package de.superdudes.traffit.dto;

import java.util.Deque;
import java.util.LinkedList;

import de.superdudes.traffit.controller.VehicleController;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import sun.reflect.*;

@Getter
@Setter
@ToString(of = { "nr" })
public class Vehicle extends SimulationObject {

	public static final int ANCESTOR_DISTANCE_TO_RECOGNIZE_SPEED = Type.CAR.getLength() * 2;
	public static final int ANCESTOR_DISTANCE_MIN = Type.CAR.getLength();

	public enum Type {

		CAR(20), TRUCK(40), MOTORCYCLE(5);

		@Getter
		private int length;

		private Type(int length) {
			this.length = length;
		}
	}

	@NonNull
	private Integer maxSpeed;

	private Integer speedLimit;

	@NonNull
	private Integer currentSpeed;

	@NonNull
	private Type type;

	@NonNull
	private Deque<Cell> blockedCells;

	public Vehicle(@NonNull Type type) {
		this.type = type;

		this.blockedCells = new LinkedList<>();
	}

	public int getLength() {
		return type.getLength();
	}

	public Cell getFrontCell() {
		return blockedCells.getFirst();
	}

	public Cell getBackCell() {
		return blockedCells.getLast();
	}

	public Lane getLane() {
		return getFrontCell().getLane();
	}
	
	public void accelerate() {
		if (currentSpeed < maxSpeed) {
			currentSpeed++;
		}
	}
	
	public void brake() {
		if (currentSpeed > 0) {
			currentSpeed--;
		}
	}

	public void setBlockedCells(@NonNull Deque<Cell> newCells) {

		// if (!Reflection.getCallerClass().equals(VehicleController.class)) {
		// throw new SecurityError();
		// }

		if (newCells.size() != getLength()) {
			throw new IllegalArgumentException("Wrong number of cells corresponding to getLength()");
		}
		
		// Free old cells
		for (Cell cell : this.blockedCells) {
			cell.setBlockingVehicle(null);
		}
		
		// Block new cells
		for (Cell cell : newCells) {
			cell.setBlockingVehicle(this);
		}
		
		this.blockedCells = newCells;
	}
}