package creatures;

import huglife.*;
import org.junit.Test;
import java.awt.*;
import java.util.HashMap;

import static org.junit.Assert.*;


public class TestClorus {
    @Test
    public void testBasic() {
        double originEnergy = 0.065;
        Clorus c = new Clorus(originEnergy);
        assertEquals("clorus", c.name());
        c.move();
        assertEquals(originEnergy - 0.03, c.energy(), 0.01);
        c.move();
        assertEquals(originEnergy - 0.03 * 2, c.energy(), 0.01);
        c.stay();
        assertEquals(0, c.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), c.color());
    }

    @Test
    public void testChooseAction() {
        Clorus c = new Clorus(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Plip());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());
        assertEquals(new Action(Action.ActionType.STAY), c.chooseAction(surrounded));

        HashMap<Direction, Occupant> EmptyAndPlip = new HashMap<Direction, Occupant>();
        EmptyAndPlip.put(Direction.TOP, new Empty());
        EmptyAndPlip.put(Direction.BOTTOM, new Plip());
        EmptyAndPlip.put(Direction.LEFT, new Impassible());
        EmptyAndPlip.put(Direction.RIGHT, new Impassible());
        assertEquals(new Action(Action.ActionType.ATTACK, Direction.BOTTOM),
                c.chooseAction(EmptyAndPlip));

        HashMap<Direction, Occupant> Empty = new HashMap<Direction, Occupant>();
        Empty.put(Direction.TOP, new Empty());
        Empty.put(Direction.BOTTOM, new Impassible());
        Empty.put(Direction.LEFT, new Impassible());
        Empty.put(Direction.RIGHT, new Impassible());
        assertEquals(new Action(Action.ActionType.REPLICATE, Direction.TOP),
                c.chooseAction(Empty));
        Clorus cc = new Clorus(0.99);
        assertEquals(new Action(Action.ActionType.MOVE, Direction.TOP),
                cc.chooseAction(Empty));

    }

    @Test
    public void testReplicate() {
        Clorus c = new Clorus();
        Clorus cChild = (Clorus) c.replicate();
        assertEquals(c.energy(), 0.5, 0.01);
        assertEquals(cChild.energy(), 0.5, 0.01);
    }
}
