package de.superdudes.traffit.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode( of = { "id" } )
@ToString( of = { "id" } )
public abstract class DatabaseObject {

    protected Integer id;

    public DatabaseObject() {
        this( null );
    }

    public DatabaseObject( Integer id ) {
        this.id = id;
    }
}
