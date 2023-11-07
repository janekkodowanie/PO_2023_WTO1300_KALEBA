package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.WorldMap;

import java.util.List;

public class Simulation {

    private final List<Animal> animals;

    private final List<MoveDirection> moves;

    private final WorldMap<Animal, Vector2D> worldMap;

    public Simulation(List<MoveDirection> moves, List<Vector2D> positions, WorldMap<Animal, Vector2D> worldMap) {
        this.animals = positions.stream().map(Animal::new).toList();
        this.moves = moves;
        this.worldMap = worldMap;
    }

    public void run() {
        this.animals.forEach(this.worldMap::place);

        MapVisualizer visualizer = new MapVisualizer(this.worldMap);

        Vector2D lowerLeft = this.worldMap.getLeftLowerCorner();
        Vector2D upperRight = this.worldMap.getRightUpperCorner();

        System.out.println(visualizer.draw(lowerLeft, upperRight));

        for (int i = 0; i < this.moves.size(); i++) {

            Animal animal = this.animals.get(i % this.animals.size());
            MoveDirection direction = this.moves.get(i);

            this.worldMap.move(animal, direction);

            System.out.println(visualizer.draw(lowerLeft, upperRight));
        }
    }

    public List<Animal> getAnimals() {
        /* class java.util.ImmutableCollections$ListN */
        return animals;
    }
}
