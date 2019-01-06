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

	/**
	 * Cache, so not modifiable
	 */
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

	/**
	 * Please instead use cells to add vehicles.
	 *
	 * @param vehicle
	 */
	void addVehicle(@NonNull Vehicle vehicle) {
		vehicles.add(vehicle);
	}

	void removeVehicle(@NonNull Vehicle vehicle) {
		vehicles.remove(vehicle);
	}

	private void setVehicles(@NonNull Set<Vehicle> vehicles) {
		throw new UnsupportedOperationException("Not settable");
	}

	public void restoreFrom(@NonNull StartingGrid startingGrid) {
		name = startingGrid.name;

		vehicles = startingGrid.vehicles;
		for (Vehicle vehicle : vehicles) {
			vehicle.setStartingGrid(this);
		}

		street = startingGrid.street;
		street.setStartingGrid(this);
	}
}
