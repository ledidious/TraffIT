package de.superdudes.traffit.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString( of = { "nr", "name" } )
public class StartingGrid extends SimulationObject {

    @NonNull
    private Street street;

    @NonNull
    private Set<Vehicle> vehicles = new HashSet<>();

    @NonNull
    private String name;

    public StartingGrid( @NonNull String name, @NonNull Street street ) {
        super();

        this.name = name;
        this.street = street;
    }
    
	public boolean addVehicle(@NonNull Vehicle vehicle) {
		return vehicles.add(vehicle);
	}
	
	public boolean removeVehicle(@NonNull Vehicle vehicle) {
		return vehicles.remove(vehicle);
	}
}
