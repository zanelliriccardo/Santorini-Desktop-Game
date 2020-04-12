package it.polimi.ingsw.riccardoemelissa;

import elements.*;

public class Turn {
    private Player player;
    private Worker worker;

    public void SetPlayer (Player p)
    {
        player = p;
    }

    public void SetWorkerInAction (Worker w)
    {
        worker = w;
    }

    public boolean MoveAccepted(int x, int y)
    {
        return false;
    }

    public void Move (Worker worker)
    {
        //CHECK

       int[][] move_accepted = new int[8][2];
       int i=0;

       int workerX = worker.GetX();
       int workerY = worker.GetY();

       for ( int x = -1; x <= 1; x++)
       {
           for (int y = -1; y <= 1; y++,i++)
           {
               if(x==0&&y==0)
                   continue;
               if (MoveAccepted (x+workerX,y+workerY))
               {
                   move_accepted[i][0]=x+workerX;
                   move_accepted[i][1]=y+workerY;
               }
           }
       }


    }
}
