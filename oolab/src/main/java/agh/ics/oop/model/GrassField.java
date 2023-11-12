package agh.ics.oop.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

public class GrassField implements WorldMap<WorldElement, Vector2D> {
    
    private final int N;
    private final Map<Vector2D, Grass> grassMap;

    private final Map<Vector2D, Animal> animalMap;

    private final Vector2D leftLowerCornerBoundary;
    private final Vector2D rightUpperCornerBoundary;

    private Vector2D dynamicLeftLowerCorner;
    private Vector2D dynamicRightUpperCorner;


    public GrassField(int numberOfGrass) {
        grassMap = new HashMap<>(numberOfGrass);
        animalMap = new HashMap<>();

        this.N = numberOfGrass;
        this.leftLowerCornerBoundary = this.dynamicRightUpperCorner = new Vector2D(0,0);
        this.rightUpperCornerBoundary = this.dynamicLeftLowerCorner = new Vector2D(Integer.MAX_VALUE, Integer.MAX_VALUE);

        this.placeGrass();
    }

    private void updateVisibleCorners(Vector2D position) {

        this.dynamicLeftLowerCorner = new Vector2D(
                Math.min(this.dynamicLeftLowerCorner.getX(), position.getX()),
                Math.min(this.dynamicLeftLowerCorner.getY(), position.getY()));

        this.dynamicRightUpperCorner = new Vector2D(
                Math.max(this.dynamicRightUpperCorner.getX(), position.getX()),
                Math.max(this.dynamicRightUpperCorner.getY(), position.getY()));

    }

    public void placeGrass() {
        Random random = new Random();
        int bound = (int) Math.sqrt(10 * N);

        for (int i = 0; i < N; i++) {
            Stream.generate(() -> new Vector2D(random.nextInt(bound), random.nextInt(bound)))
                    .filter(position -> !this.isOccupied(position))
                    .findFirst()
                    .ifPresentOrElse(position -> {
                        grassMap.put(position, new Grass(position));
                        updateVisibleCorners(position);
                    }, () -> {
                        throw new IllegalArgumentException("Grass hasn't been placed.");
                    });
        }
    }

    public boolean place(Animal animal) {

        if (this.canMoveTo(animal.getPosition())) {
            animalMap.put(animal.getPosition(), animal);
            /* If no such key in grassMap found, grass.remove(...) returns null -> no exception. */
            grassMap.remove(animal.getPosition());
            updateVisibleCorners(animal.getPosition());

            return true;
        }

        return false;
    }

    @Override
    public boolean place(WorldElement object) {
        return object instanceof Animal && this.place((Animal) object);
    }

    @Override
    public void move(WorldElement object, MoveDirection direction) {
        if (object instanceof Animal) {
            this.move((Animal) object, direction);
        } else {
            throw new IllegalArgumentException("Only animals can move.");
        }
    }

    @Override
    public boolean canMoveTo(Vector2D newPosition) {
        return newPosition.follows(leftLowerCornerBoundary)
                && newPosition.precedes(rightUpperCornerBoundary)
                && !this.isOccupiedByAnimal(newPosition);
    }

    private void move(Animal animal, MoveDirection direction) {
        Vector2D oldPosition = animal.getPosition();

        boolean moved = animal.move(this, direction);

        if (moved) {
            this.animalMap.remove(oldPosition);
            this.animalMap.put(animal.getPosition(), animal);
            updateVisibleCorners(animal.getPosition());
        }
    }

    @Override
    public boolean isOccupied(Vector2D position) {
        return animalMap.containsKey(position) || grassMap.containsKey(position);
    }


    public boolean isOccupiedByAnimal(Vector2D position) {
        return animalMap.containsKey(position);
    }


    @Override
    public WorldElement objectAt(Vector2D position) {
        return animalMap.containsKey(position)
                ? animalMap.get(position) : grassMap.get(position);
    }

    @Override
    public Vector2D getLeftLowerCorner() {
        return this.dynamicLeftLowerCorner;
    }

    @Override
    public Vector2D getRightUpperCorner() {
        return this.dynamicRightUpperCorner;
    }
}
