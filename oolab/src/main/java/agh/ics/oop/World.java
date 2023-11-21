package agh.ics.oop;


import agh.ics.oop.model.*;

import java.util.List;

public class World {

    public static void main(String[] args) {

        /* f b r l f f r r f f f f f f f f */
        List<MoveDirection> directions = OptionsParser.parse(args);
        List<Vector2D> positions = List.of(new Vector2D(2,2), new Vector2D(3,4));


        WorldMap<WorldElement, Vector2D> map = new GrassField(10);

        ConsoleMapDisplay consoleMapDisplay = new ConsoleMapDisplay();
        map.registerObserver(consoleMapDisplay);

        Simulation simulation = new Simulation(positions, directions, map);
        simulation.run();
    }
}