package it.polimi.ingsw.riccardoemelissa.elements.card;

import it.polimi.ingsw.riccardoemelissa.elements.*;
import it.polimi.ingsw.riccardoemelissa.CommandType;

import java.io.Serializable;

public class Atlas extends God implements Serializable {
    private PowerType type=PowerType.DISABLE;

    /**
     * apply atlas rules if power is active
     *
     * if power is active build a dome
     *
     * @param b:board
     * @param activeWorker : worker chosen to do the build
     * @param pos -> the build position given by the player belongs to an adjacent box
     * @return
     */
    @Override
    public CommandType build(BoardGame b, Worker activeWorker, int[] pos)
    {
        if (type.isActive())
        {
            b.buildDome(pos);
            super.setCardType(GodCardType.ENDTURN);
            return CommandType.BUILD;
        }
        else
        {
            return super.build(b,activeWorker,pos);
        }
    }

    /**
     * set the status of the power
     * @param powerSet
     */
    @Override
    public void setIn_action(PowerType powerSet) {
        if(!type.isPassive())
            type=powerSet;
    }

    /**
     * get the status of the power
     * @return
     */
    @Override
    public PowerType getIn_action() {
        return type;
    }


    /**
     * reset the God card
     */
    @Override
    public void resetCard() {
        super.setCardType(GodCardType.MOVE);
        setIn_action(PowerType.DISABLE);
    }
}
