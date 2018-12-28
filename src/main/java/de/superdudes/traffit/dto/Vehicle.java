package de.superdudes.traffit.dto;

import de.superdudes.traffit.exception.ObjectMisplacedException;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Deque;
import java.util.LinkedList;

@Getter
@Setter
public class Vehicle extends SimulationObject implements AttachedToCell {

//	// Constants used for driving
//	public static final int DRIVE_LIMIT_TO_RESET_COUNTER = 1;
//	public static final int DRIVE_COUNTER_INCREASE = 5;

	public static final int DRIVE_WAIT_UNLIMITED = -1;
	public static final int DRIVE_WAIT_MAX = 400;

	// Constants used for overtaking logic
	public static final int ANCESTOR_DISTANCE_TO_RECOGNIZE_SPEED = Type.CAR.getLength() * 2;
	public static final int ANCESTOR_DISTANCE_MIN = Type.CAR.getLength();

	// Attribute limits
	public static final int MAX_SPEED = 300;

	public enum Type {

		CAR(20, 250),
		TRUCK(40, 90),
		MOTORCYCLE(5, 120);

		private static final double MAX_SPEED_DEVIATION = 0.2;

		@Getter
		private int length;
		private int avgMaxSpeed;

		private Type(int length, int avgMaxSpeed) {
			this.length = length;
			this.avgMaxSpeed = avgMaxSpeed;
		}

		public int randomMaxSpeed() {
			int lowerBorder = (int) (avgMaxSpeed - (avgMaxSpeed * MAX_SPEED_DEVIATION));
			int upperBorder = (int) (avgMaxSpeed + (avgMaxSpeed * MAX_SPEED_DEVIATION));

			return (int) (lowerBorder + Math.random() * (upperBorder - lowerBorder));
		}
	}

	@NonNull
	private StartingGrid startingGrid;

	@NonNull
	private Type type;

	@NonNull
	private Deque<Cell> blockedCells = new LinkedList<>();

	@NonNull
	private Integer maxSpeed;

	// Volatile thus not in db !
	private Integer speedLimit;

	private Integer currentSpeed = 0;

	private int gensToWaitUntilDrive = -1; // Never set
	// Counter to drive
	private int gensSinceLastDrive = -1; // Never set

	public Vehicle(@NonNull Type type, @NonNull Cell tailCell) {
		this(type, tailCell, true);
	}

	public Vehicle(@NonNull Type type, @NonNull Cell tailCell, boolean cellSuccessorsLoaded) {
		this.type = type;
		this.startingGrid = tailCell.getLane().getStreet().getStartingGrid();
		this.maxSpeed = type.randomMaxSpeed();

		if (cellSuccessorsLoaded) {
			connectNewCells(tailCell);
		} else {
			// Add to blockedCells so that getTailCell() is working
			blockedCells.addFirst(tailCell);
		}

		gensToWaitUntilDrive = renderGensToWaitUntilDrive();
	}

	@Override
	public Integer getLength() {
		return type.getLength();
	}

	public Cell getFrontCell() {
		return blockedCells.getFirst();
	}

	@Deprecated
	public Cell getBackCell() {
		return getTailCell();
	}

	@Override
	public Cell getTailCell() {
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
		gensToWaitUntilDrive = renderGensToWaitUntilDrive();

		final Cell removedCell = blockedCells.removeLast();
		removedCell.setBlockingVehicle(null);

		final Cell addedCell = blockedCells.getFirst().getSuccessor();
		if (addedCell == null) {
			setToStartOfLane();
		} else {
			blockedCells.addFirst(addedCell);
			addedCell.setBlockingVehicle(this);
		}
	}

	public void dontDrive() {
		gensToWaitUntilDrive--;
	}

	public void setToStartOfLane() {
		connectNewCells(getLane().getFirstCell());
	}

	public boolean mayDrive() {
		return gensToWaitUntilDrive <= 0;
	}

	private int renderGensToWaitUntilDrive() {
		return currentSpeed <= 0 ? DRIVE_WAIT_UNLIMITED : DRIVE_WAIT_MAX / currentSpeed;
	}

	public void turnLeft() {
		connectNewCells(getTailCell().getLeftNeighbour());
	}

	public void turnRight() {
		connectNewCells(getTailCell().getRightNeighbour());
	}

	private void connectNewCells(@NonNull Cell tailCell) {

		// Free old
		for (Cell blockedCell : blockedCells) {
			blockedCell.setBlockingVehicle(null);
		}
		blockedCells.clear();

		// todo unify in AttachedToCell
		// Block new cells
		Cell currentCell = tailCell;
		for (int i = 0 /* First cell already set */; i < type.getLength(); i++) {

			if (currentCell == null) {
				throw new ObjectMisplacedException(this, "Reaches the end of street");
			}

			if (currentCell.isBlocked()) {
				throw new ObjectMisplacedException(this, "Blocked by " + currentCell.getBlockingObject());
			}

			blockedCells.addFirst(currentCell); // Habe ich über die Prüfung gezogen, damit auch die tailCell dem Vehicle hinzugefügt wird. ;)
			currentCell.setBlockingVehicle(this);
			currentCell = currentCell.getSuccessor(); // Hab ich nach untern gezogen, damit auch die erste Zelle hinzugefügt wird.
		}
	}

	public void setMaxSpeed(@NonNull Integer maxSpeed) {
		this.maxSpeed = Math.min(MAX_SPEED, maxSpeed);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "{type=" + getType() + ", lane=" + getLane().getIndex() + ", headCell=" + getFrontCell().getIndex() + "}";
	}
}

