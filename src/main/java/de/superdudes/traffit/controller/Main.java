package de.superdudes.traffit.controller;

import de.superdudes.traffit.ControllerManager;
import de.superdudes.traffit.dto.Cell;
import de.superdudes.traffit.dto.Lane;
import de.superdudes.traffit.dto.StartingGrid;
import de.superdudes.traffit.dto.Street;
import de.superdudes.traffit.dto.Vehicle;

public class Main {

	public static void main(String[] args) {
		
		
	}
	
	private static StartingGrid szenario1() {

		final Street street = new Street(150, 2);
		final StartingGrid startingGrid = new StartingGrid("try", street);
		
		final Vehicle vehicle = new Vehicle(Vehicle.Type.CAR);
		final Vehicle vehicle2 = new Vehicle(Vehicle.Type.TRUCK);
		final Vehicle vehicle3 = new Vehicle(Vehicle.Type.MOTORCYCLE);

		startingGrid.addVehicle(vehicle);
		startingGrid.addVehicle(vehicle2);
		startingGrid.addVehicle(vehicle3);
		
		return startingGrid;
	}
}
