package de.superdudes.traffit.dto;

import de.superdudes.traffit.exception.ObjectMisplacedException;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.util.Deque;
import java.util.LinkedList;

@Getter
@Setter
@ToString( of = { "length" } )
public class ConstructionSite extends SimulationObject implements AttachedToCell {

    @NonNull
    private Deque<Cell> blockedCells = new LinkedList<>();

    // Not persisted
    @NonNull
    private int length;

    public ConstructionSite( int length, @NonNull Cell tailCell ) {
        this( length, tailCell, true );
    }

    // ! Please only use in ConstructionSiteController
    // If cellSuccessorLoaded == false, please maintain other cells afterwards as soon as loaded
    public ConstructionSite( @NonNull Integer length, @NonNull Cell tailCell, boolean cellSuccessorLoaded ) {
        this.length = length;

        if( cellSuccessorLoaded ) {
            setTailCell( tailCell, length );
        }
        else {
            // Add to blockedCells so that getTailCell() is working
            blockedCells.addFirst( tailCell );
        }
    }

    // todo unify in AttachedToCell
    private void setTailCell( @NonNull Cell tailCell, int length ) {

        // Block new cells
        Cell currentCell = tailCell;
        for( int i = 0 /* First cell already set */; i < length; i++ ) {

            if( currentCell == null ) {
                throw new ObjectMisplacedException( this, "Reaches the end of street" );
            }

            if( currentCell.isBlocked() ) {
                throw new ObjectMisplacedException( this, "Blocked by " + currentCell.getBlockingObject() );
            }

            blockedCells.addFirst( currentCell ); // Habe ich über die Prüfung gezogen, damit auch die tailCell dem Vehicle hinzugefügt wird. ;)
            currentCell.setBlockingConstructionSite( this );
            currentCell = currentCell.getSuccessor(); // Hab ich nach untern gezogen, damit auch die erste Zelle hinzugefügt wird.
        }
    }

    public Cell getTailCell() {
        return blockedCells.getLast();
    }

    private void setBlockedCells() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer getLength() {
        return length;
    }
}
