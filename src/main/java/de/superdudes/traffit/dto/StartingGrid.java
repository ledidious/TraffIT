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

    @NonNull
    private String name;

    @NonNull
    private Set<Vehicle> vehicles = new HashSet<>();

    private Street street;

    public StartingGrid(String name) {
        this.name = name;
    }

    // Please use cells to
    void addVehicle(@NonNull Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    void removeVehicle(@NonNull Vehicle vehicle) {
        vehicles.remove(vehicle);
    }
}
