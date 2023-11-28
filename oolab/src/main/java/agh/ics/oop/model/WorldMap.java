package agh.ics.oop.model;

import agh.ics.oop.exceptions.PositionNotAvailableException;

import java.util.List;
import java.util.UUID;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2D and MoveDirection classes are defined.
 *
 * @author apohllo, idzik
 */
public interface WorldMap<T, P> extends MoveValidator<P> {


    UUID getID();

    /**
     * Place a T object on the map.
     */
    void place(T object) throws PositionNotAvailableException;

    /**
     * Moves an animal (if it is present on the map) according to specified direction.
     * If the move is not possible, this method has no effect.
     */
    void move(T object, MoveDirection direction) throws PositionNotAvailableException;

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

    List<WorldElement> getElements();

    void registerObserver(MapChangeListener observer);
    void unregisterObserver(MapChangeListener observer);





}