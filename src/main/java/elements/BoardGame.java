package elements;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

public class BoardGame extends CustomObservable implements Serializable {
    private Box[][] Board;

    public BoardGame(Box[][] boxes){
        this.Board = boxes;
        custom_notifyAll();
    }

    public boolean GetStateBox (int[] pos)
    {
        return Board[pos[0]][pos[1]].GetState();
    }

    public boolean GetStateBox (int i,int j)
    {
        return Board[i][j].GetState();
    }

    public int GetLevelBox (int[] pos)
    {
        return Board[pos[0]][pos[1]].GetLevel();
    }

    public int GetLevelBox(int i, int j)
    {
        return Board[i][j].GetLevel();
    }

    public boolean IsAPossibleMove(int[] newpos,int[] oldpos)
    {
        if(!Board[newpos[0]][newpos[1]].GetState())
            return false;
        if(Board[newpos[0]][newpos[1]].GetLevel()==4)
            return false;
        if((GetLevelBox(newpos)-GetLevelBox(oldpos))>1)
            return false;

        return true;
    }

    public void ChangeState(int[] pos)
    {
        Board[pos[0]][pos[1]].ChangeState();
        custom_notifyAll();
    }

    public void ChangeState(int[] pos, String color)
    {
        Board[pos[0]][pos[1]].ChangeState(color);
        custom_notifyAll();
    }

    public boolean IsAPossibleBuild(int[] pos, int[] workerpos)
    {
        if(Math.abs(pos[0]-workerpos[0])>1)
            return false;
        if(Math.abs(pos[1]-workerpos[1])>1)
            return false;
        if(Board[pos[0]][pos[1]].GetLevel()==4)
            return false;
        return true;
    }

    public void DoBuild(int[] pos)
    {
        Board[pos[0]][pos[1]].Build();
        custom_notifyAll();
    }

    public boolean IsABlockedWorker(int[] pos)
    {
        for(int i=pos[0]-1;i<=pos[0]+1;i++)
            for(int j=pos[1]-1;j<pos[1]+1;j++)
            {
                if(IsAPossibleMove(new int[]{i, j},pos))
                    return false;
            }
        return true;
    }

    public Worker GetOccupant(int[] pos)
    {
        return Board[pos[0]][pos[1]].GetOccupant();
    }

    public void setOccupant(int[] pos, Worker worker)
    {
        Board[pos[0]][pos[1]].SetOccupant(worker);
        custom_notifyAll();
    }

    public void BuildDome(int[] pos)
    {
        Board[pos[0]][pos[1]].SetDome();
        custom_notifyAll();
    }

    public Box[][] GetBoard()
    {
        return Board;
    }

    public boolean IsAdjacentBox(int[] workerpos, int[] newpos)
    {
        return AdjacentBox(workerpos).contains(newpos);
    }

    public ArrayList<int[]> AdjacentBox (int[] worker_pos)
    {
        ArrayList<int[]> adj_boxes = new ArrayList<>();
        int[] pos = new int[2];

        for (int x = worker_pos[0] - 1; x <= worker_pos[0] + 1; x++) {
            for (int y = worker_pos[1] - 1; y <= worker_pos[1] + 1; y++) {
                if (x == worker_pos[0] && y == worker_pos[1])
                    continue;

                if (x > 4 || x < 0)
                    continue;

                if (y > 4 || y < 0)
                    continue;

                pos[0] = x;
                pos[1] = y;

                adj_boxes.add(pos);
            }
        }
        return adj_boxes;
    }

}

