package agh.ics.oop.model;

import agh.ics.oop.exceptions.PositionAlreadyOccupiedException;
import agh.ics.oop.exceptions.PositionOutOfBoundsException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrassField extends AbstractWorldMap {
    
    private final int grassNumber;
    private final Map<Vector2D, Grass> grassMap;

    /* currentBounds ->
    * visualisation purpose bounds dynamically updated to
    * cover all elements placed on the map. */
    private Boundary currentBounds;


    public GrassField(int grassNumber) {

        super(new Vector2D(Integer.MIN_VALUE, Integer.MIN_VALUE),
                new Vector2D(Integer.MAX_VALUE, Integer.MAX_VALUE), 0);

        this.grassNumber = grassNumber;
        this.currentBounds = new Boundary(super.mapLimits.rightUpperCorner(), super.mapLimits.leftLowerCorner());

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
    public void place(WorldElement object) {

        if (object instanceof Animal animal) {
            try {
                this.placeAnimal(animal);
                super.mapChanged("Animal placed at " + animal.getPosition() + ".");

            } catch (PositionAlreadyOccupiedException | PositionOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }
        }

        else if (object instanceof Grass grass) {
            try {
                this.placeGrass(grass);
                super.mapChanged("Grass placed at " + grass.getPosition() + ".");

            } catch (PositionAlreadyOccupiedException | PositionOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private boolean canPlaceGrass(Vector2D newPosition) throws PositionOutOfBoundsException, PositionAlreadyOccupiedException {

        if (!super.inBounds(newPosition))
            throw new PositionOutOfBoundsException(newPosition);

        if (this.isOccupied(newPosition))
            throw new PositionAlreadyOccupiedException(newPosition);

        return true;
    }

    public void placeAnimal(Animal animal) throws PositionAlreadyOccupiedException, PositionOutOfBoundsException {
        if (this.canMoveTo(animal.getPosition())) {
            animalMap.put(animal.getPosition(), animal);
            grassMap.remove(animal.getPosition());
            this.updateGrassField(animal.getPosition());
        }
    }

    public void placeGrass(Grass grass) throws PositionAlreadyOccupiedException, PositionOutOfBoundsException {
        if (this.canPlaceGrass(grass.getPosition())) {
            grassMap.put(grass.getPosition(), grass);
            this.updateGrassField(grass.getPosition());
        }
    }

    private void updateGrassField(Vector2D position) {
        this.updateVisibleCorners(position);
    }
    private void removeGrass(Vector2D position) {
        grassMap.remove(position);
    }


    @Override
    public void move(WorldElement element, MoveDirection direction) {
        /* After super.move() element.getPosition() returns it's new Position. */

        if (element instanceof Animal animal) {
            try {
                Vector2D oldPosition = animal.getPosition();

                if (animal.move(this, direction)) {
                    this.animalMap.remove(oldPosition);
                    this.animalMap.put(animal.getPosition(), animal);
                    this.removeGrass(animal.getPosition());
                    this.updateGrassField(animal.getPosition());
                    super.mapChanged("Animal moved from " + oldPosition + " " + animal + " to " + animal.getPosition());
                }

            } catch (PositionAlreadyOccupiedException | PositionOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }
        }
        else {
            throw new IllegalArgumentException("Only animals can move on the map!");
        }
    }

    @Override
    public boolean canMoveTo(Vector2D newPosition) throws PositionAlreadyOccupiedException {

        if (this.isOccupiedByAnimal(newPosition))
            throw new PositionAlreadyOccupiedException(newPosition);

        return super.inBounds(newPosition);
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

}
