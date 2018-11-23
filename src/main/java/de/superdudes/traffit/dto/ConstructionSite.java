package de.superdudes.traffit.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of = {"nr", "length"})
public class ConstructionSite extends SimulationObject {

	@NonNull
	private Integer length;
	
	public ConstructionSite(@NonNull Integer length) {
		this.length = length;
	}
}
