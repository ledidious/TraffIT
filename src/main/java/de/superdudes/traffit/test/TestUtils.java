package de.superdudes.traffit.test;

import de.superdudes.traffit.dto.*;

public class TestUtils {

	public static void outputOnConsole(StartingGrid startingGrid) {

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
