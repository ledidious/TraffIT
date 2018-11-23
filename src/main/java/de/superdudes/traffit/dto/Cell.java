package de.superdudes.traffit.dto;

import javax.print.attribute.standard.RequestingUserName;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of = { "nr" })
public class Cell extends SimulationObject {

	@NonNull
	private Integer nr;
	
	@NonNull
	private Lane lane;

	private Vehicle blockingVehicle;
	private ConstructionSite blockingConstructionSite;

	private Cell ancestor;
	private Cell successor;

	public Cell(@NonNull Integer nr, @NonNull Lane lane) {
		super();
		
		this.nr = nr;
		this.lane = lane;
	}
	
	public boolean isBlocked() {
		return blockingVehicle != null || blockingConstructionSite != null;
	}
}
