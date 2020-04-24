package elements.card;

import elements.BoardGame;
import elements.God;
import elements.Worker;

public class Demeter extends God {
    private boolean opponent_turn = false;
    @Override
    public boolean Build(BoardGame b, Worker activeWorker, int[] pos)
    {
        super.Build(b, activeWorker, pos);

        if(BuildAgain() && CanBuildAgain(b, pos))
            secondBuild(b, activeWorker, pos);
        return true;
    }

    public void secondBuild(BoardGame b, Worker activeWorker, int[] pos)
    {
        if(b.IsAPossibleBuild(pos,activeWorker.GetPosition()))
            b.DoBuild(pos);
    }

    public boolean BuildAgain ()
    {
        return true;
    }

    public boolean CanBuildAgain(BoardGame b, int[] last_build)
    {
        int[] pos = GetPosSecondBuild();

        if(DemeterAction(b, pos, last_build))
            return true;
        else return false;
    }

    public int[] GetPosSecondBuild ()
    {
        return new int[]{0, 0};
    }

    public boolean DemeterAction (BoardGame b, int[] pos, int[] last_build)
    {
        if(pos[0] != last_build[0] && pos[1] != last_build[1])
            return true;
        else return false;
    }
}