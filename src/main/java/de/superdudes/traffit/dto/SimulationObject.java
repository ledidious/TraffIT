package de.superdudes.traffit.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode( callSuper = true, of = "nr" )
@ToString( of = { "nr" } )
public class SimulationObject extends DatabaseObject {

    private static int nrCounter = 0;

    // todo not in use, was a previous idea never used. Can be removed
    @Deprecated
    @NonNull
    private Integer nr;

    public SimulationObject() {
        this( null );
    }

    public SimulationObject( Integer id ) {
        super( id );

        this.nr = nrCounter;

        nrCounter++;
    }
}
