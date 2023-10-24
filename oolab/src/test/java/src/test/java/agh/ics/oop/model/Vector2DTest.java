package src.test.java.agh.ics.oop.model;


import agh.ics.oop.model.Vector2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Vector2DTest {

    @Test
    public void testToString() {

        /* Given */
        Vector2D a = new Vector2D(1, 2);
        Vector2D b = new Vector2D(-1, 2);
        Vector2D c = new Vector2D(-1, -2);

        /* When */
        String resA = a.toString();
        String resB = b.toString();
        String resC = c.toString();

        /* Then */
        assert resA.equals("(1, 2)");
        assert resB.equals("(-1, 2)");
        assert resC.equals("(-1, -2)");
    }

    @Test
    public void precedes() {

        /* Given */
        Vector2D a = new Vector2D(1, 1);
        Vector2D b = new Vector2D(2, 2);
        Vector2D c = new Vector2D(3, 3);

        /* When */
        boolean aPrecedesB = a.precedes(b);
        boolean bPrecedesC = b.precedes(c);
        boolean cPrecedesA = c.precedes(a);

        /* Then */
        assert aPrecedesB;
        assert bPrecedesC;
        assert !cPrecedesA;
    }

    @Test
    public void follows() {

        /* Given */
        Vector2D c = new Vector2D(3, 3);
        Vector2D b = new Vector2D(2, 2);
        Vector2D a = new Vector2D(1, 1);

        /* When */
        boolean cFollowsB = c.follows(b);
        boolean bFollowsA = b.follows(a);
        boolean aFollowsC = a.follows(c);

        /* Then */
        assert cFollowsB;
        assert bFollowsA;
        assert !aFollowsC;
    }

    @Test
    public void add() {

        /* Given */
        Vector2D a = new Vector2D(1, 1);
        Vector2D b = new Vector2D(2, 2);

        var properResult = new Vector2D(3, 3);

        /* When */
        Vector2D c = a.add(b);
        Vector2D d = b.add(a);

        /* Then */
        assert c.equals(properResult);
        assert d.equals(properResult);
    }

    @Test
    public void subtract() {

        /* Given */
        Vector2D a = new Vector2D(4, 3);
        Vector2D b = new Vector2D(1, 2);

        var aSubB = new Vector2D(3, 1);
        var bSubA = new Vector2D(-3, -1);

        /* When */
        Vector2D c = a.subtract(b);
        Vector2D d = b.subtract(a);

        /* Then */
        assert c.equals(aSubB);
        assert d.equals(bSubA);
    }

    @Test
    public void upperRight() {

        /* Given */
        Vector2D a = new Vector2D(-2, 3);
        Vector2D b = new Vector2D(3, 2);

        var properResult = new Vector2D(3, 3);

        /* When */
        Vector2D c = a.upperRight(b);
        Vector2D d = b.upperRight(a);

        /* Then */
        assert c.equals(properResult);
        assert d.equals(properResult);
    }

    @Test
    public void lowerLeft() {

        /* Given */
        Vector2D a = new Vector2D(-2, 3);
        Vector2D b = new Vector2D(3, 2);

        var properResult = new Vector2D(-2, 2);

        /* When */
        Vector2D c = a.lowerLeft(b);
        Vector2D d = b.lowerLeft(a);

        /* Then */
        assertEquals(c, properResult);
        assertEquals(d, properResult);

    }

    @Test
    public void opposite() {

        /* Given */
        Vector2D a = new Vector2D(-2, 3);
        Vector2D b = new Vector2D(2, -3);

        /* When & Then */
        assertEquals(a.opposite(), b);
        assertEquals(b.opposite(), a);
    }

    @Test
    public void testEquals() {

        /* Given */
        Vector2D a = new Vector2D(-2, 3);
        Vector2D b = new Vector2D(2, -3);

        Vector2D c = a.opposite();

        /* When & Then */
        assert a.equals(a);
        assert c.equals(b);
        assert !a.equals(b);
        assert !a.equals("Other object of other class");
    }
}
