package agh.ics.oop.model;

import agh.ics.oop.MapVisualizer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrassFieldTest {

    @Test
    void placeGrass() {

        /* Given */
        int N = 7;
        int maxPossibleIdx = (int) Math.sqrt(10 * N) + 1;
        int minPossibleIdx = 0;

        /* When */
        GrassField grassField = new GrassField(N);
        MapVisualizer visualizer = new MapVisualizer(grassField);

        String mapPicture = visualizer.draw(grassField.getCurrentBounds().leftLowerCorner(), grassField.getCurrentBounds().rightUpperCorner());

        long grasses = mapPicture.chars().filter(character -> character == '*').count();

        /* Then */
        assertTrue(maxPossibleIdx >= grassField.getCurrentBounds().rightUpperCorner().getX());
        assertTrue(maxPossibleIdx >= grassField.getCurrentBounds().rightUpperCorner().getY());
        assertTrue(minPossibleIdx <= grassField.getCurrentBounds().leftLowerCorner().getX());
        assertTrue(minPossibleIdx <= grassField.getCurrentBounds().leftLowerCorner().getY());
        assertEquals(N, grasses);
    }


    @Test
    public void place() {

        /* Given */
        GrassField grassField = new GrassField(10);
        Animal animalTrue = new Animal(new Vector2D(2,2));
        Animal highCoords = new Animal(new Vector2D(100,100));

        Grass grassFalse = new Grass(new Vector2D(2,2));
        Grass grassToBeOverrun = new Grass(new Vector2D(3,3));
        Animal animalTrueOverGrass = new Animal(new Vector2D(3,3));

        /* When */
        grassField.place(animalTrue);
        grassField.place(grassToBeOverrun);

        /* Then */
        assertEquals(animalTrue, grassField.objectAt(new Vector2D(2,2)));
        grassField.place(grassFalse);

        assertTrue(grassField.isOccupied(new Vector2D(3,3)));

        grassField.place(animalTrueOverGrass);
        grassField.place(highCoords);

        assertTrue(grassField.isOccupied(new Vector2D(2,2)));
        assertTrue(grassField.isOccupied(new Vector2D(3,3)));
        assertTrue(grassField.isOccupied(new Vector2D(100,100)));
    }


    @Test
    public void updateVisibleCorners() {

        /* Given */
        GrassField grassField = new GrassField(0);
        Animal animal = new Animal(new Vector2D(2,2));
        Animal animal2 = new Animal(new Vector2D(3,3));
        Animal animal3 = new Animal(new Vector2D(100,100));

        Grass grass = new Grass(new Vector2D(1,1));

        /* When */
        grassField.place(animal);
        grassField.place(animal2);
        grassField.place(animal3);

        /* Then */
        assertEquals(new Vector2D(2,2), grassField.getCurrentBounds().leftLowerCorner());
        assertEquals(new Vector2D(100,100), grassField.getCurrentBounds().rightUpperCorner());

        grassField.place(grass);
        assertEquals(new Vector2D(1,1), grassField.getCurrentBounds().leftLowerCorner());
        assertEquals(new Vector2D(100,100), grassField.getCurrentBounds().rightUpperCorner());
    }


    @Test
    public void move() {
        /* Make test to move function from GrassField class. */
        /* Given */
        GrassField grassField = new GrassField(10);
        Animal animal = new Animal(new Vector2D(2,2));
        Animal animal2 = new Animal(new Vector2D(3,3));
        Animal animal3 = new Animal(new Vector2D(100,100));
        Grass grass = new Grass(new Vector2D(77,77));


        /* When */
        grassField.place(animal);
        grassField.place(animal2);
        grassField.place(animal3);
        grassField.place(grass);

        grassField.move(animal, MoveDirection.FORWARD);
        grassField.move(animal2, MoveDirection.BACKWARD);
        grassField.move(animal3, MoveDirection.RIGHT);

        /* Then */
        assertEquals(new Vector2D(2,3), animal.getPosition());
        assertEquals(new Vector2D(3,2), animal2.getPosition());
        assertEquals(new Vector2D(100,100), animal3.getPosition());

        assertThrows(IllegalArgumentException.class, () -> grassField.move(grass, MoveDirection.LEFT));
    }


    @Test
    public void isOccupied() {

        /* Given */
        GrassField grassField = new GrassField(0);
        Animal animal = new Animal(new Vector2D(2,2));
        Animal animal2 = new Animal(new Vector2D(3,3));
        Animal animal3 = new Animal(new Vector2D(100,100));
        Grass grass = new Grass(new Vector2D(77,77));

        /* When */
        grassField.place(animal);
        grassField.place(animal2);
        grassField.place(animal3);
        grassField.place(grass);

        /* Then */
        assertTrue(grassField.isOccupied(new Vector2D(2,2)));
        assertTrue(grassField.isOccupied(new Vector2D(3,3)));
        assertTrue(grassField.isOccupied(new Vector2D(100,100)));
        assertTrue(grassField.isOccupied(new Vector2D(77,77)));

        assertFalse(grassField.isOccupied(new Vector2D(1,1)));
        assertFalse(grassField.isOccupied(new Vector2D(0,0)));
        assertFalse(grassField.isOccupied(new Vector2D(101,101)));
    }


    @Test
    public void getElements() {

            /* Given */
            GrassField grassField = new GrassField(0);
            Animal animal = new Animal(new Vector2D(2,2));
            Animal animal2 = new Animal(new Vector2D(3,3));
            Animal animal3 = new Animal(new Vector2D(100,100));
            Grass grass = new Grass(new Vector2D(77,77));

            /* When */
            grassField.place(animal);
            grassField.place(animal2);
            grassField.place(animal3);
            grassField.place(grass);

            /* Then */
            assertEquals(4, grassField.getElements().size());
            assertTrue(grassField.getElements().contains(animal));
            assertTrue(grassField.getElements().contains(animal2));
            assertTrue(grassField.getElements().contains(animal3));
            assertTrue(grassField.getElements().contains(grass));
    }


    @Test
    public void objectAt() {

        /* Given */
        GrassField grassField = new GrassField(0);
        Animal animal = new Animal(new Vector2D(2,2));
        Animal animal2 = new Animal(new Vector2D(3,3));
        Animal animal3 = new Animal(new Vector2D(100,100));
        Grass grass = new Grass(new Vector2D(77,77));

        /* When */
        grassField.place(animal);
        grassField.place(animal2);
        grassField.place(animal3);
        grassField.place(grass);

        /* Then */
        assertEquals(animal, grassField.objectAt(new Vector2D(2,2)));
        assertEquals(animal2, grassField.objectAt(new Vector2D(3,3)));
        assertEquals(animal3, grassField.objectAt(new Vector2D(100,100)));
        assertEquals(grass, grassField.objectAt(new Vector2D(77,77)));

        assertNull(grassField.objectAt(new Vector2D(1,1)));
        assertNull(grassField.objectAt(new Vector2D(0,0)));
        assertNull(grassField.objectAt(new Vector2D(101,101)));
    }

}