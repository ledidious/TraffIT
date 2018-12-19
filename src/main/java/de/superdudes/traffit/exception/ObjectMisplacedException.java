package de.superdudes.traffit.exception;

import de.superdudes.traffit.dto.SimulationObject;
import lombok.Getter;

@Getter
public class ObjectMisplacedException extends RuntimeException {

	private SimulationObject whichObject;
	
	public ObjectMisplacedException(SimulationObject whichObject) {
		super();
		this.whichObject = whichObject;
	}

	public ObjectMisplacedException(SimulationObject whichObject, String message) {
		super(message);
		this.whichObject = whichObject;
	}
	
	@Override
	public String getMessage() {
		return "Object: " + whichObject + ". " + super.getMessage();
	}
}
