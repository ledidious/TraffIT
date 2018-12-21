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
    private StartingGrid startingGrid;

    @NonNull
    private Type type;

    @NonNull
    private Integer maxSpeed;

    // Volatile thus not in db !
    private Integer speedLimit;

    private Integer currentSpeed;

    @NonNull
    private Deque<Cell> blockedCells = new LinkedList<>();

    private int gensSinceLastDrive = -1; // Never set

    public Vehicle(@NonNull Type type, @NonNull Cell tailCell) {
        this(type, tailCell, true);
    }

    public Vehicle(@NonNull Type type, @NonNull Cell tailCell, boolean cellSuccessorsLoaded) {
        this.type = type;
        this.startingGrid = tailCell.getLane().getStreet().getStartingGrid();

        if (cellSuccessorsLoaded) {
            connectNewCells(tailCell);
        }
        else {
            // Add to blockedCells so that getTailCell() is working
            blockedCells.addFirst(tailCell);
        }
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
        gensSinceLastDrive = 0;

        final Cell removedCell = blockedCells.removeLast();
        removedCell.setBlockingVehicle(null);

        final Cell addedCell = blockedCells.getLast().getSuccessor();
        blockedCells.addFirst(addedCell);
        addedCell.setBlockingVehicle(this);
    }

    public void dontDrive() {
        gensSinceLastDrive++;
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
        this.maxSpeed = Math.max(MAX_SPEED - 1, maxSpeed);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{type=" + getType() + ", lane=" + getLane().getIndex() + ", headCell=" + getFrontCell().getIndex() + "}";
    }
}
