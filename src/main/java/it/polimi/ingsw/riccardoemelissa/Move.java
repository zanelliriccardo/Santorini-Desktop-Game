package it.polimi.ingsw.riccardoemelissa;
import elements.*;

public class Move extends Turn {
    private Worker ActiveWorker;
    private int[] NewPos;

    public Move(Worker w)
    {
        ActiveWorker=w;
    }

    public void SetNewPos(int x,int y)
    {
        NewPos[0]=x;
        NewPos[1]=y;
    }

    public void DoMove(Worker w,int[] pos)
    {

    }
}
