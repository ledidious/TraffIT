package de.superdudes.traffit.controller;

import java.sql.SQLException;

import de.superdudes.traffit.dto.SimulationObject;

public abstract class AbstractController<T extends SimulationObject> {

	String  url = "jdbc:mariadb://localhost/TraffIT";
	String  user = "TraffIT";
	String  pw  = "TraffIT";
	
	public abstract void save(T object) throws SQLException;

	public abstract T load(Integer Id) throws SQLException;

	public void render(T object) {
		// Nothing to do except if overridden
	}
}
