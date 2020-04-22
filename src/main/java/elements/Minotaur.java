package elements;

import it.polimi.ingsw.riccardoemelissa.Message;

import java.util.ArrayList;

public class Minotaur extends God {
    private boolean opponent_turn = false;

    @Override
    public boolean Move(BoardGame b, ArrayList<Worker> worker_list, int[] newpos) {
        ArrayList<God> opponents_action = checkOpponentCondition();
        int n = 0;

        if (CheckAdjacentBox(newpos, worker_list.get(0).GetPosition()))
        {
            for (God g : opponents_action)
            {
                if (g.Move(b, worker_list, newpos) && MinotaurAction (b, worker_list, newpos))
                    n++;
            }

            if(n==opponents_action.size()) {
                if (!b.GetStateBox(newpos))
                    SetMinotaurPosition(worker_list, newpos, b);
                SetPosition(worker_list, newpos, b);
                return true;
            }
        }
        return false;
    }

    public void SetMinotaurPosition (ArrayList<Worker> worker_list, int[] newpos, BoardGame b)
    {
        int[] newpos_opponent = new int[]{(newpos[0] - worker_list.get(0).GetX()) + newpos[0], (newpos[1] - worker_list.get(0).GetY()) + newpos[1]};

            b.GetOccupant(newpos).SetPosition(newpos_opponent);
            b.ChangeState(newpos_opponent, b.GetOccupant(newpos).GetProprietary().GetColor());

            b.ChangeState(worker_list.get(0).GetPosition());
            worker_list.get(0).SetPosition(newpos);
            b.ChangeState(newpos, worker_list.get(0).GetProprietary().GetColor());
    }

    private boolean MinotaurAction(BoardGame b, ArrayList<Worker> worker_list, int[] newpos) {
        int[] newpos_opponent=new int[]{(newpos[0]-worker_list.get(0).GetX())+newpos[0],(newpos[1]-worker_list.get(0).GetY())+newpos[1]};

        if (b.GetOccupant(newpos).GetProprietary().GetNickname().equals(worker_list.get(0).GetProprietary().GetNickname()))
            return false;
        if(b.GetLevelBox(newpos)==4)
            return  false;
        if(b.GetLevelBox(newpos)-b.GetLevelBox(worker_list.get(0).GetPosition()) > 1)
            return false;
        if (!CheckAdjacentBox(newpos_opponent, newpos))
            return false;


        return true;
    }
}