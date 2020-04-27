package elements.card;

import elements.BoardGame;
import elements.God;
import elements.GodCardType;
import elements.Worker;

import java.util.ArrayList;

public class Demeter extends God {
    private boolean opponent_turn = false;
    private GodCardType type=GodCardType.BUILD;

    private int[] old_position=null;
    private boolean first_build=true;


    private boolean in_action=false;
    @Override
    public GodCardType Build(BoardGame b, Worker active_Worker, int[] pos)
    {
        if(in_action)
        {
            old_position = pos;
            super.Build(b,active_Worker,pos);
            in_action=false;
            this.type=GodCardType.BUILD;
            return GodCardType.OK;
        }
        else
        {
            super.Build(b, active_Worker, pos);
            type=GodCardType.ENDTURN;
            return GodCardType.OK;
        }
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

    public void setIn_action(boolean set)
    {
        in_action=set;
    }

    @Override
    public ArrayList<int[]> adjacentBoxNotOccupiedNotDome(BoardGame b, int[] worker_pos) {
        ArrayList<int[]> possibleBox=super.adjacentBoxNotOccupiedNotDome(b, worker_pos);
        if(!in_action&&old_position!=null&&type==GodCardType.BUILD)
            possibleBox.remove(old_position);

        return possibleBox;
    }
}