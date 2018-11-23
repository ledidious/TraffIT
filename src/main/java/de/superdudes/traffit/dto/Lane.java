package de.superdudes.traffit.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString( of = { "nr" } )
public class Lane extends SimulationObject {

    @NonNull
    private Street street;

    @NonNull
    private Cell[] cells;

    @NonNull
    private Set<ConstructionSite> constructionSites = new HashSet<>();

    public Lane( @NonNull Street street ) {
        super();

        this.street = street;
        this.cells = new Cell[ street.getLength() ];
    }
}
