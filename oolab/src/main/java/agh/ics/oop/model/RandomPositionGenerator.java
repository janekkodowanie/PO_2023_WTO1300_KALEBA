package agh.ics.oop.model;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomPositionGenerator implements Iterable<Vector2D>, Iterator<Vector2D> {

    private final Iterator<Vector2D> iterator;

    public RandomPositionGenerator(int width, int height, int grassCount) {

        /* width <- sqrt(10 * N) + 1, height <- sqrt(10 * N) + 1
         * => width * height -> 10 * N  => O(N) complexity
         * where N = grassCount */
        List<Vector2D> positions = IntStream.range(0, height)
                .boxed()
                .flatMap(i -> IntStream.range(0, width).mapToObj(j -> new Vector2D(j, i)))
                .collect(Collectors.toList());


        /* Fisher-Yates shuffle */
        Collections.shuffle(positions, new Random());

        positions = positions.subList(0, grassCount);

        this.iterator = positions.iterator();
    }

    @Override
    public Iterator<Vector2D> iterator() {
        return this.iterator;
    }

    @Override
    public void forEach(Consumer<? super Vector2D> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<Vector2D> spliterator() {
        return Iterable.super.spliterator();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Vector2D next() {
        return iterator.next();
    }
}
