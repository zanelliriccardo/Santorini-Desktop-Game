package it.polimi.ingsw.riccardoemelissa.elements.card;


import it.polimi.ingsw.riccardoemelissa.elements.*;
import it.polimi.ingsw.riccardoemelissa.CommandType;

import java.io.Serializable;
import java.util.ArrayList;

public class Apollo extends God implements Serializable {
    private PowerType type=PowerType.PASSIVE;

    /**
     * Do move following Apollo rules
     *
     * If position is occupied, the method call method for switch worker position
     *
     * @param b
     * @param active_worker : worker chosen to do the move
     * @param newpos : position chosen by player
     * @return
     */
    @Override
    public CommandType move(BoardGame b, Worker active_worker, int[] newpos)
    {
        if (!b.getStateBox(newpos))
            setApolloPosition(active_worker, newpos, b);
        else
            super.setPosition(active_worker, active_worker.getPosition(), newpos, b);

        super.setCardType(GodCardType.BUILD);
        return CommandType.MOVE;
    }

    /**
     * Get the box where is possible moves in
     *
     * Get the box where is possible moves in, following Apollo rules
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

                if(b.getLevelBox(x,y)==4)
                    continue;

                if(b.getStateBox(x,y))
                {
                    int[] pos = new int[]{x,y};
                    adj_boxes.add(pos);
                    continue;
                }

                if(b.getOccupant(x,y).getProprietary().getNickname().compareTo(b.getOccupant(worker_pos).getProprietary().getNickname())==0)
                    continue;

                int[] pos = new int[]{x,y};

                adj_boxes.add(pos);
            }
        }
        else
            adj_boxes= super.adjacentBoxNotOccupiedNotDome(b,worker_pos);
        return adj_boxes;
    }

    /**
     * Switch worker position
     *
     * @param active_worker : worker chosen to move by player
     * @param newpos : position chosen by player
     * @param b : board
     */
    public void setApolloPosition(Worker active_worker, int[] newpos, BoardGame b)
    {
        b.getOccupant(newpos).setPosition(active_worker.getPosition());
        b.setOccupant(active_worker.getPosition(),b.getOccupant(newpos));

        active_worker.setPosition(newpos);
        b.setOccupant(newpos,active_worker);
    }

    /**
     * Set the status of the power
     * @param powerSet
     */
    @Override
    public void setIn_action(PowerType powerSet) {
        if(!type.isPassive())
            type=powerSet;
    }

    /**
     * Get the status of the power
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
        setIn_action(PowerType.DISABLE);
    }
}

