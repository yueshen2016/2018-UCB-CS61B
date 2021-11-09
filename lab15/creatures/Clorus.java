package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Random;

public class Clorus extends Creature {
    public Clorus(double e) {
        super("clorus");
        energy = e;
    }

    public Clorus() {
        this(1);
    }

    public void move() {
        energy -= 0.03;
        if (energy < 0) {
            energy = 0;
        }
    }

    public void attack(Creature c) {
        energy += c.energy();
    }

    public Creature replicate() {
        double babyEnergy = 0.5 * energy;
        energy = 0.5 * energy;
        return new Clorus(babyEnergy);
    }

    public void stay() {
        energy -= 0.01;
        if (energy < 0) {
            energy = 0;
        }
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Deque<Direction> allNeighbors = new ArrayDeque<>();
        allNeighbors.add(Direction.TOP);
        allNeighbors.add(Direction.BOTTOM);
        allNeighbors.add(Direction.LEFT);
        allNeighbors.add(Direction.RIGHT);
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plipNeighbors = new ArrayDeque<>();
        boolean plipFlag = false;
        boolean noEmptyFlag = true;
        Random rand = new Random();
        //Rule 1:
        for (Direction d : allNeighbors) {
            if (neighbors.get(d).name().equals("empty")) {
                noEmptyFlag = false;
                emptyNeighbors.add(d);
            } else if (neighbors.get(d).name().equals("plip")) {
                plipFlag = true;
                plipNeighbors.add(d);
            }
        }
        if (noEmptyFlag) {
            return new Action(Action.ActionType.STAY);
        }

        //Rule 2:
        if (plipFlag) {
            return new Action(Action.ActionType.ATTACK,
                    (Direction) plipNeighbors.toArray()[rand.nextInt(plipNeighbors.size())]);
        }

        //Rule 3:
        if (energy >= 1) {
            return new Action(Action.ActionType.REPLICATE,
                    (Direction) emptyNeighbors.toArray()[rand.nextInt(emptyNeighbors.size())]);
        }

        //Rule 4:
        return new Action(Action.ActionType.MOVE,
                (Direction) emptyNeighbors.toArray()[rand.nextInt(emptyNeighbors.size())]);
    }

    public Color color(){
        return color(34, 0, 231);
    }

}
