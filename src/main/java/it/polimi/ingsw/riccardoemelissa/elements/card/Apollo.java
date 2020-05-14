package it.polimi.ingsw.riccardoemelissa.elements.card;


import it.polimi.ingsw.riccardoemelissa.elements.*;
import it.polimi.ingsw.riccardoemelissa.CommandType;

import java.io.Serializable;
import java.util.ArrayList;

public class Apollo extends God implements Serializable {
    private PowerType type=PowerType.PASSIVE;

    /**
     * do move following apollo rules
     *
     * if position is occupied, the method call method for switch worker position
     *
     * @param b : board
     * @param active_worker : worker chosen to do the move
     * @param newpos : position chosen by player
     * @return
     */
    @Override
    public CommandType Move(BoardGame b, Worker active_worker, int[] newpos)
    {
        if (!b.GetStateBox(newpos))
            SetApolloPosition(active_worker, newpos, b);
        else
            super.SetPosition(active_worker, active_worker.GetPosition(), newpos, b);

        super.setCardType(GodCardType.BUILD);
        return CommandType.MOVE;
    }

    /**
     * get the box where is possible moves in
     *
     * get the box where is possible moves in, following apollo rules
     *
     * @param b : board
     * @param worker_pos : actual position of worker
     * @return
     */
    @Override
    public ArrayList<int[]> adjacentBoxNotOccupiedNotDome(BoardGame b, int[] worker_pos) {
        ArrayList<int[]> adj_boxes = new ArrayList<>();

        for (int x = worker_pos[0] - 1; x <= worker_pos[0] + 1; x++) {
            for (int y = worker_pos[1] - 1; y <= worker_pos[1] + 1; y++) {

                if (x == worker_pos[0] && y == worker_pos[1])
                    continue;

                if (x > 4 || x < 0)
                    continue;

                if (y > 4 || y < 0)
                    continue;

                if(b.GetLevelBox(x,y)==4)
                    continue;

                if(b.GetStateBox(x,y))
                {
                    int[] pos = new int[]{x,y};
                    adj_boxes.add(pos);
                    continue;
                }

                if(b.GetOccupant(x,y).GetProprietary().GetNickname().compareTo(b.GetOccupant(worker_pos).GetProprietary().GetNickname())==0)
                    continue;

                int[] pos = new int[]{x,y};

                adj_boxes.add(pos);
            }
        }
        return adj_boxes;
    }

    /**
     * switch worker position
     *
     * @param active_worker : worker chosen to move by player
     * @param newpos : position chosen by player
     * @param b : board
     */
    public void SetApolloPosition(Worker active_worker, int[] newpos, BoardGame b)
    {
        b.GetOccupant(newpos).SetPosition(active_worker.GetPosition());//da controllare
        b.setOccupant(active_worker.GetPosition(),b.GetOccupant(newpos));

        active_worker.SetPosition(newpos);
        b.setOccupant(newpos,active_worker);
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

