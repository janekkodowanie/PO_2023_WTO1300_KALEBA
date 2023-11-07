package agh.ics.oop.model;

import java.util.HashMap;
import java.util.Map;

public class GrassField implements WorldMap<Grass, Vector2D> {


    private final int N;
    private final Map<Vector2D, Grass> grassMap;

    private final Vector2D leftLowerCorner;
    private final Vector2D rightUpperCorner;

    public GrassField(int numberOfGrass) {
        grassMap = new HashMap<>(numberOfGrass);

        this.N = numberOfGrass;
        this.leftLowerCorner = new Vector2D(0,0);
        this.rightUpperCorner = new Vector2D(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    @Override
    public boolean canMoveTo(Vector2D position) {
        return grassMap.containsKey(position);
    }

    @Override
    public boolean place(Grass object) {

        int added = 0;
        while (added < N) {
            /* todo */
            Vector2D position = new Vector2D(0, 0);
            if (!grassMap.containsKey(position)) {
                grassMap.put(position, object);
                added++;
            }
        }


        return false;
    }

    @Override
    public void move(Grass object, MoveDirection direction) {

    }

    @Override
    public boolean isOccupied(Vector2D position) {
        return false;
    }

    @Override
    public Grass objectAt(Vector2D position) {
        return null;
    }

    @Override
    public Vector2D getLeftLowerCorner() {
        return null;
    }

    @Override
    public Vector2D getRightUpperCorner() {
        return null;
    }

    public String toString() {
        return " ";
    }


}
