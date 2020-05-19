package it.polimi.ingsw.riccardoemelissa.elements.card;

import it.polimi.ingsw.riccardoemelissa.elements.*;
import it.polimi.ingsw.riccardoemelissa.CommandType;

import java.io.Serializable;

public class Pan extends God implements Serializable {
    private PowerType type=PowerType.PASSIVE;

    /**
     * manage win condition following rules pan
     *
     * if different level is 2 or plus active player win the game
     *
     * @param b : board
     * @param active_worker : worker chosen to do the move
     * @param newpos : position chosen by player
     * @return
     */
    @Override
    public CommandType move(BoardGame b, Worker active_worker, int[] newpos)
    {
        int[] old_position=active_worker.getPosition();//problema reference??
        super.move(b, active_worker, newpos);

        if (b.getLevelBox(old_position) - b.getLevelBox(newpos) > 1)
            super.setCardType(GodCardType.WIN); //settare anche position??? provare a mandare fake comando a executor con win da server

       return CommandType.BUILD;
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
