package agh.ics.oop.model;

import agh.ics.oop.annotations.Observer;

@Observer
public interface MapChangeListener {

    void mapChanged(WorldMap<WorldElement, Vector2D> worldMap, String message);

}
