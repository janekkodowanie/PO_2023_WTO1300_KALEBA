package agh.ics.oop.model;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2D and MoveDirection classes are defined.
 *
 * @author apohllo, idzik
 */
public interface WorldMap<T, P> extends MoveValidator<P> {


    /**
     * Place a T object on the map.
     * @return True if the object was placed. The animal cannot be placed if the move is not valid.
     */
    boolean place(T object);

    /**
     * Moves an animal (if it is present on the map) according to specified direction.
     * If the move is not possible, this method has no effect.
     */
    boolean move(T object, MoveDirection direction);

    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(P position);

    /**
     * Return an animal at a given position.
     *
     * @param position The position of the animal.
     * @return animal or null if the position is not occupied.
     */
    WorldElement objectAt(P position);


    P getLeftLowerCorner();
    P getRightUpperCorner();


}