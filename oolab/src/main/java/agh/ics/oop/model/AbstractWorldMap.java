package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractWorldMap implements WorldMap<WorldElement, Vector2D> {

    /* leftLowerCorner & rightUpperCorner -> map bounds.*/
    protected final Vector2D leftLowerCorner;
    protected final Vector2D rightUpperCorner;
    protected final Map<Vector2D, Animal> animalMap;


    protected AbstractWorldMap(Vector2D leftLowerCorner, Vector2D rightUpperCorner, int initialCapacity) {
        this.leftLowerCorner = leftLowerCorner;
        this.rightUpperCorner = rightUpperCorner;

        animalMap = new HashMap<>(initialCapacity);
    }

    @Override
    public boolean place(WorldElement worldElement) {

        if (worldElement instanceof Animal animal) {
            Vector2D position = animal.getPosition();
            if (this.canMoveTo(position)) {
                animalMap.put(position, animal);

                return true;
            }
        }

        return false;
    }

    @Override
    public WorldElement objectAt(Vector2D position) {
        return animalMap.get(position);
    }

    @Override
    public boolean canMoveTo(Vector2D newPosition) {
        return newPosition.follows(leftLowerCorner)
                && newPosition.precedes(rightUpperCorner)
                && !this.isOccupied(newPosition);
    }

    @Override
    public boolean move(WorldElement element, MoveDirection direction) {

        if (!(element instanceof Animal animal)) {
            throw new IllegalArgumentException("Only animals can be moved on the map.");
        }

        Vector2D oldPosition = animal.getPosition();

        if (animal.move(this, direction)) {
            this.animalMap.remove(oldPosition);
            this.animalMap.put(animal.getPosition(), animal);
            return true;
        }

        return false;
    }

    @Override
    public boolean isOccupied(Vector2D position) {
        return animalMap.containsKey(position);
    }

    @Override
    public Vector2D getLeftLowerCorner() {
        return leftLowerCorner;
    }

    @Override
    public Vector2D getRightUpperCorner() {
        return rightUpperCorner;
    }

    @Override
    public List<WorldElement> getElements() {
        return new ArrayList<>(animalMap.values());
    }

}
