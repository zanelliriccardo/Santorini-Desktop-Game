package it.polimi.ingsw.riccardoemelissa.elements.card;

import it.polimi.ingsw.riccardoemelissa.elements.*;
import it.polimi.ingsw.riccardoemelissa.CommandType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Demeter extends God implements Serializable {
    private PowerType type=PowerType.DISABLE;

    private int[] old_position=new int[]{-1,-1};

    /**
     * Apply Demeter rules if power is active
     *
     * If is the first build of turn, saves the position and during the next build
     * of this turn check they is different
     *
     * @param b : board
     * @param active_Worker : worker chosen to do the build
     * @param pos : position chosen by player
     * @return
     */
    @Override
    public CommandType build(BoardGame b, Worker active_Worker, int[] pos)
    {
        if(old_position[0]==-1&&type.isActive())
        {
            old_position[0] = pos[0];
            old_position[1] = pos[1];
            super.build(b,active_Worker,pos);

            if(type.isActive())
                super.setCardType(GodCardType.BUILD);
            else
                super.setCardType(GodCardType.ENDTURN);

            return CommandType.BUILD;
        }
        else
        {
            old_position[0] = -1;
            return super.build(b, active_Worker, pos);
        }
    }

    /**
     * Get adjacent box where possible moves in following Demeter rules
     *
     * @param b : board
     * @param worker_pos : actual position of worker
     * @return
     */
    @Override
    public ArrayList<int[]> adjacentBoxNotOccupiedNotDome(BoardGame b, int[] worker_pos) {
        ArrayList<int[]> possibleBox=super.adjacentBoxNotOccupiedNotDome(b, worker_pos);
        if(type.isActive()&&old_position[0]!=-1&&super.getCardType()==GodCardType.BUILD) {
            //possibleBox.remove(old_position);
            System.out.println("pos da eliminare : " + Arrays.toString(old_position));
            possibleBox.removeIf(p -> p[0] == old_position[0] && p[1] == old_position[1]);
        }
        for (int[] pos : possibleBox)
            System.out.println("Demeter -> pos : " + Arrays.toString(pos));
        return possibleBox;
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