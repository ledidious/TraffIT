package de.superdudes.traffit.controller;

public class ConstructionSiteController {

	private static class Singletons {

		private static final ConstructionSiteController INSTANCE = new ConstructionSiteController();
	}

	public static ConstructionSiteController instance() {
		return Singletons.INSTANCE;
	}
}
