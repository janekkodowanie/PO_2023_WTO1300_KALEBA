package agh.ics.oop.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RectangularMap implements WorldMap<Animal, Vector2D> {

    private final Map<Vector2D, Animal> animalMap;

    private final Vector2D leftLowerCorner;
    private final Vector2D rightUpperCorner;


    public RectangularMap(int width, int height) {
        this.animalMap = new HashMap<>(width * height);
        leftLowerCorner = new Vector2D(0,0);
        rightUpperCorner = new Vector2D(width - 1, height - 1);
    }

    @Override
    public boolean place(Animal animal) {

        if (this.canMoveTo(animal.getPosition())) {
            animalMap.put(animal.getPosition(), animal);
            return true;
        }

        return false;
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {

        Vector2D oldPosition = animal.getPosition();

        boolean moved = animal.move(this, direction);

        if (moved) {
            this.animalMap.remove(oldPosition);
            this.animalMap.put(animal.getPosition(), animal);
        }
    }

    @Override
    public boolean isOccupied(Vector2D position) {
        return animalMap.containsKey(position);
    }

    @Override
    public Animal objectAt(Vector2D position) {
        return animalMap.get(position);
    }

    @Override
    public boolean canMoveTo(Vector2D newPosition) {
        return newPosition.follows(leftLowerCorner)
                && newPosition.precedes(rightUpperCorner)
                && !isOccupied(newPosition);
    }

    Map<Vector2D, Animal> getAnimalMap() {
        return Collections.unmodifiableMap(animalMap);
    }

    public Vector2D getLeftLowerCorner() {
        return leftLowerCorner;
    }

    public Vector2D getRightUpperCorner() {
        return rightUpperCorner;
    }
}
