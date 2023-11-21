package agh.ics.oop.model;

import agh.ics.oop.annotations.Observer;

@Observer
public class ConsoleMapDisplay implements MapChangeListener {

    private int changeCounter;

    public ConsoleMapDisplay() {
        this.changeCounter = 0;
    }

    @Override
    public void mapChanged(WorldMap<WorldElement, Vector2D> worldMap, String message) {
        System.out.println("####################################");
        System.out.println(message);
        System.out.println(worldMap);
        System.out.println("Change counter: " + ++this.changeCounter);
        System.out.println("\n\n");
    }
}
