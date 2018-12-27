package de.superdudes.traffit.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of = {"index"})
public class Cell extends SimulationObject {

    @NonNull
    private Integer index;

    @NonNull
    private Lane lane;

    private Vehicle blockingVehicle;
    private ConstructionSite blockingConstructionSite;

    private StreetSign streetSign;

    public Cell(@NonNull Integer index, @NonNull Lane lane) {
        super();

        this.index = index;
        this.lane = lane;
    }

    /**   
     * @return ancestor Cell of an lane by his index or null
     */
    public Cell getAncestor() {
        return index > 0 ? lane.getCell(index - 1) : null;
    }

    /**
     * @return successor Cell of an lane by his index or null
     */
    public Cell getSuccessor() {
        return lane.getLength() > index + 1 ? lane.getCell(index + 1) : null;
    }

    /**
     * provides both ConstructionSite and Vehicle for general purposes
     * 
     * @return blockingVehicle || blockingConstructionsite
     */
    public Object getBlockingObject() {
        return blockingVehicle != null ? blockingVehicle : blockingConstructionSite;
    }

    /**
     * if a Vehicle or Construction-Site blocked a Cell
     * 
     * @return true
     */
    public boolean isBlocked() {
        return blockingVehicle != null || blockingConstructionSite != null;
    }

    /**
     *  @return Cell at a certainly Position in the Array of Cells, which belongs to the left Lane!
     */
    public Cell getLeftNeighbour() {

        if (index <= 0) {
            return null;
        }

        final Lane leftLane = lane.getStreet().getLanes().get(lane.getIndex() - 1);
        return leftLane.getCells()[index];
    }

    /** 
     * @return Cell at a certainly Position in the Array of Cells, which belongs to the right Lane!
     */
    public Cell getRightNeighbour() {

        if (index >= lane.getStreet().getLaneCount()) {
            return null;
        }

        final Lane rightLane = lane.getStreet().getLanes().get(lane.getIndex() + 1);
        return rightLane.getCells()[index];
    }

    /**
     *  to ask unify with setBlockingConstructionSite ?
     *  
     *  @param vehicle
     */
    public void setBlockingVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            blockingVehicle = null;
        } else {
            if (blockingConstructionSite != null) {
                throw new IllegalStateException("Already blocked");
            }
            if (blockingVehicle != null && !blockingVehicle.equals(vehicle)) {
                throw new IllegalStateException("Already blocked");
            }
            blockingVehicle = vehicle;
        }

        // To add to startingGrid
        if (vehicle != null) {
            getLane().getStreet().getStartingGrid().addVehicle(vehicle);
        }

        // Cannot be set both
        blockingConstructionSite = null;
    }

    /**
     *  to ask unify with setBlockingVehicle?
     *  
     *  @param constructionSite
     */
    public void setBlockingConstructionSite(ConstructionSite constructionSite) {
        if (constructionSite == null) {
            blockingConstructionSite = null;
        } else {
            if (blockingVehicle != null) {
                throw new IllegalStateException("Already blocked");
            }
            if (blockingConstructionSite != null && !blockingConstructionSite.equals(constructionSite)) {
                throw new IllegalStateException("Already blocked");
            }
            blockingConstructionSite = constructionSite;
        }

        // Cannot be set both
        blockingVehicle = null;
    }
}
