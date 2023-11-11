package agh.ics.oop.model;

import java.util.Objects;

public class Vector2D {
    private final int x;
    private final int y;

    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public boolean precedes(Vector2D other) {
        return this.x <= other.getX() && this.y <= other.getY();
    }

    public boolean follows(Vector2D other) {
        return this.x >= other.getX() && this.y >= other.getY();
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.getX(), this.y + other.getY());
    }

    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.getX(), this.y - other.getY());
    }

    public Vector2D upperRight(Vector2D other) {
        return new Vector2D(
                Math.max(this.x, other.getX()), Math.max(this.y, other.getY())
        );
    }

    public Vector2D lowerLeft(Vector2D other) {
        return new Vector2D(
                Math.min(this.x, other.getX()), Math.min(this.y, other.getY())
        );
    }

    public Vector2D opposite() {
        return new Vector2D(-this.x, -this.y);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Vector2D that))
            return false;
        return this.x == that.getX() && this.y == that.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }
}
