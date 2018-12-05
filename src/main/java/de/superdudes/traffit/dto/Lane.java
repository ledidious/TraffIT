package de.superdudes.traffit.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString(of = { "nr" })
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

		for (int i = 0; i < cells.length; i++) {
			cells[i] = new Cell(i, this);
		}
		
		for (int i = 1; i < cells.length; i++) {
			Cell currentCell = cells[i];
			Cell previousCell = cells[i - 1];
			Cell nextCell = cells[i + 1];
			
			currentCell.setAncestor(previousCell);
			currentCell.setSuccessor(nextCell);
		}
	}

	public boolean addConstuctionSite(@NonNull ConstructionSite constructionSite) {
		return constructionSites.add(constructionSite);
	}

	public boolean removeconstructionSite(@NonNull ConstructionSite constructionSite) {
		return constructionSites.remove(constructionSite);
	}

	public Cell getFirstCell() {
		return cells[0];
	}

	public Cell getLatestCell() {
		return cells[cells.length - 1];
	}
}
