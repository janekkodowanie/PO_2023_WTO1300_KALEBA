package agh.ics.oop.model;

import agh.ics.oop.exceptions.PositionNotAvailableException;
import agh.ics.oop.exceptions.PositionOutOfBoundsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangularMapTest {

    @Test
    void place() throws PositionNotAvailableException {

        /* Given */
        RectangularMap rectangularMap = new RectangularMap(1, 1);

        Vector2D position = new Vector2D(0, 0);
        Animal animal = new Animal(position);

        Vector2D positionDuplicate = new Vector2D(0, 0);
        Animal animalDuplicate = new Animal(positionDuplicate);

        /* When */
        rectangularMap.place(animal);

        /* Then */
        assertThrows(PositionNotAvailableException.class, () -> rectangularMap.place(animalDuplicate));

        assert rectangularMap.getAnimalMap().size() == 1;
        assert !rectangularMap.getAnimalMap().containsValue(animalDuplicate);

        assert rectangularMap.getAnimalMap().containsValue(animal);
        assert rectangularMap.getAnimalMap().containsKey(position);
    }

    @Test
    void move() throws PositionNotAvailableException {

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
        assertThrows(PositionNotAvailableException.class, () -> map.move(centralAnimal, MoveDirection.FORWARD));
        assertTrue(map.isOccupied(leftLowerCorner));
        assertTrue(map.isOccupied(newPosition));
        assertEquals(map.getAnimalMap().get(newPosition), centralAnimal);
        assertEquals(map.getAnimalMap().get(leftLowerCorner), leftLowerCornerAnimal);

        /* Moves beyond map limits -> no changes should be made. */
        assertThrows(PositionNotAvailableException.class, () -> map.move(leftLowerCornerAnimal, MoveDirection.BACKWARD));
        assertTrue(map.isOccupied(leftLowerCorner));
        assertEquals(map.getAnimalMap().get(leftLowerCorner), leftLowerCornerAnimal);

        map.move(leftLowerCornerAnimal, MoveDirection.LEFT);
        assertThrows(PositionNotAvailableException.class, () -> map.move(leftLowerCornerAnimal, MoveDirection.FORWARD));
        assertTrue(map.isOccupied(leftLowerCorner));
        assertEquals(map.getAnimalMap().get(leftLowerCorner), leftLowerCornerAnimal);
    }
}