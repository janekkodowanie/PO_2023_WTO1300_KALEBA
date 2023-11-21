package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.List;

public class Simulation {

    private final List<Animal> animals;

    private final List<MoveDirection> moves;

    private final WorldMap<WorldElement, Vector2D> worldMap;

    public Simulation(List<Vector2D> positions, List<MoveDirection> moves, WorldMap<WorldElement, Vector2D> worldMap) {
        this.animals = positions.stream().map(Animal::new).toList();
        this.moves = moves;
        this.worldMap = worldMap;
    }

    public void run() {

        this.animals.forEach(this.worldMap::place);

        if (this.worldMap.getElements().stream().noneMatch(element -> element instanceof Animal)) {
            throw new IllegalStateException("There is no animals to simulate!");
        }

        for (int i = 0; i < this.moves.size(); i++) {
            Animal animal = this.animals.get(i % this.animals.size());
            MoveDirection direction = this.moves.get(i);

            this.worldMap.move(animal, direction);
        }
    }

    public List<Animal> getAnimals() {
        /* class java.util.ImmutableCollections$ListN */
        return animals;
    }
}
