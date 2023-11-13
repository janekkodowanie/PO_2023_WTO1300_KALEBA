package agh.ics.oop.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

public class GrassField extends AbstractWorldMap {
    
    private final int N;
    private final Map<Vector2D, Grass> grassMap;

    private Vector2D dynamicLeftLowerCorner;
    private Vector2D dynamicRightUpperCorner;


    public GrassField(int numberOfGrass) {

        super(Integer.MAX_VALUE, Integer.MAX_VALUE, 0);

        this.N = numberOfGrass;
        this.dynamicRightUpperCorner = super.getLeftLowerCorner();
        this.dynamicLeftLowerCorner = super.getRightUpperCorner();

        grassMap = new HashMap<>(numberOfGrass);

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
            Grass grass = Stream.generate(() -> new Vector2D(random.nextInt(bound), random.nextInt(bound)))
                    .filter(position -> !this.isOccupied(position))
                    .findFirst()
                    .map(Grass::new)
                    .orElseThrow();

            place(grass);
        }
    }

    @Override
    public boolean place(WorldElement object) {

        if (super.place(object)) {
            /* If no such key in grassMap found, grass.remove(...) returns null -> no exception. */
            grassMap.remove(object.getPosition());
            updateVisibleCorners(object.getPosition());
            return true;
        }

        return false;
    }

    @Override
    public boolean isOccupied(Vector2D position) {
        return super.isOccupied(position) || grassMap.containsKey(position);
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
