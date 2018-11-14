package de.superdudes.traffit.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vehicle extends DatabaseObject {

	private Integer length;
	private Integer maxSpeed;
	private Integer speedLimt;
	
	public void computeNextCell()
	{
	
	}
}
