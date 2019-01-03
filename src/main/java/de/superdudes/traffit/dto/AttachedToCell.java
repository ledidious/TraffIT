package de.superdudes.traffit.dto;

import de.superdudes.traffit.exception.ObjectTooCloseException;
import lombok.NonNull;

import java.util.function.BiConsumer;

public interface AttachedToCell {

	/**
	 * Min distance to other blocking objects from interface {@link AttachedToCell}.
	 */
	int DISTANCE_MIN = 30;

	@NonNull
	Integer getLength();

	@NonNull
	Cell getTailCell();

	default Cell getFrontCell() {

		Cell frontCell = getTailCell();
		for (int i = 0; i < getLength(); i++) {
			frontCell = frontCell.getSuccessor();

			if (frontCell == null) {
				throw new NullPointerException("Front cell does not exist. Object '" + this + "' too long: " + getLength());
			}
		}

		return frontCell;
	}

	default <T extends AttachedToCell> void connectCells(Cell tailCell, BiConsumer<Cell, T> connector) {

		// Already connected
		Cell currentCell = tailCell.getSuccessor();

		for (int i = 0 /* First cell already connected */; i < getLength(); i++) {
			connector.accept(currentCell, (T) this);
		}
	}

	default void checkMinDistanceToOtherBlockingObjects() {

		final SimulationObject thisSimulationObject = (SimulationObject) this;

		Cell currentCell = getFrontCell();
		for (int i = 0; i < DISTANCE_MIN; i++) {
			currentCell = currentCell.getSuccessor();

			if (currentCell == null) {
				break; // Street ends here
			}

			if (currentCell.isBlocked()) {
				throw new ObjectTooCloseException(thisSimulationObject, currentCell.getBlockingObject());
			}
		}

		currentCell = getTailCell();
		for (int i = 0; i < DISTANCE_MIN; i++) {
			currentCell = currentCell.getAncestor();

			if (currentCell == null) {
				break; // Street ends here
			}

			if (currentCell.isBlocked()) {
				throw new ObjectTooCloseException(thisSimulationObject, currentCell.getBlockingObject());
			}
		}
	}
}
