package de.superdudes.traffit.dto;

import java.util.Deque;

import javax.swing.text.AbstractDocument.LeafElement;

import de.superdudes.traffit.exception.ObjectMisplacedException;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class Vehicle extends SimulationObject {

	public static final int MAX_SPEED = 200;

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

	private int gensSinceLastDrive = -1; // Never set

	public Vehicle(@NonNull Type type, @NonNull Cell tailCell) {
		this.type = type;

		Cell currentCell = tailCell;
		for (int i = 0 /* First cell already set */; i < type.getLength(); i++) {
			currentCell = currentCell.getSuccessor();

			if (currentCell == null) {
				throw new ObjectMisplacedException(this, "Reaches the end of street");
			}
			if (currentCell.isBlocked()) {
				throw new ObjectMisplacedException(this, "Blocked by " + currentCell.getBlockingObject());
			}
			blockedCells.addFirst(currentCell);
			currentCell.setBlockingVehicle(this);
		}
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

	// Moves one cell forward
	public void drive() {
		gensSinceLastDrive = 0;

		blockedCells.removeLast();
		blockedCells.addFirst(blockedCells.getLast().getSuccessor());
	}

	public void dontDrive() {
		gensSinceLastDrive++;
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

	public void setMaxSpeed(@NonNull Integer maxSpeed) {
		this.maxSpeed = Math.max(MAX_SPEED - 1, maxSpeed);
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "{type=" + getType() + ", lane=" + getLane().getIndex() + ", headCell=" + getFrontCell().getIndex() + "}";
	}
}