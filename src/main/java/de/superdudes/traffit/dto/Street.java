package de.superdudes.traffit.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString( of = { "length" } )
public class Street extends SimulationObject {

    @NonNull
    private Integer length;

    @NonNull
    private List<Lane> lanes = new ArrayList<>();

    @NonNull
    private StartingGrid startingGrid;

    public Street( @NonNull Integer length, int laneCount, @NonNull StartingGrid startingGrid ) {
        super();

        this.length = length;
        this.startingGrid = startingGrid;

        // On other side
        startingGrid.setStreet( this );

        // From left lane to right lane
        for( int i = 0; i < laneCount; i++ ) {
            lanes.add( new Lane( this, i ) );
        }
    }

    public int getLaneCount() {
        return lanes.size();
    }
}
