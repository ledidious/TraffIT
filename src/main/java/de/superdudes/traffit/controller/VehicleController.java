package de.superdudes.traffit.controller;

import de.superdudes.traffit.dto.Cell;
import de.superdudes.traffit.dto.Vehicle;

public class VehicleController {

	private static class Singletons {

		private static final VehicleController INSTANCE = new VehicleController();
	}

	public static VehicleController instance() {
		return Singletons.INSTANCE;
	}
}
