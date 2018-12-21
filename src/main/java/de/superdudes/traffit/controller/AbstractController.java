package de.superdudes.traffit.controller;

import de.superdudes.traffit.DbManager;
import de.superdudes.traffit.dto.SimulationObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractController<T extends SimulationObject> {

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

    public void render(T object) {
        // Nothing to do except if overridden
    }
}
