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

    public StreetSign( @NonNull Integer speedLimit ) {
        super();

        this.speedLimit = speedLimit;
    }
}

