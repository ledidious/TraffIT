package de.superdudes.traffit.controller;

import de.superdudes.traffit.dto.Cell;
import de.superdudes.traffit.dto.Lane;
import de.superdudes.traffit.dto.StartingGrid;
import de.superdudes.traffit.dto.Street;
import de.superdudes.traffit.dto.Vehicle;

public class Main {

	public static void main(String[] args) {
		
		final StartingGrid startingGrid = szenario1();
		consoleOutput(startingGrid);
	}
	
	private static StartingGrid szenario1() {

		final Street street = new Street(300, 2);
		final StartingGrid startingGrid = new StartingGrid("try", street);
		
		final Vehicle vehicle = new Vehicle(Vehicle.Type.CAR);
		final Vehicle vehicle2 = new Vehicle(Vehicle.Type.TRUCK);
		final Vehicle vehicle3 = new Vehicle(Vehicle.Type.MOTORCYCLE);
		
		street.getLanes().get(0).getCells()[1].setBlockingVehicle(vehicle);

		startingGrid.addVehicle(vehicle);
		startingGrid.addVehicle(vehicle2);
		startingGrid.addVehicle(vehicle3);
		
		return startingGrid;
		
	}
	
	private static void consoleOutput(StartingGrid startingGrid) {
		
		final Street street = startingGrid.getStreet();
		
		for (int i = 0; i < street.getLength(); i++) {
			System.out.print("-");
		}
		System.out.println();
		
		for (Lane lane : street.getLanes()) {
		
			for (Cell cell : lane.getCells()) {
				if (cell.getBlockingVehicle() != null) {
					System.out.print("*");
				}
				if (cell.getBlockingConstructionSite() != null) {
					System.out.print("#");
				}
			}
			System.out.println();
			
			for (int i = 0; i < street.getLength(); i++) {
				System.out.print("-");
			}
			
			System.out.println();
		}
	}
}