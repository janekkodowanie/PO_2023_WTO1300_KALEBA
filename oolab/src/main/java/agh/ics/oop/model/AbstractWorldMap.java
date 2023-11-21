package agh.ics.oop.model;

import agh.ics.oop.MapVisualizer;
import agh.ics.oop.annotations.Observable;
import agh.ics.oop.exceptions.PositionNotAvailableException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Observable
public abstract class AbstractWorldMap implements WorldMap<WorldElement, Vector2D> {

    protected final Map<Vector2D, Animal> animalMap;
    protected final MapVisualizer visualizer;
    private List<MapChangeListener> observers;

    protected AbstractWorldMap(int initialCapacity) {
        animalMap = new HashMap<>(initialCapacity);

        visualizer = new MapVisualizer(this);
        observers = new ArrayList<>();
    }

    @Override
    public WorldElement objectAt(Vector2D position) {
        return animalMap.get(position);
    }

    protected abstract boolean inBounds(Vector2D newPosition);

    @Override
    public boolean canMoveTo(Vector2D newPosition) {
        return inBounds(newPosition) && !this.isOccupied(newPosition);
    }

    @Override
    public void place(WorldElement worldElement) throws PositionNotAvailableException {
        if (worldElement instanceof Animal animal) {
            Vector2D position = animal.getPosition();
            if (canMoveTo(position)) {
                animalMap.put(position, animal);
            }
            else {
                throw new PositionNotAvailableException(position);
            }
        }
    }

    @Override
    public void move(WorldElement element, MoveDirection direction) throws PositionNotAvailableException {

        if (element instanceof Animal animal) {
            Vector2D oldPosition = animal.getPosition();

            if (animal.move(this, direction)) {
                this.animalMap.remove(oldPosition);
                this.animalMap.put(animal.getPosition(), animal);
            }
            else {
                throw new PositionNotAvailableException(animal.getPosition());
            }
        }
        else {
            throw new IllegalArgumentException("Only animals can move on the map!");
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

    public void unregisterObserver(MapChangeListener observer) {
        observers.remove(observer);
    }

    protected void mapChanged(String message) {
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this, message);
        }
    }

}
