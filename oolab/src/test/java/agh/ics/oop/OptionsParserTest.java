package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OptionsParserTest {

    @Test
    public void parseArgsToDirections() {

        /* Given */
        String[] args = {
                "f", "b", "r", "l",
                "f", "b", "r", "l",
                "f", "b", "r", "l",
                "f", "b", "r", "l"
        };

        List<MoveDirection> expectedDirections = List.of(
                MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.RIGHT, MoveDirection.LEFT
        );

        /* When */
        var directions = OptionsParser.parse(args);

        /* Then */
        assert directions.size() == args.length;

        for(int i = 0; i < args.length; i++) {
            assertEquals(directions.get(i), expectedDirections.get(i % 4));
        }

        assertThrows(IllegalArgumentException.class, () -> OptionsParser.parse(new String[] {"a"}));
    }

    @Test
    public void parseArgsToDirectionsExtended() {

        /* Given */

        String[] args = {
                "f", "b", "r", "l", "f", "f", "r", "r",
                "f", "f", "f", "f", "f", "f", "f", "f"
        };

        /*
            f b r l
            f f r r
            f f f f
            f f f f
        */

        /* When */
        List<MoveDirection> directions = OptionsParser.parse(args);

        /* Then */
        assert directions.size() == args.length;
        assert directions.equals(List.of(
                MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.RIGHT, MoveDirection.LEFT,
                MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.RIGHT, MoveDirection.RIGHT,
                MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.FORWARD,
                MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.FORWARD));

        assert OptionsParser.parse(new String[] {}).equals(List.of());
    }


}
