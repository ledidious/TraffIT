package de.superdudes.traffit.controller;

import de.superdudes.traffit.DbManager;
import de.superdudes.traffit.dto.Cell;
import de.superdudes.traffit.dto.ConstructionSite;
import de.superdudes.traffit.exception.ObjectNotPersistedException;
import lombok.NonNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConstructionSiteController extends AbstractController<ConstructionSite> {

    private static class Singletons {

        private static final ConstructionSiteController INSTANCE = new ConstructionSiteController();
    }

    public static ConstructionSiteController instance() {
        return Singletons.INSTANCE;
    }

    void save(@NonNull ConstructionSite object) {

        try {
            // language=sql
            final String sql;

            if (object.getId() != null) {
                sql = "UPDATE CONSTRUCTION_SITE SET cs_id = '" + object.getId() + "', nr = '" + object.getNr() + "', " +
                        "length = '" + object.getLength() + "', tailCell_id = '" + object.getTailCell().getId() + "'";
            } else {
                sql = "INSERT INTO CONSTRUCTION_SITE (nr, length, tailCell_id) VALUES ('" + object.getNr() + "', '"
                        + object.getLength() + "', '" + object.getTailCell().getId() + "')";
            }

            insertOrUpdate(sql, object);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Eintragen der Daten fehlgeschlagen!!!");
        }
    }

    ConstructionSite load(@NonNull Cell tailCell) {

        if (tailCell.getId() == null) {
            throw new ObjectNotPersistedException(tailCell);
        }

        try {
            final Connection connection = DbManager.instance().getConnection();
            final ResultSet resultSet = connection.createStatement().executeQuery(
                    "SELECT * FROM CONSTRUCTION_SITE WHERE tailCell_id = '" + tailCell.getId() + "' LIMIT 1"
            );

            while (resultSet.next()) {
                final Integer id = resultSet.getInt("cs_id");
                final Integer nr = resultSet.getInt("nr");
                final Integer length = resultSet.getInt("length");

                final ConstructionSite object = new ConstructionSite(length, tailCell, false);

                object.setId(id);
                object.setNr(nr);
                object.setLength(length);

                // Connect cell on other side
                tailCell.setBlockingConstructionSite(object);

                return object;
            }
        } catch (SQLException ex) {

            ex.printStackTrace();
            System.out.print("Laden der Daten nicht m�glich!!!");
        }
        return null;
    }
}
