package elements;

import it.polimi.ingsw.riccardoemelissa.Message;
import it.polimi.ingsw.riccardoemelissa.Turn;

import java.util.ArrayList;

public class Artemis implements God {
    private boolean opponent_turn = false;
    private String type= "move";
    private int[] oldpos=new int[2];
    private boolean ActivePower=false;
    private Box[][] Board;
    @Override
    public boolean CheckMoment(Worker activeWorker, Player CardOwner, String str, int[] newpos, BoardGame b, Message m)
    {
        if(activeWorker.GetProprietary().GetNickname()==CardOwner.GetNickname() && str.compareTo("build")==0)
        {
            oldpos[0] = newpos[0];
            oldpos[1] = newpos[1];
            ActivePower=m.SecondMove();
            if(ActivePower)
            {
                ArrayList<Worker> worker_list=new ArrayList<Worker>();
                worker_list.add(activeWorker);

                m.WhereMove();

                while(true)
                {
                newpos = m.GetInputPosition();

                if(!b.GetStateBox(newpos) && !(b.GetLevelBox(newpos) == 4) && (b.GetLevelBox(newpos) - b.GetLevelBox(oldpos)) > 1 )
                {
                    if (newpos[0]!= worker_list.get(0).GetX() && newpos[1]!= worker_list.get(1).GetY())
                        break;
                }
                }
                Power(worker_list,newpos,b);
            }
        }
        return false;
    }

    @Override
    public boolean Power(ArrayList<Worker> worker_list,int[] newpos,BoardGame b) {

        int[] oldpos=worker_list.get(0).GetPosition();

        worker_list.get(0).SetPosition(newpos);
        b.ChangeState(newpos,worker_list.get(0).GetProprietary().GetColor());
        b.ChangeState(oldpos);
        return true;
    }
}
