package controller;


public class CellController {
	
	private static class Singletons {

		private static final CellController INSTANCE = new CellController();
	}
	
	public static CellController instance() {
		return Singletons.INSTANCE;
	}

}
