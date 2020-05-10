package it.polimi.ingsw.riccardoemelissa.elements.card;

import it.polimi.ingsw.riccardoemelissa.elements.BoardGame;
import it.polimi.ingsw.riccardoemelissa.elements.God;
import it.polimi.ingsw.riccardoemelissa.elements.GodCardType;
import it.polimi.ingsw.riccardoemelissa.elements.Worker;
import it.polimi.ingsw.riccardoemelissa.CommandType;

import java.io.Serializable;

public class Pan extends God implements Serializable {
    private boolean opponent_turn=false;
    private GodCardType type=GodCardType.MOVE;

    public Pan()
    {
        opponent_turn=false;
        type=GodCardType.MOVE;
    }
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
    public CommandType Move (BoardGame b, Worker active_worker, int[] newpos)
    {
        int[] old_position=active_worker.GetPosition();
        super.Move(b, active_worker, newpos);

        if (b.GetLevelBox(old_position) - b.GetLevelBox(newpos) > 1)
            type=GodCardType.WIN;//settare anche position

       return CommandType.BUILD;
    }
}
