package de.superdudes.traffit.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

import javax.swing.text.AbstractDocument.LeafElement;

import de.superdudes.traffit.controller.AbstractController;

@Getter
@Setter
@ToString(of = { "nr" })
public class Lane extends SimulationObject  {

	@NonNull
	private Integer index;
	
	@NonNull
	private Street street;

	@NonNull
	private Cell[] cells;

	@NonNull
	private Set<ConstructionSite> constructionSites = new HashSet<>();

	public Lane(@NonNull Street street, @NonNull Integer index) {
		super();

		this.index = index;
		this.street = street;
		this.cells = new Cell[street.getLength()];

		for (int i = 0; i < cells.length; i++) {
			cells[i] = new Cell(i, this);
		}
		
		cells[0].setSuccessor(cells[1]); // First cell
		for (int i = 1; i < cells.length - 1; i++) { // Second until second-last cell
			Cell currentCell = cells[i];
			Cell previousCell = cells[i - 1];
			Cell nextCell = cells[i + 1];
			
			currentCell.setAncestor(previousCell);
			currentCell.setSuccessor(nextCell);
		}
		cells[cells.length - 1].setAncestor(cells[cells.length - 2]); // Last cell
	}
	
	public boolean isTopLeftLane() {
		return index <= 0;
	}
	
	public boolean isTopRightLane() {
		return index == street.getLength() - 1;
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
