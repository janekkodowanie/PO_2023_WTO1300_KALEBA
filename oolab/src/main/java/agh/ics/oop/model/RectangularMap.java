package agh.ics.oop.model;

import agh.ics.oop.exceptions.PositionNotAvailableException;

import java.util.Collections;
import java.util.Map;

public class RectangularMap extends AbstractWorldMap {

    private final Boundary boundary;

    public RectangularMap(int width, int height) {
        super(width * height);
        this.boundary = new Boundary(new Vector2D(0, 0), new Vector2D(width - 1, height - 1));
    }

    Map<Vector2D, Animal> getAnimalMap() {
        return Collections.unmodifiableMap(animalMap);
    }

    @Override
    public void place(WorldElement worldElement) throws PositionNotAvailableException {
        super.place(worldElement);
        mapChanged("Animal placed at " + worldElement.getPosition() + ".");
    }

    @Override
    protected boolean inBounds(Vector2D newPosition) {
        return newPosition.follows(this.boundary.leftLowerCorner()) && newPosition.precedes(this.boundary.rightUpperCorner());
    }

    @Override
    public void move(WorldElement element, MoveDirection direction) throws PositionNotAvailableException {
        Vector2D oldPosition = element.getPosition();
        String oldOrientation = element.toString();

        super.move(element, direction);
        Animal animal = (Animal) element;

        super.mapChanged("Animal moved from " + oldPosition + " " + animal + " to " + animal.getPosition() + " " + oldOrientation);
    }

    @Override
    public Boundary getCurrentBounds() {
        return this.boundary;
    }
}
