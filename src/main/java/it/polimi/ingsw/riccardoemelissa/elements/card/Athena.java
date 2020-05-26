package it.polimi.ingsw.riccardoemelissa.elements.card;

import it.polimi.ingsw.riccardoemelissa.elements.*;
import it.polimi.ingsw.riccardoemelissa.CommandType;

import java.io.Serializable;

public class Athena extends God implements Serializable {
    private PowerType type=PowerType.PASSIVE;
    private boolean in_action=false;

    /**
     * Manage Athena's power
     *
     * If the difference between the level of the new position and the level of the old position is one,
     * Athena's power is active until the next turn
     *
     * @param old_pos
     * @param new_pos
     * @param b
     * @return
     */
    public boolean setInAction(int[] old_pos, int[] new_pos, BoardGame b) {
        return (b.getLevelBox(new_pos) - b.getLevelBox(old_pos)) == 1;
    }

    /**
     * Check Athena power in opponent turn and set athena power in owner turn
     *
     * If the active player is the athena owner set athena power on if player moves on,
     * if the active player is an opponent of athena return true if athena allow the chosen move
     *
     * @param b : board
     * @param active_worker : worker chosen to do the move
     * @param newpos : position chosen by player
     * @return
     */
    @Override
    public CommandType move(BoardGame b, Worker active_worker, int[] newpos)
    {
        if (active_worker.getProprietary().getGodCard() instanceof Athena)
        {
            in_action = setInAction(active_worker.getPosition(), newpos, b);
            super.move(b, active_worker, newpos);
        }
        else if (in_action)
        {
            return respectAthenaAction(active_worker.getPosition(), newpos, b);
        }
        return CommandType.BUILD;
    }

    /**
     * Check is a move-on moves
     *
     * @param old_pos : actual opponent worker position
     * @param new_pos : next opponent worker position
     * @param b : board
     * @return
     */
    public CommandType respectAthenaAction(int[] old_pos, int[] new_pos, BoardGame b) {
        if ((b.getLevelBox(new_pos) - b.getLevelBox(old_pos)) > 0)
            return CommandType.ERROR;
        return CommandType.MOVE;
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