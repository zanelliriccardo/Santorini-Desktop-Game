package elements;

import it.polimi.ingsw.riccardoemelissa.Message;
import it.polimi.ingsw.riccardoemelissa.Turn;

import java.util.ArrayList;

public class Minotaur implements God {
    private boolean opponent_turn = false;
    private String type= "move";

    @Override
    public boolean CheckMoment(Worker activeWorker, Player CardOwner, String str, int[] newpos, BoardGame b, Message m) {
        if(activeWorker.GetProprietary().GetNickname()==CardOwner.GetNickname() && str.compareTo("move")==0)
        {
            if (!b.GetStateBox(newpos))
                return true;
        }
        return false;
    }

    @Override
    public boolean Power(ArrayList<Worker> worker_list,int[] newpos,BoardGame b) {
        /*
        //        POWER: "il W PUO' spostarsi anche in una box già occupata,
        //        in tal caso il W_AVVERSARIO sarebbe obbligato a spostarsi nella pos iniziale del MIO W"
        //         */

        int[] newpos_opponent=new int[]{(newpos[0]-worker_list.get(0).GetX())+newpos[0],(newpos[1]-worker_list.get(0).GetY())+newpos[1]};

        if(b.IsAPossibleMove(newpos_opponent,newpos))
        {
            worker_list.get(1).SetPosition(newpos_opponent);
            b.ChangeState(newpos_opponent,worker_list.get(1).GetProprietary().GetColor());

            b.ChangeState(worker_list.get(0).GetPosition());
            worker_list.get(0).SetPosition(newpos);
            b.ChangeState(newpos,worker_list.get(0).GetProprietary().GetColor());

            return true;
        }
        return true;

    }


}
