package de.superdudes.traffit.controller;

<<<<<<< Updated upstream
import de.superdudes.traffit.ControllerManager;
=======
import java.sql.SQLException;

>>>>>>> Stashed changes
import de.superdudes.traffit.dto.Cell;
import de.superdudes.traffit.dto.Lane;
import de.superdudes.traffit.dto.StartingGrid;
import de.superdudes.traffit.dto.Street;
import de.superdudes.traffit.dto.Vehicle;

public class Main {

	public static void main(String[] args) {
		
	     StartingGrid sg = new StartingGrid("TraffIT", new Street(1, 3));
	     
	     sg.setId(1);
	     sg.setNr(4);
	     sg.setName("Strasse");
	     
	     StartingGridController sgc = new StartingGridController();
	     
	     try 
	     {
			sgc.save(sg);
		 } 
	     catch (SQLException e) 
	     {
		    e.printStackTrace();
		 }
	     
	     
		
	/*		final StartingGrid startingGrid = szenario1();
		 consoleOutput(startingGrid);
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
<<<<<<< Updated upstream
	}
=======
		
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
		}                                      */
	}                                                                        
>>>>>>> Stashed changes
}
