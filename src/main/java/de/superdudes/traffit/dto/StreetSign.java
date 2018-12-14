package de.superdudes.traffit.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString( of = { "nr", "speedLimit" } )
public class StreetSign extends SimulationObject {

    public static final int DEFAULT_SPEED = 100;

    @NonNull
    private Integer speedLimit;
    
    @NonNull
    private Cell cell;

    public StreetSign( @NonNull Integer speedLimit, @NonNull Cell cell ) {
        super();

        this.speedLimit = speedLimit;
        this.cell = cell;
        
        // Link on other side
        cell.setStreetSign(this);
    }
}

