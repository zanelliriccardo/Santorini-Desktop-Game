package elements.card;

import elements.BoardGame;
import elements.God;
import elements.GodCardType;
import elements.Worker;

public class Pan extends God {
    private boolean opponent_turn;
    private GodCardType type=GodCardType.MOVE;

    @Override
    public GodCardType Move (BoardGame b, Worker active_worker, int[] newpos)
    {
        int[] oldpos = active_worker.GetPosition();

        if(super.Move(b, active_worker, newpos)==GodCardType.OK)
        {
            if (b.GetLevelBox(oldpos) - b.GetLevelBox(newpos) > 1)
                return GodCardType.WIN;
        }
        return GodCardType.BUILD;
    }
}
