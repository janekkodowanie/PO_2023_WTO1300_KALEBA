package agh.ics.oop;


import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2D;

public class World {
    public static void main(String[] args) {

        System.out.println("Start");

        run(OptionsParser.parse(args));

        System.out.println("Stop");

        Vector2D position1 = new Vector2D(1,2);
        System.out.println(position1);
        Vector2D position2 = new Vector2D(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));

        System.out.println(MapDirection.NORTH);
        System.out.println(MapDirection.NORTH.next());
        System.out.println(MapDirection.NORTH.previous());
        System.out.println(MapDirection.NORTH.previous().previous());
        System.out.println(MapDirection.NORTH.previous().previous().previous());
    }


    static void run(MoveDirection[] directions) {
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