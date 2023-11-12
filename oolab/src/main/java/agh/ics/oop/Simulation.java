package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.List;

public class Simulation {

    private final List<Animal> animals;

    private final List<MoveDirection> moves;

    private final WorldMap<WorldElement, Vector2D> worldMap;

    public Simulation(List<MoveDirection> moves, List<Vector2D> positions) {
        this.animals = positions.stream().map(Animal::new).toList();
        this.moves = moves;
        this.worldMap = new GrassField(10);
    }

    public void run() {
        this.animals.forEach(this.worldMap::place);

        if (this.animals.isEmpty()) {
            throw new IllegalStateException("There is no animals to simulate!");
        }


        MapVisualizer visualizer = new MapVisualizer(this.worldMap);

        Vector2D upperRight = this.worldMap.getRightUpperCorner();
        Vector2D lowerLeft = this.worldMap.getLeftLowerCorner();

        System.out.println(visualizer.draw(lowerLeft, upperRight));

        for (int i = 0; i < this.moves.size(); i++) {

            Animal animal = this.animals.get(i % this.animals.size());
            MoveDirection direction = this.moves.get(i);

            this.worldMap.move(animal, direction);

            upperRight = this.worldMap.getRightUpperCorner();
            lowerLeft = this.worldMap.getLeftLowerCorner();

            System.out.println(visualizer.draw(lowerLeft, upperRight));
        }
    }

    public List<Animal> getAnimals() {
        /* class java.util.ImmutableCollections$ListN */
        return animals;
    }
}
