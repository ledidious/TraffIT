package de.superdudes.traffit.controller;

public class StreetSignController {

	private static class Singletons {

		private static final StreetSignController INSTANCE = new StreetSignController();
	}

	public static StreetSignController instance() {
		return Singletons.INSTANCE;
	}

}
