package it.polimi.ingsw.riccardoemelissa.elements.card;

import it.polimi.ingsw.riccardoemelissa.CommandType;
import it.polimi.ingsw.riccardoemelissa.elements.*;

import java.io.Serializable;
import java.util.ArrayList;

public class Prometheus extends God implements Serializable {
    private PowerType type=PowerType.DISABLE;

    private Boolean malus=false;
    /**
     * Manage turn following prometheus rules
     *
     * If power is active before move active player do a build
     *
     * @param b : board
     * @param worker_pos : actual position of worker
     * @return
     */
    @Override
    public ArrayList<int[]> adjacentBoxNotOccupiedNotDome(BoardGame b, int[] worker_pos) {
        ArrayList<int[]> adj_boxes = new ArrayList<>();

        if(super.getCardType().isMove())
            for (int x = worker_pos[0] - 1; x <= worker_pos[0] + 1; x++) {
                for (int y = worker_pos[1] - 1; y <= worker_pos[1] + 1; y++) {

                    if (x == worker_pos[0] && y == worker_pos[1])
                        continue;

                    if (x > 4 || x < 0)
                        continue;

                    if (y > 4 || y < 0)
                        continue;

                    if(!b.getStateBox(x,y))
                        continue;

                    if(b.getLevelBox(x,y)>b.getLevelBox(worker_pos)&&malus&&super.getCardType()==GodCardType.MOVE)
                        continue;

                    int[] pos = new int[]{x,y};

                    adj_boxes.add(pos);
                }
            }
        else
            adj_boxes=super.adjacentBoxNotOccupiedNotDome(b,worker_pos);

        return adj_boxes;
    }

    /**
     * Build using prometeo power
     *
     * @param b : board
     * @param activeWorker : worker chosen to do the build
     * @param pos -> the build position given by the player belongs to an adjacent box
     * @return
     */
    @Override
    public CommandType build(BoardGame b, Worker activeWorker, int[] pos) {
        if(type.isActive())
        {
            super.build(b,activeWorker,pos);
            super.setCardType(GodCardType.MOVE);
            type=PowerType.DISABLE;
            malus=true;
            return CommandType.MOVE;
        }
        else
            return super.build(b,activeWorker,pos);
    }

    /** Set the status of the power
     *
     * @param powerSet
     */
    @Override
    public void setIn_action(PowerType powerSet) {
        if(powerSet.isActive())
        {
            type=PowerType.ACTIVE;
            super.setCardType(GodCardType.BUILD);
            return;
        }
        type=PowerType.DISABLE;
    }

    /**
     * Get the status of the
     * @return
     */
    @Override
    public PowerType getIn_action() {
        return type;
    }

    /**
     * Set to default value
     */
    @Override
    public void resetCard() {
        super.setCardType(GodCardType.MOVE);
        malus=false;
        setIn_action(PowerType.DISABLE);
    }
}

