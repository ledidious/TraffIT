package de.superdudes.traffit.dto;

import java.awt.Window.Type;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of = {"nr"})
public class Vehicle extends SimulationObject {

	public enum Type {
		
		CAR(20), TRUCK(40), MOTORCYCLE(5);
	
		@Getter
		private int length;
		
		private Type(int length) {
			this.length = length;
		}
	}
	
	@NonNull
	private Integer maxSpeed;
	
	private Integer speedLimit;
	
	@NonNull
	private Integer nr;
	
	@NonNull
	private Type type;
	
	@NonNull
	private Cell[] blockedCells;
	
	public Vehicle(@NonNull Integer nr, @NonNull Type type ) {
		this.nr = nr;
		this.type = type;
		
		this.blockedCells = new Cell[getLength()];
	}
	
	public int getLength() {
		return type.getLength();
	}
	
	// todo implement method which speed to hold dependent of 
	// - max speed
	// - speed limit 
	// - speed from ancestor
	
	// public Vehicle findAncestor()
}