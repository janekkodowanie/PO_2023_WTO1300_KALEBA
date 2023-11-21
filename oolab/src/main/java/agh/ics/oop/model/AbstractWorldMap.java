package agh.ics.oop.model;

import agh.ics.oop.MapVisualizer;
import agh.ics.oop.annotations.Observable;
import agh.ics.oop.exceptions.PositionAlreadyOccupiedException;
import agh.ics.oop.exceptions.PositionOutOfBoundsException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Observable
public abstract class AbstractWorldMap implements WorldMap<WorldElement, Vector2D> {

    protected final Map<Vector2D, Animal> animalMap;

    /* Not exceedable map boundaries. */
    protected final Boundary mapLimits;
    protected final MapVisualizer visualizer;
    private List<MapChangeListener> observers;

    protected AbstractWorldMap(Vector2D leftLowerCorner, Vector2D rightUpperCorner, int initialCapacity) {
        this.mapLimits = new Boundary(leftLowerCorner, rightUpperCorner);
        animalMap = new HashMap<>(initialCapacity);

        visualizer = new MapVisualizer(this);
        observers = new ArrayList<>();
    }

    @Override
    public WorldElement objectAt(Vector2D position) {
        return animalMap.get(position);
    }

    protected boolean inBounds(Vector2D newPosition) {
        return newPosition.follows(this.mapLimits.leftLowerCorner()) && newPosition.precedes(this.mapLimits.rightUpperCorner());
    }

    @Override
    public boolean canMoveTo(Vector2D newPosition) throws PositionOutOfBoundsException, PositionAlreadyOccupiedException {
        if (!inBounds(newPosition))
            throw new PositionOutOfBoundsException(newPosition);

        if (this.isOccupied(newPosition))
            throw new PositionAlreadyOccupiedException(newPosition);

        return true;
    }

    @Override
    public void move(WorldElement element, MoveDirection direction) {

        try {
            if (element instanceof Animal animal) {
                Vector2D oldPosition = animal.getPosition();

                if (animal.move(this, direction)) {
                    this.animalMap.remove(oldPosition);
                    this.animalMap.put(animal.getPosition(), animal);
                }
                this.mapChanged("Animal moved from " + oldPosition + " " + animal + " to " + animal.getPosition());
            }
        }
        catch (PositionAlreadyOccupiedException | PositionOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean isOccupied(Vector2D position) {
        return animalMap.containsKey(position);
    }

    @Override
    public List<WorldElement> getElements() {
        return new ArrayList<>(animalMap.values());
    }

    public String toString() {
        return visualizer.draw(this.getCurrentBounds().leftLowerCorner(), this.getCurrentBounds().rightUpperCorner());
    }

    public abstract Boundary getCurrentBounds();

    public void registerObserver(MapChangeListener observer) {
        observers.add(observer);
    }

    public void removeObserver(MapChangeListener observer) {
        observers.remove(observer);
    }

    protected void mapChanged(String message) {
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this, message);
        }
    }
}
