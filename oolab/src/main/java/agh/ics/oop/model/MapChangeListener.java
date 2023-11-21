package agh.ics.oop.model;

public interface MapChangeListener {

    void mapChanged(WorldMap<WorldElement, Vector2D> worldMap, String message);

}
