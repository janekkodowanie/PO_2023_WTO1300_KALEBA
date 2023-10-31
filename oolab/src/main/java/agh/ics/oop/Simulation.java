package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2D;

import java.util.List;

public class Simulation {

    private final List<Animal> animals;

    private final List<MoveDirection> moves;

    public Simulation(List<MoveDirection> moves, List<Vector2D> positions) {
        this.animals = positions.stream().map(Animal::new).toList();
        this.moves = moves;
    }

    public void run() {
        for (int i = 0; i < moves.size(); i++) {
            this.animals.get(i % this.animals.size()).move(moves.get(i));
            System.out.println(this.animals.get(i % this.animals.size()) + "\n");
        }
    }

    public List<Animal> getAnimals() {
        /* animals - final */
        return animals;
    }
}
