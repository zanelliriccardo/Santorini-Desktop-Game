package it.polimi.ingsw.riccardoemelissa;
import elements.*;

import java.io.PrintWriter;
import java.util.Scanner;

public class Move extends Turn {
    private Worker ActiveWorker;
    private int[] NewPos;
    private Message m=new Message();
    public Move(Worker w)
    {
        ActiveWorker=w;
    }

    public void SetNewPos(int x,int y)
    {
        NewPos[0]=x;
        NewPos[1]=y;
    }

    public int[] DoMove(Scanner in, PrintWriter out)
    {
        int[] pos=new int[2];

        while (true) {
            m.PositioningX(out);
            try {
                pos[0] = Integer.parseInt(in.nextLine());
            } catch (Exception ex) {
                m.Verification(out);
                continue;
            }
            break;
        }

        while (true) {
            m.PositioningY(out);
            try {
                pos[1] = Integer.parseInt(in.nextLine());
            } catch (Exception ex) {
                m.Verification(out);
                continue;
            }
            break;
        }

        return pos;
    }
}
