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

	private StreetSign streetSign;

	public Cell(@NonNull Integer index, @NonNull Lane lane) {
		super();

		this.index = index;
		this.lane = lane;
	}

	// Provides both ConstructionSite and Vehicle for general purposes
	public Object getBlockingObject() {
		return blockingVehicle != null ? blockingVehicle
				: blockingConstructionSite != null ? blockingConstructionSite : null;
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

	// Implement because package-private with restricted access
	// Only invokable by vehicle if blocked by vehicle
	void setBlockingVehicle(Vehicle vehicle) {
		if (vehicle == null) {
			blockingVehicle = null;
		} else {
			if (blockingVehicle != null) {
				throw new IllegalStateException("Already blocked");
			}
			blockingVehicle = vehicle;
		}

		// Cannot be set both
		blockingConstructionSite = null;
	}

	public void setBlockingConstructionSite(ConstructionSite constructionSite) {
		if (constructionSite == null) {
			blockingConstructionSite = null;
		} else {
			if (blockingConstructionSite != null) {
				throw new IllegalStateException("Already blocked");
			}
			blockingConstructionSite = constructionSite;
		}

		// Cannot be set both
		blockingVehicle = null;
	}
}
