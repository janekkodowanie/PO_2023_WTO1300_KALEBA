package agh.ics.oop.model;

import agh.ics.oop.exceptions.PositionNotAvailableException;
import agh.ics.oop.exceptions.PositionOutOfBoundsException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AbstractWorldMapTest {


    @Test
    void canMoveTo() throws PositionNotAvailableException, PositionOutOfBoundsException {

        /* Given */
        int rows = 1, cols = 2;
        RectangularMap map = new RectangularMap(rows, cols);

        Animal animal = new Animal(new Vector2D(0, 0));
        map.place(animal);

        /* When */
        var passingPositions = Arrays.stream(MapDirection.values())
                .map(mapDirection -> animal.getPosition().add(mapDirection.toUnitVector()))
                .filter(pos -> pos.follows(map.getCurrentBounds().leftLowerCorner()) && pos.precedes(map.getCurrentBounds().rightUpperCorner()));

        var notPassingPositions = Arrays.stream(MapDirection.values())
                .map(mapDirection -> animal.getPosition().add(mapDirection.toUnitVector()))
                .filter(pos -> !pos.follows(map.getCurrentBounds().leftLowerCorner()) || !pos.precedes(map.getCurrentBounds().rightUpperCorner()));

        /* Then */
        assertTrue(notPassingPositions.noneMatch(map::canMoveTo));


    }

    @Test
    void isOccupied() throws PositionNotAvailableException, PositionOutOfBoundsException {

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
    void objectAt() throws PositionNotAvailableException, PositionOutOfBoundsException {

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
    public void getElements() throws PositionNotAvailableException, PositionOutOfBoundsException {

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
