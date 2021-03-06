package de.superdudes.traffit.dto;

import de.superdudes.traffit.exception.ObjectMisplacedException;
import de.superdudes.traffit.exception.ObjectTooCloseException;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Deque;
import java.util.LinkedList;
import java.util.function.UnaryOperator;

@Getter
@Setter
// todo common super class with Vehicle such as BlockingObject, then logic in VehicleController also becomes easier!
public class ConstructionSite extends SimulationObject implements AttachedToCell {

	private static final int MIN_DISTANCE = 100;

	@NonNull
	private Deque<Cell> blockedCells = new LinkedList<>();

	@NonNull
	private int length;

	public ConstructionSite(int length, @NonNull Cell tailCell) {
		this(length, tailCell, true);
	}

	// ! Please only use in ConstructionSiteController
	// If cellSuccessorLoaded == false, please maintain other cells afterwards as
	// soon as loaded
	public ConstructionSite(@NonNull Integer length, @NonNull Cell tailCell, boolean cellSuccessorLoaded) {
		this.length = length;

		if (cellSuccessorLoaded) {
			setTailCell(tailCell, length);
		} else {
			// Add to blockedCells so that getTailCell() is working
			blockedCells.addFirst(tailCell);
		}
	}

	private void setTailCell(@NonNull Cell tailCell, int length) {

		// Check cells within constructionSite
		Cell currentCell = tailCell;
		for (int i = 0 /* First cell already set */; i < length; i++) {

			if (currentCell == null) {
				throw new ObjectMisplacedException(this, "Reaches the end of street");
			}

			if (currentCell.isBlocked()) {
				throw new ObjectMisplacedException(this, "Blocked by " + currentCell.getBlockingObject());
			}

			// Not allowed if left and right constructionSite is also blocked by a constructionSite
			checkNeighbourCellsNotBothBlocked(currentCell);

			blockedCells.addFirst(currentCell);
			currentCell.setBlockingConstructionSite(this);
			currentCell = currentCell.getSuccessor();
		}

		try {
			// Do checks before connecting to cells (otherwise saved with inconsistent state)
			checkMinDistanceToOtherBlockingObjects(); // Vehicles and construction sites
			checkMinDistanceBeforeAndAfter(blockedCells.getLast(), Cell::getAncestor); // Only construction sites
			checkMinDistanceBeforeAndAfter(blockedCells.getFirst(), Cell::getSuccessor); // Only construction sites
		}
		catch (ObjectMisplacedException e) {
			// todo find a prettier solution (is ugly but quickly solved)
			// In case of validation failure, remove construction site and do not save in inconsistent state
			removeMe();
			throw e;
		}
	}

	private void checkNeighbourCellsNotBothBlocked(Cell cell) {
		if ((cell.getRightNeighbour() == null || cell.getRightNeighbour().isBlocked())
				&& (cell.getLeftNeighbour() == null || cell.getLeftNeighbour().isBlocked())) {
			throw new ObjectTooCloseException(this, /* Information not provided */ null, "Left and right lane also blocked by constructionSites");
		}
	}

	private void checkMinDistanceBeforeAndAfter(Cell firstCellToCheck, UnaryOperator<Cell> cellHiker) {

		Cell currentCell = firstCellToCheck;
		for (int j = 0; j <= MIN_DISTANCE; j++) {
			currentCell = cellHiker.apply(currentCell);

			if (currentCell == null) {
				break;
			}
			if (currentCell.isBlocked() && !equals(currentCell.getBlockingConstructionSite())) {
				throw new ObjectTooCloseException(this, currentCell.getBlockingConstructionSite(), "On same lane");
			}
			checkNeighbourCellsNotBothBlocked(currentCell);
		}
	}

	@Override
	public Cell getTailCell() {
		return blockedCells.getLast();
	}

	@Override
	public void removeMe() {
		for (Cell blockedCell : blockedCells) {
			blockedCell.setBlockingConstructionSite(null);
		}
	}

	private void setBlockedCells() {
		throw new UnsupportedOperationException();
	}

	public Lane getLane() {
		return getTailCell().getLane();
	}

	@Override
	public Integer getLength() {
		return length;
	}

	@Override
	public String toString() {
		return new StringBuilder(getClass().getSimpleName()).append('{')
				.append("tailCell=").append(getTailCell().getIndex()).append(",")
				.append("lane=").append(getLane().getIndex())
				.append("}")
				.toString();
	}
}
