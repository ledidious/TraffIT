package de.superdudes.traffit.dto;

import java.util.Deque;
import java.util.LinkedList;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of = { "nr" })
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
	private Integer currentSpeed;

	@NonNull
	private Type type;

	@NonNull
	private Deque<Cell> blockedCells;

	public Vehicle(@NonNull Type type) {
		this.type = type;

		this.blockedCells = new LinkedList<>();
	}

	public int getLength() {
		return type.getLength();
	}

	public Cell getLatestCell() {
		return blockedCells.getFirst();
	}

	public Cell getEarliestCell() {
		return blockedCells.getLast();
	}

}