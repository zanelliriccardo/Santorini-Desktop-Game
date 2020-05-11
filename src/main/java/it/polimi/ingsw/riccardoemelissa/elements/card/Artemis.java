package it.polimi.ingsw.riccardoemelissa.elements.card;

import it.polimi.ingsw.riccardoemelissa.elements.*;
import it.polimi.ingsw.riccardoemelissa.CommandType;

import java.io.Serializable;
import java.util.ArrayList;

public class Artemis extends God implements Serializable {
    private PowerType type=PowerType.DISABLE;

    private int[] old_position=null;

    /**
     * apply artemis rules if power is active
     *
     * if is the first move of turn, saves the position and during the next move
     * of this turn check they is different
     *
     * @param b : board
     * @param active_worker : worker chosen to do the move
     * @param newpos : position chosen by player
     * @return
     */
    @Override
    public CommandType Move(BoardGame b, Worker active_worker, int[] newpos)
    {
        if(old_position==null)
        {
            old_position = active_worker.GetPosition();
            super.Move(b,active_worker,newpos);

            if(type.IsActive())
                super.setCardType(GodCardType.MOVE);
            else
                super.setCardType(GodCardType.BUILD);

            return CommandType.MOVE;
        }
        else
        {
            old_position=null;
            return super.Move(b, active_worker, newpos);
        }
    }

    /**
     * get adjacent box where possible moves in, follow artemis rules
     *
     * @param b : board
     * @param worker_pos : actual position of worker
     * @return
     */
    @Override
    public ArrayList<int[]> adjacentBoxNotOccupiedNotDome(BoardGame b, int[] worker_pos) {
        ArrayList<int[]> possibleBox=super.adjacentBoxNotOccupiedNotDome(b, worker_pos);
        if(type.IsActive()&&old_position!=null&&super.getCardType()==GodCardType.MOVE)
            possibleBox.remove(old_position);

        return possibleBox;
    }

    @Override
    public void setIn_action(PowerType powerSet) {
        if(!type.IsPassive())
            type=powerSet;
    }
}
