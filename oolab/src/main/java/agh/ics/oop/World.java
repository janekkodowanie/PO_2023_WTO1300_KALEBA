package agh.ics.oop;


import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

public class World {

    public static void main(String[] args) {

        /* f b r l f f r r f f f f f f f f */

        try {

            List<Simulation> simulations = new ArrayList<>();

            for (int i = 0; i < 1000; i++) {
                List<MoveDirection> directions = OptionsParser.parse(args);

                WorldMap<WorldElement, Vector2D> map = new GrassField(10);

                map.registerObserver(new ConsoleMapDisplay());

                simulations.add(new Simulation(
                        List.of(new Vector2D(2,2), new Vector2D(3,4)),
                        directions,
                        map));
            }

            SimulationEngine simulationEngine = new SimulationEngine(simulations);
            simulationEngine.runAsyncInThreadPool();
            simulationEngine.awaitSimulationsEnd();

            System.out.println("main stops.");

        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}