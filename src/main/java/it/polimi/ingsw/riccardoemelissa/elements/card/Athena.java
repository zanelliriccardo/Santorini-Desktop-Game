package it.polimi.ingsw.riccardoemelissa.elements.card;

import it.polimi.ingsw.riccardoemelissa.elements.BoardGame;
import it.polimi.ingsw.riccardoemelissa.elements.God;
import it.polimi.ingsw.riccardoemelissa.elements.GodCardType;
import it.polimi.ingsw.riccardoemelissa.elements.Worker;
import it.polimi.ingsw.riccardoemelissa.CommandType;

import java.io.Serializable;

public class Athena extends God implements Serializable {
    private boolean opponent_turn=true;
    private GodCardType type=GodCardType.MOVE;
    private boolean in_action=false;

    public Athena()
    {
        super();
    }


    public boolean SetInAction(int[] old_pos, int[] new_pos, BoardGame b) {
        if ((b.GetLevelBox(new_pos) - b.GetLevelBox(old_pos)) == 1)
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
    public CommandType Move(BoardGame b, Worker active_worker, int[] newpos)
    {
        if (active_worker.GetProprietary().GetGodCard() instanceof Athena)
        {
            in_action = SetInAction(active_worker.GetPosition(), newpos, b);
            super.Move(b, active_worker, newpos);
        }
        else if (in_action)
        {
            return RespectAthenaAction(active_worker.GetPosition(), newpos, b);
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
        if ((b.GetLevelBox(new_pos) - b.GetLevelBox(old_pos)) < 1)
            return CommandType.ERROR;
        return CommandType.MOVE;
    }

    @Override
    public void resetCard(GodCardType move) {
        type=move;
    }
}