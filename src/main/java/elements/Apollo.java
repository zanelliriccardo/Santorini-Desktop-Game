package elements;


import it.polimi.ingsw.riccardoemelissa.App;
import it.polimi.ingsw.riccardoemelissa.Message;

import javax.sound.midi.VoiceStatus;
import java.util.ArrayList;

public class Apollo extends God {
    private boolean opponent_turn = false;


    @Override
    public boolean Move(BoardGame b, ArrayList<Worker> worker_list, int[] newpos)
    {
        ArrayList<God> opponents_action = checkOpponentCondition();
        int n = 0;

        for(God g : opponents_action) {
            if (g.Move(b, worker_list, newpos) && ApolloAction(b, worker_list, newpos))
            {
               n++;
            }
        }

        if(n == opponents_action.size())
        {
            worker_list.get(1).SetPosition(worker_list.get(0).GetPosition());
            worker_list.get(0).SetPosition(newpos);
            return true;
        }
        return false;
    }

    @Override
    public void SetPosition(ArrayList<Worker> worker_list, int[] newpos, BoardGame b)
    {
        worker_list.get(1).SetPosition(worker_list.get(0).GetPosition());
        worker_list.get(0).SetPosition(newpos);
    }

    public boolean ApolloAction (BoardGame b, ArrayList<Worker> worker_list, int[] newpos)
    {
        if(b.GetLevelBox(newpos)==4)
            return  false;
        if(b.GetLevelBox(newpos)-b.GetLevelBox(worker_list.get(0).GetPosition()) > 1)
            return false;
        return true;
    }
}

