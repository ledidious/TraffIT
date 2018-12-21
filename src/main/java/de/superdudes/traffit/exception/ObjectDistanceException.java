package de.superdudes.traffit.exception;


import lombok.Getter;

@Getter
public class ObjectDistanceException extends RuntimeException {

	public ObjectDistanceException(String message) {
		super(message);
	}
	
	@Override
	public String getMessage() {
		return super.getMessage();
	}
}