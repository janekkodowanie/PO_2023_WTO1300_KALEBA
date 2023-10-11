package agh.ics.oop;


import agh.ics.oop.model.MoveDirection;

public class World {
    public static void main(String[] args) {

        System.out.println("Start");

        run(OptionsParser.parse(args));

        System.out.println("Stop");
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