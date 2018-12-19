package de.superdudes.traffit.controller;

import de.superdudes.traffit.dto.SimulationObject;

public abstract class AbstractController<T extends SimulationObject> {

	String url = "jdbc:mariadb://localhost/TraffIT";

	public abstract void save(T object);

	public abstract T load(Integer Id);

	public void render(T object) {
		// Nothing to do except if overridden
	}
}
