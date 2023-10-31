package src.test.java.agh.ics.oop;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2D;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SimulationTest {


    @Test
    public void run() {

        /* Given */
        List<MoveDirection> directionsVertically = List.of(
                MoveDirection.FORWARD, MoveDirection.BACKWARD);

        List<Vector2D> positions = List.of(
                new Vector2D(2,2), new Vector2D(3,4)
        );
        Simulation simulation = new Simulation(directionsVertically, positions);

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
        simulation = new Simulation(directionsHorizontally, positions);
        simulation.run();

        /* Then */
        assert simulation.getAnimals().get(0).getPosition().equals(new Vector2D(2,2));
        assert simulation.getAnimals().get(0).getOrientation().equals(MapDirection.EAST);
        assert simulation.getAnimals().get(1).getPosition().equals(new Vector2D(3,4));
        assert simulation.getAnimals().get(1).getOrientation().equals(MapDirection.WEST);
    }
}
