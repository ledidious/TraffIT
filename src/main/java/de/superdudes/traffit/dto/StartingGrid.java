package de.superdudes.traffit.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString(of = {"name"})
public class StartingGrid extends SimulationObject {

	private Street street;

	@NonNull
	private String name;

	@NonNull
	private Set<Vehicle> vehicles = new HashSet<>();

	// =============================================================================================
	// Constructor
	// =============================================================================================

	public StartingGrid(String name) {
		this.name = name;
	}

	// =============================================================================================
	// Methods
	// =============================================================================================

	// Accessors
	// ======================================================

	// Please use cells to
	void addVehicle(@NonNull Vehicle vehicle) {
		vehicles.add(vehicle);
	}

	void removeVehicle(@NonNull Vehicle vehicle) {
		vehicles.remove(vehicle);
	}

	public void restoreFrom(@NonNull StartingGrid startingGrid) {
		name = startingGrid.name;
		vehicles = startingGrid.vehicles;
		street = startingGrid.street;
	}
}
