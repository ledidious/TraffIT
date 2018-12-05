package de.superdudes.traffit.controller;

public class StreetController {

	private static class Singletons {

		private static final StreetController INSTANCE = new StreetController();
	}

	public static StreetController instance() {
		return Singletons.INSTANCE;
	}

}
