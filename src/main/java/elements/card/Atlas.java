package elements.card;

import elements.BoardGame;
import elements.God;
import elements.GodCardType;
import elements.Worker;

public class Atlas extends God {
    private boolean opponent_turn = false;
    private GodCardType type=GodCardType.BUILD;

    private boolean in_action=false;
    @Override
    public boolean Build(BoardGame b, Worker activeWorker, int[] pos)
    {
        //controllo su adjacent box
        if (AtlasAction(b, pos)&&BuildDome())
        {
            b.BuildDome(pos);
            return true;
        }
        else
        {
            super.Build(b,activeWorker,pos);
            return true;
        }

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

    public void setIn_action(boolean set)
    {
        in_action=set;
    }
}
