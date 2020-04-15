package elements;

import it.polimi.ingsw.riccardoemelissa.Message;
import it.polimi.ingsw.riccardoemelissa.Turn;

import java.util.ArrayList;

public class Artemis implements God {
    private boolean opponent_turn = false;
    private String type= "move";
    private int[] oldpos=new int[2];
    private boolean ActivePower=false;
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
                Power(worker_list,newpos,b);
            }

        }
        return false;
    }

    @Override
    public boolean Power(ArrayList<Worker> worker_list,int[] newpos,BoardGame b) {

        int[] oldpos=worker_list.get(0).GetPosition();
        if(b.IsAPossibleMove(newpos,oldpos))
        {
            worker_list.get(0).SetPosition(newpos);
            b.ChangeState(newpos,worker_list.get(0).GetProprietary().GetColor());
            b.ChangeState(oldpos);
            return true;
        }
        else
            return false;


    }


}
