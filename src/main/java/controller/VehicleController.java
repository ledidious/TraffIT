package controller;


public class VehicleController {

	private static class Singletons {

		private static final VehicleController INSTANCE = new VehicleController();
	}
	
	public static VehicleController instance() {
		return Singletons.INSTANCE;
	}
}
