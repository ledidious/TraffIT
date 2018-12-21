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
        return "Volatile: " + getWhichObject() + "." + super.getMessage();
    }
}
