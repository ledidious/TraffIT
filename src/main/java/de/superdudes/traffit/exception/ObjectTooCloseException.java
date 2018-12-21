package de.superdudes.traffit.exception;

import de.superdudes.traffit.dto.SimulationObject;

public class ObjectTooCloseException extends ObjectMisplacedException {
	
	public ObjectTooCloseException(SimulationObject whichObject) {
		super(whichObject);
	}

	public ObjectTooCloseException(SimulationObject whichObject, String message) {
		super(whichObject, message);
	}

}
