package agh.ics.oop.exceptions;

import agh.ics.oop.model.Vector2D;

public class PositionOutOfBoundsException extends Exception {

    public PositionOutOfBoundsException(Vector2D position) {
        super("Position " + position.toString() + " is out of bounds.");
    }

}
