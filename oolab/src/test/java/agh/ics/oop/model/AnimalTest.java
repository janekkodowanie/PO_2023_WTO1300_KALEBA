package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

class AnimalTest {

    @Test
    public void initTest() {
        Animal animalDefault = new Animal();
        Animal animalSpecific = new Animal(new Vector2D(1, 2));

        assert animalDefault.getPosition().equals(new Vector2D(2, 2));
        assert animalSpecific.getPosition().equals(new Vector2D(1, 2));
        assert animalDefault.getOrientation().equals(MapDirection.NORTH);
        assert animalSpecific.getOrientation().equals(MapDirection.NORTH);
    }

    @Test
    public void move() {

        /* Given */
        Animal animal = new Animal();


        /* Creating instance of anonymous class
        * that implements MoveValidator interface. */
        MoveValidator<Vector2D> validator = new MoveValidator<>() {
            @Override
            public boolean canMoveTo(Vector2D position) {
                return position.precedes(new Vector2D(4, 4))
                        && position.follows(new Vector2D(0, 0));
            }
        };

        /* When */
        animal.move(validator, MoveDirection.FORWARD);

        /* Then */
        assert animal.getPosition().equals(new Vector2D(2, 3));
        assert animal.getOrientation().equals(MapDirection.NORTH);

        /* -||- */
        animal.move(validator, MoveDirection.FORWARD);
        assert animal.getPosition().equals(new Vector2D(2, 4));
        assert animal.getOrientation().equals(MapDirection.NORTH);

        animal.move(validator, MoveDirection.FORWARD);
        assert animal.getPosition().equals(new Vector2D(2, 4));
        assert animal.getOrientation().equals(MapDirection.NORTH);

        animal.move(validator, MoveDirection.LEFT);
        assert animal.getPosition().equals(new Vector2D(2, 4));
        assert animal.getOrientation().equals(MapDirection.WEST);

        animal.move(validator, MoveDirection.FORWARD);
        assert animal.getPosition().equals(new Vector2D(1, 4));
        assert animal.getOrientation().equals(MapDirection.WEST);

        animal.move(validator, MoveDirection.FORWARD);
        assert animal.getPosition().equals(new Vector2D(0, 4));
        assert animal.getOrientation().equals(MapDirection.WEST);

        animal.move(validator, MoveDirection.FORWARD);
        assert animal.getPosition().equals(new Vector2D(0, 4));
        assert animal.getOrientation().equals(MapDirection.WEST);

        animal.move(validator, MoveDirection.LEFT);
        assert animal.getPosition().equals(new Vector2D(0, 4));
        assert animal.getOrientation().equals(MapDirection.SOUTH);

        animal.move(validator, MoveDirection.FORWARD);
        assert animal.getPosition().equals(new Vector2D(0, 3));
        assert animal.getOrientation().equals(MapDirection.SOUTH);

        animal.move(validator, MoveDirection.BACKWARD);
        assert animal.getPosition().equals(new Vector2D(0, 4));
        assert animal.getOrientation().equals(MapDirection.SOUTH);


        Animal leftLowerCornerAnimal = new Animal(new Vector2D(0, 0));
        leftLowerCornerAnimal.move(validator, MoveDirection.BACKWARD);
        assert leftLowerCornerAnimal.getPosition().equals(new Vector2D(0, 0));

        Animal rightUpperCornerAnimal = new Animal(new Vector2D(4, 4));
        rightUpperCornerAnimal.move(validator, MoveDirection.FORWARD);
        assert rightUpperCornerAnimal.getPosition().equals(new Vector2D(4, 4));

        Animal leftUpperCornerAnimal = new Animal(new Vector2D(0, 4));
        leftUpperCornerAnimal.move(validator, MoveDirection.LEFT);
        leftUpperCornerAnimal.move(validator, MoveDirection.FORWARD);
        assert leftUpperCornerAnimal.getPosition().equals(new Vector2D(0, 4));
        assert leftUpperCornerAnimal.getOrientation().equals(MapDirection.WEST);

        Animal rightLowerCornerAnimal = new Animal(new Vector2D(4, 0));
        rightLowerCornerAnimal.move(validator, MoveDirection.RIGHT);
        rightLowerCornerAnimal.move(validator, MoveDirection.FORWARD);
        assert rightLowerCornerAnimal.getPosition().equals(new Vector2D(4, 0));
        assert rightLowerCornerAnimal.getOrientation().equals(MapDirection.EAST);
    }
}
