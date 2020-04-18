package elements;


import it.polimi.ingsw.riccardoemelissa.App;
import it.polimi.ingsw.riccardoemelissa.Message;
import it.polimi.ingsw.riccardoemelissa.Turn;

import javax.sound.midi.VoiceStatus;
import java.util.ArrayList;

public class ApolloAction implements God {
    /*private boolean opponent_turn = false;
    private String type= "move";

     */

    //public void Apollo(){}

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
        POWER: "il W PUO' spostarsi anche in una box già occupata,
        in tal caso il W_AVVERSARIO sarebbe obbligato a spostarsi nella pos iniziale del MIO W"
          */

        worker_list.get(1).SetPosition(worker_list.get(0).GetPosition());
        worker_list.get(0).SetPosition(newpos);

        return true;
    }
}
