package it.polimi.ingsw.riccardoemelissa.elements.card;

import it.polimi.ingsw.riccardoemelissa.elements.BoardGame;
import it.polimi.ingsw.riccardoemelissa.elements.God;
import it.polimi.ingsw.riccardoemelissa.elements.GodCardType;
import it.polimi.ingsw.riccardoemelissa.elements.Worker;
import it.polimi.ingsw.riccardoemelissa.CommandType;

import java.io.Serializable;

public class Atlas extends God implements Serializable {
    private boolean opponent_turn = false;
    private GodCardType type=GodCardType.MOVE;

    private boolean in_action=false;

    public Atlas()
    {
        opponent_turn=false;
        type=GodCardType.MOVE;
    }
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
        if (in_action)
        {
            b.BuildDome(pos);
            type=GodCardType.ENDTURN;
            return CommandType.BUILD;
        }
        else
        {
            return super.Build(b,activeWorker,pos);
        }
    }
}
