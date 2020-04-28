package elements.card;

import elements.BoardGame;
import elements.God;
import elements.GodCardType;
import elements.Worker;
import it.polimi.ingsw.riccardoemelissa.CommandType;

public class Atlas extends God {
    private boolean opponent_turn = false;
    private GodCardType type=GodCardType.MOVE;

    private boolean in_action=false;
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
