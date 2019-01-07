package de.superdudes.traffit.controller;

import de.superdudes.traffit.DbManager;
import de.superdudes.traffit.dto.AttachedToCell;
import de.superdudes.traffit.dto.Cell;
import de.superdudes.traffit.dto.SimulationObject;
import lombok.Getter;
import lombok.NonNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Function;

public abstract class AbstractController<T extends SimulationObject> {

	static final int MAX_CELLS_TO_SEARCH__INFINITE = -1;

	/**
	 * Assuming that the first cell is the id cell
	 *
	 * @throws SQLException
	 */
	protected void insertOrUpdate(String sql, T object) throws SQLException {

		final Connection connection = DbManager.instance().getConnection();
		final Statement statement = connection.createStatement();

		statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

		final ResultSet resultSet = statement.getGeneratedKeys();
		while (resultSet.next()) {
			object.setId(resultSet.getInt(1));
		}
	}

	public void render(@NonNull T object) {
		// Nothing to do except if overridden
	}

	/**
	 * The central algorithm to find objects by moving from cell to cell
	 *
	 * @param fromCell
	 * @param backwards
	 * @param maxCellsToSearch
	 * @return entry consisting of distance and vehicle or {@code null} if no
	 * vehicle can be found until end of street
	 */
	protected <O extends AttachedToCell> DistanceTo<O> find(@NonNull Cell fromCell, @NonNull Function<Cell, O> accessor, boolean backwards, int maxCellsToSearch) {

		Cell currentCell = fromCell;
		O wantedObject = null;

		int distance = 0;
		do {
			distance++;
		} while ((maxCellsToSearch < 0 || distance < maxCellsToSearch)
				&& (currentCell = (backwards ? currentCell.getAncestor() : currentCell.getSuccessor())) != null
				&& (wantedObject = accessor.apply(currentCell)) == null);

		return wantedObject != null ? new DistanceTo<>(distance, wantedObject) : null;
	}

	@Getter
	class DistanceTo<O extends AttachedToCell> {

		private final int distance;
		private final O other;

		public DistanceTo(int distance, O other) {
			this.distance = distance;
			this.other = other;
		}
	}
}
