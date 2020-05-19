package it.polimi.ingsw.riccardoemelissa.elements.card;

import it.polimi.ingsw.riccardoemelissa.CommandType;
import it.polimi.ingsw.riccardoemelissa.elements.*;

import java.io.Serializable;
import java.util.ArrayList;

public class Hephaestus extends God implements Serializable {
    private PowerType type=PowerType.DISABLE;

    /**
     * double build if power is active
     *
     * @param b : board
     * @param activeWorker : worker chosen to do the build
     * @param pos -> the build position given by the player belongs to an adjacent box
     * @return
     */
    @Override
    public CommandType build(BoardGame b, Worker activeWorker, int[] pos) {
        if(type.isActive())
            super.build(b,activeWorker,pos);
        return super.build(b,activeWorker,pos);
    }

    /**
     * get adjacent box where possible build on
     *
     * if power is active check the box is max of level 2 to do a double build
     *
     * @param b : board
     * @param worker_pos : actual position of worker
     * @return
     */
    @Override
    public ArrayList<int[]> adjacentBoxNotOccupiedNotDome(BoardGame b, int[] worker_pos) {
        ArrayList<int[]> adj_boxes = new ArrayList<>();

        if(super.getCardType().isMove())
            adj_boxes= super.adjacentBoxNotOccupiedNotDome(b, worker_pos);
        else {
            for (int x = worker_pos[0] - 1; x <= worker_pos[0] + 1; x++) {
                for (int y = worker_pos[1] - 1; y <= worker_pos[1] + 1; y++) {
                    if (x == worker_pos[0] && y == worker_pos[1])
                        continue;

                    if (x > 4 || x < 0)
                        continue;

                    if (y > 4 || y < 0)
                        continue;

                    if (!b.getStateBox(x, y))
                        continue;

                    if (b.getLevelBox(x, y) == 4)
                        continue;

                    if (b.getLevelBox(x, y) > 1 && type.isActive())
                        continue;

                    int[] pos = new int[]{x, y};

                    adj_boxes.add(pos);
                }
            }
        }
        return adj_boxes;
    }

    @Override
    public void setIn_action(PowerType powerSet) {
        if(!type.isPassive())
            type=powerSet;
    }

    @Override
    public PowerType getIn_action() {
        return type;
    }

    @Override
    public void resetCard() {
        super.setCardType(GodCardType.MOVE);
        setIn_action(PowerType.DISABLE);
    }
}
