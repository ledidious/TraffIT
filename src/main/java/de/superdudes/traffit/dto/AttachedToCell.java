package de.superdudes.traffit.dto;

import de.superdudes.traffit.dto.Cell;
import lombok.NonNull;

import java.util.function.BiConsumer;

public interface AttachedToCell {

    @NonNull
    Integer getLength();

    @NonNull
    Cell getTailCell();

    default <T extends AttachedToCell> void connectCells(Cell tailCell, BiConsumer<Cell, T> connector) {

        // Already connected
        Cell currentCell = tailCell.getSuccessor();

        for (int i = 0 /* First cell already connected */; i < getLength(); i++) {
            connector.accept(currentCell, (T) this);
        }
    }
}
