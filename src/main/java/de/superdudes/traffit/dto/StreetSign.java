package de.superdudes.traffit.dto;

import de.superdudes.traffit.exception.ObjectMisplacedException;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of = {"speedLimit"})
public class StreetSign extends SimulationObject implements AttachedToCell {

    public static final int DEFAULT_SPEED = 100;

    @NonNull
    private Integer speedLimit;

    @NonNull
    private Cell tailCell;

    @NonNull
    private Integer length;

    public StreetSign(@NonNull Integer speedLimit, @NonNull Cell tailCell, @NonNull Integer length) {
        super();

        this.speedLimit = speedLimit;
        this.length = length;

        // Link on other side
        tailCell.setStreetSign(this);

        setTailCell(tailCell);
    }

    /**
     * to do unify in AttachedToCell
     * 
     * @param tailCell set  first cell of  the StreetSign guilty 
     * @param length  length of the street sign guilty to block the currentCell
     */
    public void setTailCell(@NonNull Cell tailCell, int length) {

        // Block new cells
        Cell currentCell = tailCell;
        for (int i = 0 /* First cell already set */; i < length; i++) {

            if (currentCell == null) {
                throw new ObjectMisplacedException(this, "Reaches the end of street");
            }

            if (currentCell.isBlocked()) {
                throw new ObjectMisplacedException(this, "Blocked by " + currentCell.getBlockingObject());
            }

            currentCell.setStreetSign(this);
            currentCell = currentCell.getSuccessor(); 
        }
    }

    // Not invokable -> private
	private void setLength(@NonNull Integer length) {
        this.length = length;
    }

    @Override
    public Integer getLength() {
        return length;
    }
}
