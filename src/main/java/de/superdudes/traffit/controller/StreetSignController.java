package de.superdudes.traffit.controller;

import de.superdudes.traffit.DbManager;
import de.superdudes.traffit.dto.Cell;
import de.superdudes.traffit.dto.StreetSign;
import de.superdudes.traffit.exception.ObjectNotPersistedException;
import lombok.NonNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StreetSignController extends AbstractController<StreetSign> {

    private static class Singletons {

        private static final StreetSignController INSTANCE = new StreetSignController();
    }

    public static StreetSignController instance() {
        return Singletons.INSTANCE;
    }

    void save(@NonNull StreetSign object) {

        if (object.getTailCell().getId() == null) {
            throw new ObjectNotPersistedException(object.getTailCell());
        }

        try {
            // language=sql
            final String sql;

            if (object.getId() != null) {
                sql = "UPDATE STREET_SIGN SET ss_id = " + object.getId() + ", nr = " + object.getNr() + ", speedLimit = "
                        + object.getSpeedLimit() + ", tailCell_id = " + object.getTailCell().getId()
                        + ", length = " + object.getLength();
            } else {
                sql = "INSERT INTO STREET_SIGN (nr, speedLimit, tailCell_id, length) VALUES (" + object.getTailCell()
                        + ", " + object.getSpeedLimit() + ", " + object.getTailCell().getId()
                        + ", " + object.getLength() + ")";
            }

            insertOrUpdate(sql, object);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Eintragen der Daten fehlgeschlagen!!!");
        }
    }

    StreetSign load(@NonNull Cell tailCell) {

        if (tailCell.getId() == null) {
            throw new ObjectNotPersistedException(tailCell);
        }

        try {
            final Connection connection = DbManager.instance().getConnection();
            final ResultSet resultSet = connection.createStatement().executeQuery(
                    "SELECT * FROM STREET_SIGN WHERE tailCell_id = '" + tailCell.getId() + "'"
            );

            while (resultSet.next()) {
                final Integer id = resultSet.getInt("ss_id");
                final Integer nr = resultSet.getInt("nr");
                final Integer speedLimit = resultSet.getInt("streetLimit");
                final Integer length = resultSet.getInt("length");


                final StreetSign object = new StreetSign(speedLimit, tailCell, length);
                object.setId(id);
                object.setNr(nr);

                tailCell.setStreetSign(object);

                return object;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.print("Laden der Daten nicht m�glich!!!");
        }
        return null;
    }
}
