package de.superdudes.traffit.exception;

import de.superdudes.traffit.dto.SimulationObject;
import lombok.Getter;

@Getter
public class ObjectTooCloseException extends ObjectMisplacedException {

	private final SimulationObject otherObject;

	public ObjectTooCloseException(SimulationObject whichObject, SimulationObject otherObject) {
		this(whichObject, otherObject, null);
	}

	public ObjectTooCloseException(SimulationObject whichObject, SimulationObject otherObject, String message) {
		super(whichObject, message);
		this.otherObject = otherObject;
	}

	@Override
	public String getMessage() {
		return new StringBuilder().append(getWhichObject()).append(" and ").append(getOtherObject()).append(": ").append(super.getMessage()).toString();
	}
}
