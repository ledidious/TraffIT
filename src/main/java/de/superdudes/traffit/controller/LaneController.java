package de.superdudes.traffit.controller;

public class LaneController {

	private static class Singletons {

		private static final LaneController INSTANCE = new LaneController();
	}

	public static LaneController instance() {
		return Singletons.INSTANCE;
	}
}
