package elements.card;

import elements.BoardGame;
import elements.God;
import elements.Worker;

import java.util.ArrayList;

public class Pan extends God {
    private Boolean winner;
    private boolean opponent_turn;

    public Pan()
    {
        winner = false;
    }

    @Override
    public boolean Move (BoardGame b, Worker active_worker, int[] newpos)
    {
        int[] oldpos = active_worker.GetPosition();

        if(super.Move(b, active_worker, newpos))
        {
            if (b.GetLevelBox(oldpos) - b.GetLevelBox(newpos) > 2)
            SetWinner();
            return true;
        }

        else return false;
    }

    @Override
    public boolean GetOpponentTurn() {
        return opponent_turn;
    }

    public void SetWinner ()
    {
        winner = true;
    }






}
