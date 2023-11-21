package agh.ics.oop.model;

import agh.ics.oop.exceptions.PositionNotAvailableException;
import agh.ics.oop.exceptions.PositionOutOfBoundsException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrassField extends AbstractWorldMap {
    
    private final int grassNumber;
    private final Boundary mapLimits;
    private final Map<Vector2D, Grass> grassMap;

    /* currentBounds ->
    * visualisation purpose bounds dynamically updated to
    * cover all elements placed on the map. */
    private Boundary currentBounds;


    public GrassField(int grassNumber) {

        super(0);

        this.grassNumber = grassNumber;
        this.mapLimits = new Boundary(new Vector2D(Integer.MIN_VALUE, Integer.MIN_VALUE), new Vector2D(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.currentBounds = new Boundary(mapLimits.rightUpperCorner(), mapLimits.leftLowerCorner());

        grassMap = new HashMap<>(grassNumber);

        this.randomlyPlaceGrass(getGrassBounds());
    }

    private int getGrassBounds() {

        /* (0, 0) - (sqrt(n*10), sqrt(n*10)) -> I assume inclusive range edges. */
        return (int) Math.sqrt(10 * grassNumber) + 1;
    }

    private void randomlyPlaceGrass(int bounds) {

        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(bounds, bounds, grassNumber);
        for(Vector2D grassPosition : randomPositionGenerator) {
            grassMap.put(grassPosition, new Grass(grassPosition));
            this.updateGrassField(grassPosition);
        }
    }

    private void updateVisibleCorners(Vector2D position) {
        this.currentBounds = new Boundary(
                position.lowerLeft(this.currentBounds.leftLowerCorner()),
                position.upperRight(this.currentBounds.rightUpperCorner()));
    }

    @Override
    public void place(WorldElement worldElement) throws PositionNotAvailableException {

        if (worldElement instanceof Animal animal) {
            super.place(animal);
            this.updateGrassField(animal.getPosition());
            super.mapChanged("Animal placed at " + animal.getPosition() + ".");
        }

        else if (worldElement instanceof Grass grass) {
            this.placeGrass(grass);
            super.mapChanged("Grass placed at " + grass.getPosition() + ".");
        }

    }

    public void placeGrass(Grass grass) throws PositionNotAvailableException {
        if (this.canPlaceGrass(grass.getPosition())) {
            grassMap.put(grass.getPosition(), grass);
            this.updateGrassField(grass.getPosition());
        }
        else {
            throw new PositionNotAvailableException(grass.getPosition());
        }
    }

    private boolean canPlaceGrass(Vector2D newPosition) {
        return inBounds(newPosition) && !this.isOccupied(newPosition);
    }

    private void updateGrassField(Vector2D position) {
        this.updateVisibleCorners(position);
    }

    @Override
    public void move(WorldElement element, MoveDirection direction) throws PositionNotAvailableException {
        Vector2D oldPosition = element.getPosition();
        String oldOrientation = element.toString();

        super.move(element, direction);

        Animal animal = (Animal) element;

        this.updateGrassField(animal.getPosition());

        super.mapChanged("Animal moved from " + oldPosition + " " + animal + " to " + animal.getPosition() + " " + oldOrientation);
    }

    @Override
    public boolean canMoveTo(Vector2D newPosition) {
        return inBounds(newPosition) && !this.isOccupiedByAnimal(newPosition);
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
    public List<WorldElement> getElements() {
        List<WorldElement> elements = super.getElements();
        elements.addAll(grassMap.values());
        return elements;
    }

    @Override
    public Boundary getCurrentBounds() {
        return currentBounds;
    }

    public boolean inBounds(Vector2D newPosition) {
        return newPosition.follows(this.mapLimits.leftLowerCorner()) && newPosition.precedes(this.mapLimits.rightUpperCorner());
    }

}
