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
    public CommandType Build(BoardGame b, Worker activeWorker, int[] pos)
    {
        if (type.IsActive())
        {
            b.BuildDome(pos);
            super.setCardType(GodCardType.ENDTURN);
            return CommandType.BUILD;
        }
        else
        {
            return super.Build(b,activeWorker,pos);
        }
    }

    @Override
    public void setIn_action(PowerType powerSet) {
        if(!type.IsPassive())
            type=powerSet;
    }
}
