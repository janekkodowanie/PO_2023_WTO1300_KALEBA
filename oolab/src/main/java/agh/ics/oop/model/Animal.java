package agh.ics.oop.model;

public class Animal {
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2D position;

    public Animal(Vector2D position) {
        this.position = position;
    }

    public Animal() {
        this.position = new Vector2D(2,2);
    }

    public String toString(){
        return "Position: " + this.position.toString() + " Orientation: " + this.orientation.toString();
    }

    public boolean isAt(Vector2D position){
        return this.position.equals(position);
    }

    public void move(MoveDirection direction){

        Vector2D newPosition = switch (direction) {
            case FORWARD -> this.position.add(this.orientation.toUnitVector());
            case BACKWARD -> this.position.add(this.orientation.toUnitVector().opposite());
            default -> this.position;
        };

        if (newPosition.follows(new Vector2D(0,0)) && newPosition.precedes(new Vector2D(4,4))) {
            this.position = newPosition;
            this.orientation = switch (direction) {
                case RIGHT -> this.orientation.next();
                case LEFT -> this.orientation.previous();
                default -> this.orientation;
            };
        }
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public Vector2D getPosition() {
        return new Vector2D(this.position.getX(), this.position.getY());
    }
}
