package de.superdudes.traffit.dto;

import de.superdudes.traffit.exception.ObjectMisplacedException;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of = { "nr", "speedLimit" })
public class StreetSign extends SimulationObject {

	public static final int DEFAULT_SPEED = 100;

	@NonNull
	private Integer speedLimit;

	@NonNull
	private Cell tailCell;

	@NonNull
	private Integer length;

	public StreetSign(@NonNull Integer speedLimit, @NonNull Cell tailCell, @NonNull Integer length) {
		super();

		this.speedLimit = speedLimit;
		this.length = length;

		// Link on other side
		tailCell.setStreetSign(this);
		
		setTailCell(tailCell);
	}
	
	public void setTailCell(Cell tailCell) {

		Cell currentCell = tailCell;
		for (int i = 0 /* First cell already set */; i < length; i++) {
			currentCell = currentCell.getSuccessor();

			if (currentCell == null) {
				throw new ObjectMisplacedException(this, "Reaches the end of street");
			}
			if (currentCell.getStreetSign() != null) {
				throw new ObjectMisplacedException(this, "Blocked by " + currentCell.getStreetSign());
			}
			currentCell.setStreetSign(this);
		}
		
		this.tailCell = tailCell;
	}

	private void setLength(@NonNull Integer length) {
		this.length = length;
	}
}
