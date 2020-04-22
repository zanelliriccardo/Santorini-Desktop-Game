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

        if(CheckAdjacentBox(newpos, worker_list.get(0).GetPosition())) {
            for (God g : opponents_action) {
                if (g.Move(b, worker_list, newpos) && ApolloAction(b, worker_list, newpos))
                    n++;
            }

            if (n == opponents_action.size()) {
                if (!b.GetStateBox(newpos))
                    SetApolloPosition(worker_list, newpos, b);
                SetPosition(worker_list, newpos, b);
                return true;
            }
        }
        return false;
    }

    public void SetApolloPosition(ArrayList<Worker> worker_list, int[] newpos, BoardGame b)
    {
        worker_list.get(1).SetPosition(worker_list.get(0).GetPosition());
        worker_list.get(0).SetPosition(newpos);
    }

    public boolean ApolloAction (BoardGame b, ArrayList<Worker> worker_list, int[] newpos)
    {
        if (b.GetOccupant(newpos).GetProprietary().GetNickname().equals(worker_list.get(0).GetProprietary().GetNickname()))
            return false;
        if(b.GetLevelBox(newpos)==4)
            return  false;
        if(b.GetLevelBox(newpos)-b.GetLevelBox(worker_list.get(0).GetPosition()) > 1)
            return false;
        return true;
    }
}

