package elements;

import java.util.ArrayList;

public abstract class God {

    //worker_list: 1 -> ACTIVE_WORKER, 2-> WORKER_AVV
    public boolean Move(BoardGame b, ArrayList<Worker> worker_list, int[] newpos, Player[] players)
    {
            int[] oldpos=worker_list.get(0).GetPosition();
            if(b.IsAPossibleMove(newpos,oldpos))
            {
                worker_list.get(0).SetPosition(newpos);
                b.ChangeState(newpos,worker_list.get(0).GetProprietary().GetColor());
                b.ChangeState(oldpos);
                return true;
            }
        return false;
    }

    public boolean Build(BoardGame b, ArrayList<Worker> worker_list, int[] pos)
    {
            int[] workerpos = worker_list.get(0).GetPosition();

            if(b.IsAPossibleBuild(pos,workerpos))
            {
                b.DoBuild(pos);
                return true;
            }
            else
                return false;
        }
    }
