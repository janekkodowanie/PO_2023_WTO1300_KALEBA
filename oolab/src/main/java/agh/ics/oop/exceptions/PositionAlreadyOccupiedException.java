package agh.ics.oop.exceptions;

import agh.ics.oop.model.Vector2D;

public class PositionAlreadyOccupiedException extends Exception {

    public PositionAlreadyOccupiedException(Vector2D position) {
        super("Position " + position.toString() + " is already occupied.");
    }

}
