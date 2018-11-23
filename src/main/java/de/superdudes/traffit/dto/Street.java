package de.superdudes.traffit.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of = { "nr" })
public class Street extends SimulationObject {

	@NonNull
	private Integer length;

	@NonNull
	private List<Lane> lanes = new ArrayList<>();

	public Street(@NonNull Integer length) {
		super();
		
		this.length = length;
	}
}
