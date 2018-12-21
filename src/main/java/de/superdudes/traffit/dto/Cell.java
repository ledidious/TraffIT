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

    public Cell getAncestor() {
        return index > 0 ? lane.getCell(index - 1) : null;
    }

    public Cell getSuccessor() {
        return lane.getLength() > index + 1 ? lane.getCell(index + 1) : null;
    }

    // Provides both ConstructionSite and Vehicle for general purposes
    public Object getBlockingObject() {
        return blockingVehicle != null ? blockingVehicle : blockingConstructionSite;
    }

    public boolean isBlocked() {
        return blockingVehicle != null || blockingConstructionSite != null;
    }

    public Cell getLeftNeighbour() {

        if (lane.getIndex() <= 0) {
            return null;
        }

        final Lane leftLane = lane.getStreet().getLanes().get(lane.getIndex() - 1);
        return leftLane.getCells()[index];
    }

    public Cell getRightNeighbour() {

        if (lane.getIndex() >= lane.getStreet().getLaneCount() - 1) {
            return null;
        }

        final Lane rightLane = lane.getStreet().getLanes().get(lane.getIndex() + 1);
        return rightLane.getCells()[index];
    }

    // toask unify with setBlockingConstructionSite ?
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

    // toask unify with setBlockingVehicle?
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
