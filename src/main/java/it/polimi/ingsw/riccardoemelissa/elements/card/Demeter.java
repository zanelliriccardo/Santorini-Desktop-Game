package it.polimi.ingsw.riccardoemelissa.elements.card;

import it.polimi.ingsw.riccardoemelissa.elements.*;
import it.polimi.ingsw.riccardoemelissa.CommandType;

import java.io.Serializable;
import java.util.ArrayList;

public class Demeter extends God implements Serializable {
    private PowerType type=PowerType.DISABLE;

    private int[] old_position=null;

    /**
     * apply demeter rules if power is active
     *
     * if is the first build of turn, saves the position and during the next build
     * of this turn check they is different
     *
     * @param b : board
     * @param active_Worker
     * @param pos -> the build position given by the player belongs to an adjacent box
     * @return
     */
    @Override
    public CommandType Build(BoardGame b, Worker active_Worker, int[] pos)
    {
        if(old_position==null)
        {
            old_position = pos;
            super.Build(b,active_Worker,pos);

            if(type.isActive())
                super.setCardType(GodCardType.BUILD);
            else
                super.setCardType(GodCardType.ENDTURN);

            return CommandType.BUILD;
        }
        else
        {
            old_position=null;
            return super.Build(b, active_Worker, pos);
        }
    }

    /**
     * get adjacent box where possible moves in following demeter rules
     *
     * @param b : board
     * @param worker_pos : actual position of worker
     * @return
     */
    @Override
    public ArrayList<int[]> adjacentBoxNotOccupiedNotDome(BoardGame b, int[] worker_pos) {
        ArrayList<int[]> possibleBox=super.adjacentBoxNotOccupiedNotDome(b, worker_pos);
        if(type.isActive()&&old_position!=null&&super.getCardType()==GodCardType.BUILD)
            possibleBox.remove(old_position);

        return possibleBox;
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
}