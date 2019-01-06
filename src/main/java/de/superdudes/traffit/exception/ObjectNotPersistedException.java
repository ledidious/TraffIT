package de.superdudes.traffit.exception;

import de.superdudes.traffit.dto.SimulationObject;
import lombok.Getter;

@Getter
public class ObjectNotPersistedException extends RuntimeException {

    private SimulationObject whichObject;

    public ObjectNotPersistedException(SimulationObject whichObject) {
        this.whichObject = whichObject;
    }

    public ObjectNotPersistedException(SimulationObject whichObject, String message) {
        super(message);
        this.whichObject = whichObject;
    }

    @Override
    public String getMessage() {
        final String message = super.getMessage() + "";
        return "Volatile: " + getWhichObject() + ": " + message;
    }
}
