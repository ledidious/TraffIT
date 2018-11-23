package de.superdudes.traffit.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of = {"nr"})
public class Lane extends SimulationObject {

	@NonNull
	private Street street;
	
	@NonNull
	private Cell[] cells;
	
	@NonNull
	private Set<ConstructionSite> constructionSites = new HashSet<>();
	
	public Lane(@NonNull Street street) {
		super();
		
		this.street = street;
		this.cells = new Cell[street.getLength()];
	}
}
