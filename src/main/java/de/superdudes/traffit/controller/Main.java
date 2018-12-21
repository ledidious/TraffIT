package de.superdudes.traffit.controller;

import java.sql.SQLException;
import java.util.List;

import de.superdudes.traffit.SimulationManager;
import de.superdudes.traffit.dto.*;
import de.superdudes.traffit.dto.Vehicle.Type;

public class Main {

	public static void main(String[] args) throws SQLException {

	    // our test class
		final StartingGrid startingGrid = new StartingGrid("Frank");
		final Street street = new Street(50, 3, startingGrid);
        final Lane lane = street.getLanes().get(0);
        final Cell cell1 = lane.getCells()[0];
        final Cell cell2 = lane.getCells()[25];

        final ConstructionSite constructionSite = new ConstructionSite(20, cell1);
        final Vehicle vehicle = new Vehicle(Type.MOTORCYCLE, cell2);
		final StreetSign streetSign = new StreetSign(50, cell1, 12);

		StartingGridController.instance().save(startingGrid);

		final StartingGrid startingGrid1 = StartingGridController.instance().load();
        System.out.println();
	}
}