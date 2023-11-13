package agh.ics.oop.model;

import java.util.Collections;
import java.util.Map;

public class RectangularMap extends AbstractWorldMap {

    public RectangularMap(int width, int height) {
        super(width - 1, height - 1, width * height);
    }

    Map<Vector2D, Animal> getAnimalMap() {
        return Collections.unmodifiableMap(animalMap);
    }

}
