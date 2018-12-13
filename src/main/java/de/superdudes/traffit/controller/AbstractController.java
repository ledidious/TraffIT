package de.superdudes.traffit.controller;

import de.superdudes.traffit.dto.SimulationObject;

public abstract class AbstractController<T extends SimulationObject> {

	String url = "jdbc:mariadb://localhost/TraffIT";
	
	public abstract void save(T object);

	public abstract void load(T object);

	public abstract void render(T object);
}
