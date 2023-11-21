package agh.ics.oop.exceptions;

import agh.ics.oop.model.Vector2D;

public class PositionNotAvailableException extends Exception {

    public PositionNotAvailableException(Vector2D position) {
        super("Position " + position.toString() + " is already occupied.");
    }

}
