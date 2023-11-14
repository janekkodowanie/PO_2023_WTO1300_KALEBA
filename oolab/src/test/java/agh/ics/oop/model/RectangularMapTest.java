package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class RectangularMapTest {


    @Test
    void canMoveTo() {

        /* Given */
        int rows = 1, cols = 2;
        RectangularMap map = new RectangularMap(rows, cols);

        Animal animal = new Animal(new Vector2D(0, 0));
        map.place(animal);

        /* When */
        var passingPositions = Arrays.stream(MapDirection.values())
                .map(mapDirection -> animal.getPosition().add(mapDirection.toUnitVector()))
                .filter(pos -> pos.follows(map.getLeftLowerCorner()) && pos.precedes(map.getRightUpperCorner()));

        var notPassingPositions = Arrays.stream(MapDirection.values())
                .map(mapDirection -> animal.getPosition().add(mapDirection.toUnitVector()))
                .filter(pos -> !pos.follows(map.getLeftLowerCorner()) || !pos.precedes(map.getRightUpperCorner()));

        /* Then */
        assertTrue(passingPositions.allMatch(map::canMoveTo));
        assertTrue(notPassingPositions.noneMatch(map::canMoveTo));
    }

    @Test
    void isOccupied() {

        /* Given */
        RectangularMap map = new RectangularMap(1, 2);
        Vector2D occupiedPosition = new Vector2D(0, 0);
        Vector2D freePosition = new Vector2D(0, 1);

        /* When */
        map.place(new Animal(occupiedPosition));

        /* Then */
        assertTrue(map.isOccupied(occupiedPosition));
        assertTrue(map.isOccupied(new Vector2D(0, 0)));
        assertFalse(map.isOccupied(freePosition));
        assertFalse(map.isOccupied(new Vector2D(0, 1)));
    }

    @Test
    void objectAt() {

        /* Given */
        RectangularMap map = new RectangularMap(1, 2);
        Vector2D occupiedPosition = new Vector2D(0, 0);
        Vector2D freePosition = new Vector2D(0, 1);

        Animal animal = new Animal(occupiedPosition);

        /* When */
        map.place(animal);

        /* Then */
        assert map.objectAt(occupiedPosition) == animal;
        assert map.objectAt(new Vector2D(0, 0)) == animal;
        assert map.objectAt(freePosition) == null;
        assert map.objectAt(new Vector2D(0, 1)) == null;
    }

    @Test
    void place() {

        /* Given */
        RectangularMap rectangularMap = new RectangularMap(1, 1);

        Vector2D position = new Vector2D(0, 0);
        Animal animal = new Animal(position);

        Vector2D positionDuplicate = new Vector2D(0, 0);
        Animal animalDuplicate = new Animal(positionDuplicate);

        /* When */
        rectangularMap.place(animal);

        rectangularMap.place(animalDuplicate);


        /* Then */
        assert rectangularMap.getAnimalMap().size() == 1;
        assert !rectangularMap.getAnimalMap().containsValue(animalDuplicate);

        assert rectangularMap.getAnimalMap().containsValue(animal);
        assert rectangularMap.getAnimalMap().containsKey(position);
    }

    @Test
    void move() {

        /* Given */
        RectangularMap map = new RectangularMap(3, 3);
        Vector2D centralPosition = new Vector2D(1, 1);
        Vector2D leftLowerCorner = new Vector2D(0, 0);

        Animal centralAnimal = new Animal(centralPosition);
        Animal leftLowerCornerAnimal = new Animal(leftLowerCorner);

        map.place(centralAnimal);
        map.place(leftLowerCornerAnimal);

        /* When & Then */

        /* Move into not occupied position. */
        Vector2D newPosition = centralPosition.add(MapDirection.SOUTH.toUnitVector());
        map.move(centralAnimal, MoveDirection.BACKWARD);
        assertFalse(map.isOccupied(centralPosition));
        assertTrue(map.isOccupied(newPosition));
        assertNull(map.getAnimalMap().get(centralPosition));
        assertNotNull(map.getAnimalMap().get(newPosition));
        assertEquals(centralAnimal, map.getAnimalMap().get(newPosition));

        /* Move into occupied position -> no changes should be made. */
        map.move(centralAnimal, MoveDirection.LEFT);
        map.move(centralAnimal, MoveDirection.FORWARD);
        assertTrue(map.isOccupied(leftLowerCorner));
        assertTrue(map.isOccupied(newPosition));
        assertEquals(map.getAnimalMap().get(newPosition), centralAnimal);
        assertEquals(map.getAnimalMap().get(leftLowerCorner), leftLowerCornerAnimal);

        /* Moves beyond map limits -> no changes should be made. */
        map.move(leftLowerCornerAnimal, MoveDirection.BACKWARD);
        assertTrue(map.isOccupied(leftLowerCorner));
        assertEquals(map.getAnimalMap().get(leftLowerCorner), leftLowerCornerAnimal);

        map.move(leftLowerCornerAnimal, MoveDirection.LEFT);
        map.move(leftLowerCornerAnimal, MoveDirection.FORWARD);
        assertTrue(map.isOccupied(leftLowerCorner));
        assertEquals(map.getAnimalMap().get(leftLowerCorner), leftLowerCornerAnimal);
    }

    @Test
    public void getElements() {

        /* Given */
        GrassField grassField = new GrassField(0);
        Animal animal = new Animal(new Vector2D(2,2));
        Animal animal2 = new Animal(new Vector2D(3,3));
        Animal animal3 = new Animal(new Vector2D(100,100));

        /* When */
        grassField.place(animal);
        grassField.place(animal2);
        grassField.place(animal3);

        /* Then */
        assertEquals(3, grassField.getElements().size());
        assertTrue(grassField.getElements().contains(animal));
        assertTrue(grassField.getElements().contains(animal2));
        assertTrue(grassField.getElements().contains(animal3));
    }

}