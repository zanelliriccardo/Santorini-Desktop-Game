package elements;

import it.polimi.ingsw.riccardoemelissa.Message;

import java.util.ArrayList;

public class AthenaAction implements God {
    private boolean opponent_turn = true;
    private String type= "move";

    private boolean ActivePower=false;

    @Override
    public boolean CheckMoment(Worker activeWorker, Player CardOwner, String str, int[] newpos, BoardGame b, Message m) {
        if(activeWorker.GetProprietary().GetNickname()!=CardOwner.GetNickname() && str.compareTo("move")==0)
        {
            opponent_turn=true;
            return true;
        }
        else if(activeWorker.GetProprietary().GetNickname()==CardOwner.GetNickname() && str.compareTo("build")==0)
        {
            opponent_turn=false;
            return true;
        }

        return false;
    }

    @Override
    public boolean Power(ArrayList<Worker> worker_list, int[] newpos, BoardGame b) {
        int[] oldpos= worker_list.get(0).GetPosition();

        if(opponent_turn&&ActivePower)
        {
            if(b.GetLevelBox(newpos)<=b.GetLevelBox(oldpos))
                return true;
            return false;
        }
        else if(!opponent_turn)
        {
            ActivePower=(b.GetLevelBox(newpos)>b.GetLevelBox(oldpos))&&(newpos[0]!=oldpos[0])&&(newpos[1]!=oldpos[1]);
        }
        return true;
    }


}
