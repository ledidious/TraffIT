package de.superdudes.traffit.dto;

import de.superdudes.traffit.exception.ObjectDistanceException;
import de.superdudes.traffit.exception.ObjectMisplacedException;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of = { "nr", "length" })
public class ConstructionSite extends SimulationObject {

	@NonNull
	private Integer length;

	@NonNull
	private Cell tailCell;

	public ConstructionSite(@NonNull Integer length, @NonNull Cell tailCell) {
		this.length = length;

		setTailCell(tailCell);
	}

	public void setTailCell(@NonNull Cell tailCell) {

		Cell currentCell = tailCell;	
		Cell currentCellOtherLane = null; 
		
		if(currentCell.getLane().getIndex() == 0) {
			currentCellOtherLane = currentCell.getRightNeighbour();
		} else {
			currentCellOtherLane = currentCell.getLeftNeighbour();
		}
		
		for (int i = 0 /* First cell already set */; i < length; i++) {
			currentCell = currentCell.getSuccessor();

			if (currentCell == null) {
				throw new ObjectMisplacedException(this, "Reaches the end of street");
			}
			if (currentCell.isBlocked()) {
				throw new ObjectMisplacedException(this, "Blocked by " + currentCell.getBlockingObject());
			}

			for (int j = 0; j <= 100; j++) {
				if (currentCell.getAncestor().isBlocked()) {
					throw new ObjectDistanceException("Too short distance from another construction site");
				}
			}

			for (int j = 0; j <= 100 + length; j++) {
				if (currentCell.getSuccessor().isBlocked()) {
					throw new ObjectDistanceException("Too short distance from another construction site");
				}
			} 
			
			for (int j = 0; j <= 100; j++) {
				if (currentCellOtherLane.getAncestor().isBlocked()) {
					throw new ObjectDistanceException("Too short distance from another construction site");
				}
			}

			for (int j = 0; j <= 100 + length; j++) {
				if (currentCellOtherLane.getSuccessor().isBlocked()) {
					throw new ObjectDistanceException("Too short distance from another construction site");
				}
			} 

			currentCell.setBlockingConstructionSite(this);
		}

		this.tailCell = tailCell;
	}
}
