package agh.ics.oop.model;

import agh.ics.oop.exceptions.PositionAlreadyOccupiedException;
import agh.ics.oop.exceptions.PositionOutOfBoundsException;

import java.util.Collections;
import java.util.Map;

public class RectangularMap extends AbstractWorldMap {

    public RectangularMap(int width, int height) {
        super(new Vector2D(0, 0),
                new Vector2D(width - 1, height - 1), width * height);
    }

    Map<Vector2D, Animal> getAnimalMap() {
        return Collections.unmodifiableMap(animalMap);
    }

    @Override
    public void place(WorldElement worldElement) {
        try {
            if (worldElement instanceof Animal animal) {
                Vector2D position = animal.getPosition();
                if (super.canMoveTo(position)) {
                    animalMap.put(position, animal);

                    mapChanged("Animal placed at " + position + ".");
                }
            }
        }
        catch (PositionAlreadyOccupiedException | PositionOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void move(WorldElement element, MoveDirection direction) {

        try {
            Animal animal = (Animal) element;
            Vector2D oldPosition = animal.getPosition();

            if (animal.move(this, direction)) {
                this.animalMap.remove(oldPosition);
                this.animalMap.put(animal.getPosition(), animal);
            }

            super.mapChanged("Animal moved from " + oldPosition + " " + animal + " to " + animal.getPosition());
        }
        catch (PositionAlreadyOccupiedException | PositionOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Boundary getCurrentBounds() {
        return super.mapLimits;
    }
}
