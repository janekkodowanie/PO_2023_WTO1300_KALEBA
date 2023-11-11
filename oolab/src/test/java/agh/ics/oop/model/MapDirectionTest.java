package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static agh.ics.oop.model.MapDirection.*;

class MapDirectionTest {

    @Test
    public void next() {

        /* Given */
        MapDirection[] directions = {NORTH, SOUTH, EAST, WEST};

        MapDirection[] nextResults = {EAST, WEST, SOUTH, NORTH};

        for (int i = 0; i < directions.length; i++) {

            /* When */
            MapDirection next = directions[i].next();

            /* Then */
            assert next == nextResults[i];
        }
    }

    @Test
    public void previous() {

        /* Given */
        MapDirection[] directions = {EAST, WEST, SOUTH, NORTH};

        MapDirection[] prevResults = {NORTH, SOUTH, EAST, WEST};

        for (int i = 0; i < directions.length; i++) {

            /* When */
            MapDirection previous = directions[i].previous();

            /* Then */
            assert previous == prevResults[i];
        }
    }
}
