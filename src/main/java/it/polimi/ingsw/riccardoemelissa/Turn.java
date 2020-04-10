package it.polimi.ingsw.riccardoemelissa;

import elementi.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Turn {
    private Player player;
    private Worker worker;

    public Player GetPlayer (Player p)
    {
       return player = p;
    }

    public void GetWorkerInAction (Worker w)
    {
        worker = w;
    }

    public boolean MoveAccepted(int x, int y)
    {
        
    }

    public void Move (Worker worker)
    {
        //CHECK

       int[] move_accepted = new int[16];
       int i=0;

       int X = worker.GetX();
       int Y = worker.GetY();

       for ( int x = -1; x <= 1; x++)
       {
           for (int y = -1; y <= 1; y++)
           {
               if (MoveAccepted (x,y))
               {
                   move_accepted[i]=x;
                   move_accepted[i+1]=y;
                   i+=2;
               }
           }
       }


    }
}
