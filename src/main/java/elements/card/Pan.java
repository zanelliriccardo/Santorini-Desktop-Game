package elements.card;

import elements.BoardGame;
import elements.God;
import elements.GodCardType;
import elements.Worker;
import it.polimi.ingsw.riccardoemelissa.CommandType;

public class Pan extends God {
    private boolean opponent_turn=false;
    private GodCardType type=GodCardType.MOVE;

    @Override
    public CommandType Move (BoardGame b, Worker active_worker, int[] newpos)
    {
        if (b.GetLevelBox(active_worker.GetPosition()) - b.GetLevelBox(newpos) > 1)
            return CommandType.WIN;

       return super.Move(b, active_worker, newpos);
    }
}
