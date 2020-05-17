package it.polimi.ingsw.riccardoemelissa.elements.card;

import it.polimi.ingsw.riccardoemelissa.elements.*;
import it.polimi.ingsw.riccardoemelissa.CommandType;

import java.io.Serializable;
import java.util.ArrayList;

public class Artemis extends God implements Serializable {
    private PowerType type=PowerType.DISABLE;

    private int[] old_position = new int[]{-1,-1};

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
        if(old_position[0]==-1&&type.isActive())
        {
            //old_position=new int[2];
            old_position[0] = active_worker.GetPosition()[0];
            old_position[1] = active_worker.GetPosition()[1];

            super.Move(b,active_worker,newpos);

            if(type.isActive())
                super.setCardType(GodCardType.MOVE);
            else
                super.setCardType(GodCardType.BUILD);

            return CommandType.MOVE;
        }
        else
        {
            //old_position=null;
            old_position[0] = -1;
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
        if(type.isActive()&&old_position[0]!=-1&&super.getCardType()==GodCardType.MOVE)
            //possibleBox.remove(old_position);
            possibleBox.removeIf(pos -> pos[0] == old_position[0] && pos[1] == old_position[1]);

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

    @Override
    public void resetCard() {
        super.setCardType(GodCardType.MOVE);
        setIn_action(PowerType.DISABLE);
    }
}
