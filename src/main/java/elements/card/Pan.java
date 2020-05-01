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
        int[] old_position=active_worker.GetPosition();
        super.Move(b, active_worker, newpos);

        if (b.GetLevelBox(old_position) - b.GetLevelBox(newpos) > 1)
            type=GodCardType.WIN;//settare anche position

       return CommandType.BUILD;
    }
}
