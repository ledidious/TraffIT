package de.superdudes.traffit.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString( of = { "nr" } )
public class Street extends SimulationObject {

    @NonNull
    private Integer length;

    @NonNull
    private List<Lane> lanes = new ArrayList<>();

    public Street( @NonNull Integer length, int laneCount ) {
        super();

        this.length = length;
        
        // From left lane to right lane
        for (int i = 0; i < laneCount; i++) {
			lanes.add(new Lane(this, i));
		}
    }
    
    public int getLaneCount() {
    	return lanes.size();
    }
}
