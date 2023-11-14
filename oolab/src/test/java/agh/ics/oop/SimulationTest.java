package agh.ics.oop;

import agh.ics.oop.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SimulationTest {

    @Test
    public void run() {

        /* Given */
        List<MoveDirection> directionsVertically = List.of(
                MoveDirection.FORWARD, MoveDirection.BACKWARD);

        List<Vector2D> positions = List.of(
                new Vector2D(2,2), new Vector2D(3,4)
        );

        Simulation simulation = new Simulation(directionsVertically, positions, 10);

        /* When */
        simulation.run();

        /* Then */
        assert simulation.getAnimals().get(0).getPosition().equals(new Vector2D(2,3));
        assert simulation.getAnimals().get(0).getOrientation().equals(MapDirection.NORTH);
        assert simulation.getAnimals().get(1).getPosition().equals(new Vector2D(3,3));
        assert simulation.getAnimals().get(1).getOrientation().equals(MapDirection.NORTH);


        /* Given */
        List<MoveDirection> directionsHorizontally = List.of(
                MoveDirection.RIGHT, MoveDirection.LEFT);

        /* When */
        simulation = new Simulation(directionsHorizontally, positions, 10);
        simulation.run();

        /* Then */
        assert simulation.getAnimals().get(0).getPosition().equals(new Vector2D(2,2));
        assert simulation.getAnimals().get(0).getOrientation().equals(MapDirection.EAST);
        assert simulation.getAnimals().get(1).getPosition().equals(new Vector2D(3,4));
        assert simulation.getAnimals().get(1).getOrientation().equals(MapDirection.WEST);
    }

    @Test
    public void runWithNoAnimals() {

        List<MoveDirection> directionsVertically = List.of(
                MoveDirection.FORWARD, MoveDirection.BACKWARD);

        List<Vector2D> positions = List.of(
                new Vector2D(-1,-1), new Vector2D(-1,-1)
        );

        Simulation simulation = new Simulation(directionsVertically, positions, 4, 4);

        assertThrows(IllegalStateException.class, simulation::run);

    }

}
