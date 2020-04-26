package elements.card;

import elements.BoardGame;
import elements.God;
import elements.Worker;

public class Atlas extends God {
    private boolean opponent_turn = false;

    @Override
    public boolean Build(BoardGame b, Worker activeWorker, int[] pos)
    {
        if (AtlasAction(b, pos))
        {
            if(BuildDome())
                b.BuildDome(pos);

            else b.DoBuild(pos);

            return true;
        }
        else return false;
    }

    @Override
    public boolean GetOpponentTurn() {
        return opponent_turn;
    }

    public boolean BuildDome () //messaggio client
    {
        return true;
    }

    public boolean AtlasAction(BoardGame b, int[] pos)
    {
        if(b.GetLevelBox(pos) < 4)
            return true;
        else return false;
    }


}
