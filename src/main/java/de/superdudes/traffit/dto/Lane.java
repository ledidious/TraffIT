package de.superdudes.traffit.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of = {"index"})
public class Lane extends SimulationObject {

	@NonNull
	private Street street;

	@NonNull
	private Integer index;

	@NonNull
	private Cell[] cells;

	public Lane(@NonNull Street street, @NonNull Integer index) {
		super();

		this.index = index;
		this.street = street;
		this.cells = new Cell[street.getLength()];

		for (int i = 0; i < cells.length; i++) {
			cells[i] = new Cell(i, this);
		}
	}

	public boolean isTopLeftLane() {
		return index <= 0;
	}

	public boolean isTopRightLane() {
		return index == street.getLaneCount() - 1;
	}

	/**
	 * Intelligent setter that automatically fetches the index of the cell!
	 *
	 * @param cell
	 */
	public void setCell(@NonNull Cell cell) {
		cells[cell.getIndex()] = cell;
	}

	public Cell getCell(int index) {
		return cells[index];
	}

	public Cell getFirstCell() {
		return cells[0];
	}

	public Cell getLatestCell() {
		return cells[cells.length - 1];
	}

	public int getLength() {
		return getStreet().getLength();
	}

	public void removeAllObjects() {

		for (Cell cell : getCells()) {
			if (cell.getBlockingVehicle() != null) {
				cell.getBlockingVehicle().removeMe();
			}
			if (cell.getBlockingConstructionSite() != null) {
				cell.getBlockingConstructionSite().removeMe();
			}
			if (cell.getStreetSign() != null) {
				cell.getStreetSign().removeMe();
			}
		}
	}
}
