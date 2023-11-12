package agh.ics.oop;


import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.RectangularMap;
import agh.ics.oop.model.Vector2D;

import java.util.List;

public class World {

    public static void main(String[] args) {

        /* f b r l f f r r f f f f f f f f */
        List<MoveDirection> directions = OptionsParser.parse(args);
        List<Vector2D> positions = List.of(new Vector2D(2,2), new Vector2D(3,4));

        Simulation simulation = new Simulation(directions, positions);
        simulation.run();
    }


    static void run(List<MoveDirection> directions) {
        for(MoveDirection direction : directions) {
            switch (direction) {
                case FORWARD -> System.out.println("Animal walks forward.");
                case BACKWARD -> System.out.println("Animal walks backward.");
                case RIGHT -> System.out.println("Animal turns right.");
                case LEFT -> System.out.println("Animal turns left.");
            }
        }
    }

}