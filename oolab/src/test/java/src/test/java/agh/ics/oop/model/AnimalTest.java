package src.test.java.agh.ics.oop.model;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2D;
import org.junit.jupiter.api.Test;

public class AnimalTest {

    /* TODO
        Napisz testy integracyjne weryfikujące poprawność implementacji. Uwzględnij:
        czy zwierzę ma właściwą orientację,
        czy zwierzę przemieszcza się na właściwe pozycje,
        czy zwierzę nie wychodzi poza mapę.
    */

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

        /* When */
        animal.move(MoveDirection.FORWARD);

        /* Then */
        assert animal.getPosition().equals(new Vector2D(2, 3));
        assert animal.getOrientation().equals(MapDirection.NORTH);

        /* -||- */
        animal.move(MoveDirection.FORWARD);
        assert animal.getPosition().equals(new Vector2D(2, 4));
        assert animal.getOrientation().equals(MapDirection.NORTH);

        animal.move(MoveDirection.FORWARD);
        assert animal.getPosition().equals(new Vector2D(2, 4));
        assert animal.getOrientation().equals(MapDirection.NORTH);

        animal.move(MoveDirection.LEFT);
        assert animal.getPosition().equals(new Vector2D(2, 4));
        assert animal.getOrientation().equals(MapDirection.WEST);

        animal.move(MoveDirection.FORWARD);
        assert animal.getPosition().equals(new Vector2D(1, 4));
        assert animal.getOrientation().equals(MapDirection.WEST);

        animal.move(MoveDirection.FORWARD);
        assert animal.getPosition().equals(new Vector2D(0, 4));
        assert animal.getOrientation().equals(MapDirection.WEST);

        animal.move(MoveDirection.FORWARD);
        assert animal.getPosition().equals(new Vector2D(0, 4));
        assert animal.getOrientation().equals(MapDirection.WEST);

        animal.move(MoveDirection.LEFT);
        assert animal.getPosition().equals(new Vector2D(0, 4));
        assert animal.getOrientation().equals(MapDirection.SOUTH);

        animal.move(MoveDirection.FORWARD);
        assert animal.getPosition().equals(new Vector2D(0, 3));
        assert animal.getOrientation().equals(MapDirection.SOUTH);

        animal.move(MoveDirection.BACKWARD);
        assert animal.getPosition().equals(new Vector2D(0, 4));
        assert animal.getOrientation().equals(MapDirection.SOUTH);


        Animal leftLowerCornerAnimal = new Animal(new Vector2D(0, 0));
        leftLowerCornerAnimal.move(MoveDirection.BACKWARD);
        assert leftLowerCornerAnimal.getPosition().equals(new Vector2D(0, 0));

        Animal rightUpperCornerAnimal = new Animal(new Vector2D(4, 4));
        rightUpperCornerAnimal.move(MoveDirection.FORWARD);
        assert rightUpperCornerAnimal.getPosition().equals(new Vector2D(4, 4));

        Animal leftUpperCornerAnimal = new Animal(new Vector2D(0, 4));
        leftUpperCornerAnimal.move(MoveDirection.LEFT);
        leftUpperCornerAnimal.move(MoveDirection.FORWARD);
        assert leftUpperCornerAnimal.getPosition().equals(new Vector2D(0, 4));
        assert leftUpperCornerAnimal.getOrientation().equals(MapDirection.WEST);

        Animal rightLowerCornerAnimal = new Animal(new Vector2D(4, 0));
        rightLowerCornerAnimal.move(MoveDirection.RIGHT);
        rightLowerCornerAnimal.move(MoveDirection.FORWARD);
        assert rightLowerCornerAnimal.getPosition().equals(new Vector2D(4, 0));
        assert rightLowerCornerAnimal.getOrientation().equals(MapDirection.EAST);
    }


}
