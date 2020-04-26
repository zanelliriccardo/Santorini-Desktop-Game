package elements.card;

import elements.BoardGame;
import elements.God;
import elements.Worker;

import java.util.ArrayList;

public class Minotaur extends God {
    private boolean opponent_turn = false;
    private boolean activable=false;

    @Override
    public boolean Move(BoardGame b, Worker active_worker, int[] newpos) {
        if(MinotaurAction (b, active_worker, newpos))
        {
            if (!b.GetStateBox(newpos))
                SetMinotaurPosition(active_worker, newpos, b);

            else SetPosition(active_worker, active_worker.GetPosition(), newpos, b);
            return true;
        }
        else return false;
    }

    public void SetMinotaurPosition (Worker active_worker, int[] newpos, BoardGame b)
    {
        int[] newpos_opponent = new int[]{(newpos[0] - active_worker.GetX()) + newpos[0], (newpos[1] - active_worker.GetY()) + newpos[1]};

            b.GetOccupant(newpos).SetPosition(newpos_opponent);
            b.ChangeState(newpos_opponent, b.GetOccupant(newpos).GetProprietary().GetColor());

            b.ChangeState(active_worker.GetPosition());
            active_worker.SetPosition(newpos);
            b.ChangeState(newpos, active_worker.GetProprietary().GetColor());
    }

    private boolean MinotaurAction(BoardGame b, Worker active_worker, int[] newpos) {
        int[] newpos_opponent=new int[]{(newpos[0]-active_worker.GetX())+newpos[0],(newpos[1]-active_worker.GetY())+newpos[1]};

        if (b.GetOccupant(newpos).GetProprietary().GetNickname().equals(active_worker.GetProprietary().GetNickname()))
            return false;
        if(b.GetLevelBox(newpos)==4)
            return  false;
        if(b.GetLevelBox(newpos)-b.GetLevelBox(active_worker.GetPosition()) > 1)
            return false;

        else return true;
    }
}