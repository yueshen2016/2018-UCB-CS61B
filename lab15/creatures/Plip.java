package creatures;
import java.util.Map;
import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

/**
 * An implementation of a motile pacifist photosynthesizer.
 *
 * @author Josh Hug
 */
public class Plip extends Creature {

    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;

    /**
     * creates plip with energy equal to E.
     */
    public Plip(double e) {
        super("plip");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }

    /**
     * creates a plip with energy equal to 1.
     */
    public Plip() {
        this(1);
    }

    /**
     * Should return a color with red = 99, blue = 76, and green that varies
     * linearly based on the energy of the Plip. If the plip has zero energy,
     * it should have a green value of 63. If it has max energy, it should
     * have a green value of 255. The green value should vary with energy
     * linearly in between these two extremes. It's not absolutely vital
     * that you get this exactly correct.
     */
    public Color color() {
        g = (int) (63 + 96 * energy);
        r = 99;
        b = 76;
        return color(r, g, b);
    }

    /**
     * Do nothing with C, Plips are pacifists.
     */
    public void attack(Creature c) {
        // do nothing.
    }

    /**
     * Plips should lose 0.15 units of energy when moving. If you want to
     * to avoid the magic number warning, you'll need to make a
     * private static final variable. This is not required for this lab.
     */
    public void move() {
        // TODO
        energy -= 0.15;
        if (energy < 0) {
            energy = 0;
        }
    }

    /**
     * Plips gain 0.2 energy when staying due to photosynthesis.
     */
    public void stay() {
        // TODO
        energy += 0.2;
        if (energy > 2) {
            energy = 2;
        }
    }

    /**
     * Plips and their offspring each get 50% of the energy, with none
     * lost to the process. Now that's efficiency! Returns a baby
     * Plip.
     */
    public Plip replicate() {
        double babyEnergy = 0.5 * energy;
        energy = 0.5 * energy;
        return new Plip(babyEnergy);
    }

    /**
     * Plips take exactly the following actions based on NEIGHBORS:
     * 1. If no empty adjacent spaces, STAY.
     * 2. Otherwise, if energy >= 1, REPLICATE towards an empty direction
     * chosen at random.
     * 3. Otherwise, if any Cloruses, MOVE with 50% probability,
     * towards an empty direction chosen at random.
     * 4. Otherwise, if nothing else, STAY
     * <p>
     * Returns an object of type Action. See Action.java for the
     * scoop on how Actions work. See SampleCreature.chooseAction()
     * for an example to follow.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Random rand = new Random();
        // Rule 1
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        boolean anyClorus = false;
        // TODO
        // (Google: Enhanced for-loop over keys of NEIGHBORS?)
        Deque<Direction> allNeighbors = new ArrayDeque<>();
        boolean stayFlag = false;
        allNeighbors.add(Direction.TOP);
        allNeighbors.add(Direction.BOTTOM);
        allNeighbors.add(Direction.LEFT);
        allNeighbors.add(Direction.RIGHT);
        for (Direction d : allNeighbors) {
            if (neighbors.get(d).name().equals("empty")) {
                stayFlag = true;
                emptyNeighbors.add(d);
            } else if (neighbors.get(d).name().equals("clorus")) {
                anyClorus = true;
            }
        }

        if (!stayFlag) { // FIXME
            // TODO
            return new Action(Action.ActionType.STAY);
        }

        // Rule 2
        // HINT: randomEntry(emptyNeighbors)
        if (energy >= 1) {
            return new Action(Action.ActionType.REPLICATE, (Direction) emptyNeighbors.toArray()[rand.nextInt(emptyNeighbors.size())]);
        }

        // Rule 3
        if (anyClorus) {
            if (Math.random() < 0.5) {
                return new Action(Action.ActionType.MOVE, (Direction) emptyNeighbors.toArray()[rand.nextInt(emptyNeighbors.size())]);
            }
        }

        // Rule 4
        return new Action(Action.ActionType.STAY);
    }
}
