package de.superdudes.traffit.exception;

public class SimulationInDbNotPresentException extends RuntimeException {

	public SimulationInDbNotPresentException() {
	}

	public SimulationInDbNotPresentException(String message) {
		super(message);
	}
}
