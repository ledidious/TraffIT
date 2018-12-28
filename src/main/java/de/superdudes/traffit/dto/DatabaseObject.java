package de.superdudes.traffit.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id"})

public abstract class DatabaseObject implements Cloneable {

	protected Integer id;

	public DatabaseObject() {
		this(null);
	}

	public DatabaseObject(Integer id) {
		this.id = id;
	}

	@Override
	public DatabaseObject clone() {
		try {
			return (DatabaseObject) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError("Cannot happen");
		}
	}
}
