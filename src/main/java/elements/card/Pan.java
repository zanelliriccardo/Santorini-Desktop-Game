package elements.card;

import elements.BoardGame;
import elements.God;
import elements.Worker;

import java.util.ArrayList;

public class Pan extends God {
    private boolean opponent_turn;
    private boolean activable=false;

    @Override
    public boolean Move (BoardGame b, Worker active_worker, int[] newpos)
    {
        int[] oldpos = active_worker.GetPosition();

        if(super.Move(b, active_worker, newpos))
        {
            if (b.GetLevelBox(oldpos) - b.GetLevelBox(newpos) > 1)
            b.setActivePlayer(null);
            return true;
        }

        else return false;
    }
}
