package elements;

import it.polimi.ingsw.riccardoemelissa.Message;

import java.io.IOException;
import java.util.ArrayList;

public class Artemis extends God {
    private boolean opponent_turn = false;

    @Override
    public boolean Move(BoardGame b, ArrayList<Worker> worker_list, int[] newpos)
    {
        ArrayList<God> opponents_action = checkOpponentCondition();
        int [] oldpos = new int[2];
        oldpos[0] = worker_list.get(0).GetX();
        oldpos[1]= worker_list.get(1).GetY();

        for (God g : opponents_action) {
            if (g.GetType().equals("move") && g.Move(b, worker_list, newpos) && b.IsAPossibleMove(newpos, worker_list.get(0).GetPosition())) {

                worker_list.get(0).SetPosition(newpos);
                b.ChangeState(newpos, worker_list.get(0).GetProprietary().GetColor());
                b.ChangeState(worker_list.get(0).GetPosition());

                if (MoveAgain())
                {
                    if(ArtemisAction(b, worker_list, GetNewPosition(), g, oldpos));
                    return true;
                }
                return true;
            }
        }
        return  false;
    }

    public boolean MoveAgain ()
    {
        //CLIENT
            return true;
    }

    public int[] GetNewPosition ()
    {
        //CLIENT
        int[] newpos = new int[2];
        newpos[0] = 0;
        newpos[1] = 1;
        return newpos;
    }



    public boolean ArtemisAction (BoardGame b, ArrayList<Worker> worker_list, int[] newpos, God g, int[] oldpos)
    {
        if (g.Move(b, worker_list, newpos) )
        {
            if(!b.GetStateBox(newpos))
                return false;
            if(b.GetLevelBox(newpos)==4)
                return false;
            if((b.GetLevelBox(newpos)-b.GetLevelBox(worker_list.get(0).GetPosition()))>1)
                return false;
            if(newpos[0] == oldpos[0] && newpos[1] == oldpos[1])
                return false;

            worker_list.get(0).SetPosition(newpos);
            b.ChangeState(newpos, worker_list.get(0).GetProprietary().GetColor());
            b.ChangeState(worker_list.get(0).GetPosition());

            return  true;
        }
        return  false;
    }
}
