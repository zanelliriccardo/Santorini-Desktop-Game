package elements.card;

import elements.BoardGame;
import elements.God;
import elements.Worker;

import java.util.ArrayList;

public class Artemis extends God {
    private boolean opponent_turn = false;

    @Override
    public boolean Move(BoardGame b, Worker active_worker, int[] newpos)
    {
        int[] oldpos =active_worker.GetPosition();

        if (b.IsAPossibleMove(newpos, oldpos))
        {
            SetPosition(active_worker, active_worker.GetPosition(), newpos, b);

            if(MoveAgain() && CanMoveAgain())
            ArtemisAction(b, active_worker, oldpos);

            return true;
        }
        else return false;
    }


    /**
     * This method asks to player if he wants to move again the active worker
     * @return
     */
    public boolean MoveAgain ()
    {
        //CLIENT
            return true;
    }

    /**
     * This method checks if the new position is allow
     * @return
     */
    public boolean CanMoveAgain()
    {
        return true;
    }

    public int[] GetNewPosition ()
    {
        //CLIENT
        int[] newpos = new int[2];
        newpos[0] = 0;
        newpos[1] = 1;
        return newpos; //va controllata in board game, nel caso in cui non sia consentita,
    }

    public void ArtemisAction (BoardGame b, Worker active_worker, int[] oldpos)
    {
        int[] newpos = GetNewPosition();

        if (!b.GetStateBox(newpos))
            return;
        if (b.GetLevelBox(newpos) == 4)
            return;
        if ((b.GetLevelBox(newpos) - b.GetLevelBox(active_worker.GetPosition())) > 1)
            return;
        if (newpos[0] == oldpos[0] && newpos[1] == oldpos[1])
            return;

        else
        {
            SetPosition(active_worker, active_worker.GetPosition(), newpos, b);
        }
    }
}
