package de.superdudes.traffit.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of = { "nr", "name" })
public class StartingGrid extends SimulationObject {

	@NonNull
	private Street street;
	
	@NonNull
	private Set<Vehicle> vehicles = new HashSet<>();

	@NonNull
	private String name;
	
	public StartingGrid(@NonNull String name, @NonNull Street street) {
		super();
		
		this.name = name;
	}
}
