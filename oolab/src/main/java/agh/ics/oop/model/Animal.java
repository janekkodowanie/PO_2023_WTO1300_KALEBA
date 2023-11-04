package agh.ics.oop.model;

public class Animal {
    private static final Vector2D defaultPosition = new Vector2D(2,2);

    private MapDirection orientation = MapDirection.NORTH;
    private Vector2D position;

    public Animal(Vector2D position) {
        this.position = position;
    }

    public Animal() {
        this(defaultPosition);
    }

    public String toString(){
        /* N / S / E / W */
        return this.orientation.toString().substring(0,1);
    }

    public boolean isAt(Vector2D position){
        return this.position.equals(position);
    }

    boolean move(MoveValidator validator, MoveDirection direction){
        Vector2D oldPosition = this.position;
        MapDirection orientation = this.orientation;

        switch (direction) {
            case FORWARD -> {
                Vector2D newPosition = this.position.add(this.orientation.toUnitVector());
                if (validator.canMoveTo(newPosition)) {
                    this.position = newPosition;
                }
            }
            case BACKWARD -> {
                Vector2D newPosition = this.position.subtract(this.orientation.toUnitVector());
                if (validator.canMoveTo(newPosition)) {
                    this.position = newPosition;
                }
            }
            case RIGHT -> this.orientation = this.orientation.next();
            case LEFT -> this.orientation = this.orientation.previous();
        }

        return !this.position.equals(oldPosition) || this.orientation != orientation;
    }

    public MapDirection getOrientation() {
        return this.orientation;
    }

    public Vector2D getPosition() {
        return this.position;
    }
}
