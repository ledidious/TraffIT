package de.superdudes.traffit.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of = { "nr" })
public class Cell extends SimulationObject {

	@NonNull
	private Integer index;

	@NonNull
	private Lane lane;

	private Vehicle blockingVehicle;
	private ConstructionSite blockingConstructionSite;

	private Cell ancestor;
	private Cell successor;

	public Cell( @NonNull Integer index, @NonNull Lane lane ) {
	    super();
	
	    this.index = index;
	    this.lane = lane;
	}

	public boolean isBlocked() {
		return blockingVehicle != null || blockingConstructionSite != null;
	}
	
	public Cell getLeftNeighbour() {
		
		if (index <= 0) {
			return null;
		}
		
		final Lane leftLane = lane.getStreet().getLanes().get(lane.getIndex() - 1);
		return leftLane.getCells()[index];
	}
	
	public Cell getRightNeighbour() {
		
		if (index >= lane.getStreet().getLaneCount()) {
			return null;
		}
		
		final Lane rightLane = lane.getStreet().getLanes().get(lane.getIndex() + 1);
		return rightLane.getCells()[index];
	}
}
