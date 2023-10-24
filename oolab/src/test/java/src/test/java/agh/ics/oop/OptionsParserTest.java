package src.test.java.agh.ics.oop;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        MoveDirection[] expectedDirections = {
                MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.RIGHT, MoveDirection.LEFT
        };

        /* When */
        var directions = OptionsParser.parse(args);

        /* Then */
        assert directions.length == args.length;

        for(int i = 0; i < args.length; i++) {
            assertEquals(directions[i], expectedDirections[i % 4]);
        }
    }

}
