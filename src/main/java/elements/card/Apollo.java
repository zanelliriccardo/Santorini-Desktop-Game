package elements.card;


import elements.BoardGame;
import elements.God;
import elements.Worker;

import java.util.ArrayList;

public class Apollo extends God {
    private boolean opponent_turn = false;

    @Override
    public boolean Move(BoardGame b, Worker active_worker, int[] newpos) {
        if (ApolloAction(b, active_worker, newpos))
        {
            if (!b.GetStateBox(newpos))
                SetApolloPosition(active_worker, newpos, b);
            else SetPosition(active_worker, active_worker.GetPosition(), newpos, b);
            return true;
        }
        else return false;
    }

    public void SetApolloPosition(Worker active_worker, int[] newpos, BoardGame b)
    {
        b.GetOccupant(newpos).SetPosition(active_worker.GetPosition());
        active_worker.SetPosition(newpos);
    }

    public boolean ApolloAction (BoardGame b, Worker active_worker, int[] newpos)
    {
        if (b.GetOccupant(newpos).GetProprietary().GetNickname().equals(active_worker.GetProprietary().GetNickname()))
            return false;
        if(b.GetLevelBox(newpos)==4)
            return  false;
        if(b.GetLevelBox(newpos)-b.GetLevelBox(active_worker.GetPosition()) > 1)
            return false;
        else return true;
    }
}

