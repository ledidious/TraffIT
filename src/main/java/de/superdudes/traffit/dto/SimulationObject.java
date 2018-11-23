package de.superdudes.traffit.dto;

import javax.swing.text.InternationalFormatter;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true, of = "nr")
@ToString(of = {"nr"})
public class SimulationObject extends DatabaseObject {

	private static int nrCounter = 0;
	
	@NonNull
	private Integer nr;
	
	public SimulationObject() {
		this(null);
	}
	
	public SimulationObject(@NonNull Integer id) {
		super(id);
		
		this.nr = nrCounter;
		
		nrCounter++;
	}
}
