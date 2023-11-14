package agh.ics.oop.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        this.placeGrass(getGrassBounds());
    }

    private int getGrassBounds() {

        /* (0, 0) - (sqrt(n*10), sqrt(n*10)) -> I assume inclusive range edges. */
        return (int) Math.sqrt(10 * N) + 1;
    }

    private void updateVisibleCorners(Vector2D position) {

        this.dynamicLeftLowerCorner = new Vector2D(
                Math.min(this.dynamicLeftLowerCorner.getX(), position.getX()),
                Math.min(this.dynamicLeftLowerCorner.getY(), position.getY()));

        this.dynamicRightUpperCorner = new Vector2D(
                Math.max(this.dynamicRightUpperCorner.getX(), position.getX()),
                Math.max(this.dynamicRightUpperCorner.getY(), position.getY()));
    }

    private void placeGrass(int bounds) {

        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(bounds, bounds, N);
        for(Vector2D grassPosition : randomPositionGenerator) {
            grassMap.put(grassPosition, new Grass(grassPosition));
            updateGrassField(grassPosition);
        }
    }

    @Override
    public boolean place(WorldElement object) {

        if (super.place(object)) {
            /* If no such key in grassMap found, grass.remove(...) returns null -> no exception. */
            grassMap.remove(object.getPosition());
            return updateGrassField(object.getPosition());
        }
        else if (object instanceof Grass grass && !isOccupied(grass.getPosition())) {
            grassMap.put(grass.getPosition(), grass);
            return updateGrassField(grass.getPosition());
        }

        return false;
    }

    private boolean updateGrassField(Vector2D position) {
        updateVisibleCorners(position);
        return true;
    }
    private boolean removeGrass(Vector2D position) {
        grassMap.remove(position);
        return true;
    }

    @Override
    public boolean move(WorldElement element, MoveDirection direction) {
        /* After super.move() element.getPosition() returns it's new Position. */
        return super.move(element, direction)
                && this.updateGrassField(element.getPosition())
                && this.removeGrass(element.getPosition());
    }

    @Override
    public boolean canMoveTo(Vector2D newPosition) {
        return newPosition.follows(leftLowerCorner)
                && newPosition.precedes(rightUpperCorner)
                && !isOccupiedByAnimal(newPosition);
    }

    @Override
    public boolean isOccupied(Vector2D position) {
        return super.isOccupied(position) || grassMap.containsKey(position);
    }

    private boolean isOccupiedByAnimal(Vector2D position) {
        return super.isOccupied(position);
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

    @Override
    public List<WorldElement> getElements() {
        List<WorldElement> elements = super.getElements();
        elements.addAll(grassMap.values());
        return Collections.unmodifiableList(elements);
    }

}
