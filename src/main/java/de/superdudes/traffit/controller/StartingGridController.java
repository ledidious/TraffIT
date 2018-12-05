package de.superdudes.traffit.controller;


public class StartingGridController {

	private static class Singletons {

		private static final StartingGridController INSTANCE = new StartingGridController();
	}

	public static StartingGridController instance() {
		return Singletons.INSTANCE;
	}
	
	
}
