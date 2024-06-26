package agh.ics.oop.model;

import agh.ics.oop.annotations.Observer;

@Observer
public class ConsoleMapDisplay implements MapChangeListener {

    private int changeCounter;

    public ConsoleMapDisplay() {
        this.changeCounter = 0;
    }

    @Override
    public synchronized void mapChanged(WorldMap<WorldElement, Vector2D> worldMap, String message) {

        String view = "####################################\n" +
                worldMap.getID() + "\n" +
                message + "\n" +
                worldMap +
                "Change counter: " + ++this.changeCounter + "\n\n\n";

        System.out.println(view);
    }
}
