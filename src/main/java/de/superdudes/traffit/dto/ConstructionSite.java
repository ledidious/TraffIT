package de.superdudes.traffit.dto;

import de.superdudes.traffit.exception.ObjectMisplacedException;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString( of = { "nr", "length" } )
public class ConstructionSite extends SimulationObject {

    @NonNull
    private Integer length;
    
    @NonNull
    private Cell tailCell;

    public ConstructionSite( @NonNull Integer length, @NonNull Cell tailCell ) {
        this.length = length;
        
        setTailCell(tailCell);
    }
    
    public void setTailCell(@NonNull Cell tailCell) {
    	
    	Cell currentCell = tailCell;
		for (int i = 0 /* First cell already set */; i < length; i++) {
			currentCell = currentCell.getSuccessor();

			if (currentCell == null) {
				throw new ObjectMisplacedException(this, "Reaches the end of street");
			}
			if (currentCell.isBlocked()) {
				throw new ObjectMisplacedException(this, "Blocked by " + currentCell.getBlockingObject());
			}
			
			currentCell.setBlockingConstructionSite(this);
		}
		
		this.tailCell = tailCell;
    }
}
