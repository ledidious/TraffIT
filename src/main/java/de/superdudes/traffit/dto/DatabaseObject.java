package de.superdudes.traffit.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id"})
public abstract class DatabaseObject implements Serializable {

	protected Integer id;

	public DatabaseObject() {
		this(null);
	}

	public DatabaseObject(Integer id) {
		this.id = id;
	}
}
