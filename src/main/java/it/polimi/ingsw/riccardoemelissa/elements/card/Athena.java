package it.polimi.ingsw.riccardoemelissa.elements.card;

import it.polimi.ingsw.riccardoemelissa.elements.*;
import it.polimi.ingsw.riccardoemelissa.CommandType;

import java.io.Serializable;

public class Athena extends God implements Serializable {
    private PowerType type=PowerType.PASSIVE;

    private boolean in_action=false;

    public boolean setInAction(int[] old_pos, int[] new_pos, BoardGame b) {
        if ((b.getLevelBox(new_pos) - b.getLevelBox(old_pos)) == 1)
            return true;
        return false;
    }

    /**
     * check athena power in opponent turn and set athena power in owner turn
     *
     * if the active player is the athena owner set athena power on if player moves on,
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
        System.out.println("in action = " + in_action);
        if (active_worker.getProprietary().getGodCard() instanceof Athena)
        {
            in_action = setInAction(active_worker.getPosition(), newpos, b);
            super.move(b, active_worker, newpos);
            System.out.println("in action = " + in_action);
        }
        else if (in_action)
        {
            System.out.println("in action = " + in_action);
            return RespectAthenaAction(active_worker.getPosition(), newpos, b);
        }
        return CommandType.BUILD;
    }

    /**
     * check is a move-on moves
     *
     * @param old_pos : actual opponent worker position
     * @param new_pos : next opponent worker position
     * @param b : board
     * @return
     */
    public CommandType RespectAthenaAction(int[] old_pos, int[] new_pos, BoardGame b) {
        System.out.println("Entra in respectAthenaAction");
        System.out.println("Differenza " +  new_pos[0] + " , " + new_pos[1] + " - " + old_pos[0] + " , " + old_pos[1] + " = " + (b.getLevelBox(new_pos) - b.getLevelBox(old_pos)));
        if ((b.getLevelBox(new_pos) - b.getLevelBox(old_pos)) > 0)
            return CommandType.ERROR;
        return CommandType.MOVE;
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