package elements;

import it.polimi.ingsw.riccardoemelissa.Message;
import it.polimi.ingsw.riccardoemelissa.Turn;

import java.util.ArrayList;

public class Prometheus implements God {
    private boolean opponent_turn = false;
    private String type= "move";

    @Override
    public boolean CheckMoment(Worker activeWorker, Player CardOwner, String str, int[] newpos, BoardGame b, Message m) {
        if(activeWorker.GetProprietary().GetNickname()==CardOwner.GetNickname() && str.compareTo("move")==0);
        {
            if (m.UsePower()) {
                ArrayList<Worker> worker_list = new ArrayList<Worker>();
                worker_list.add(activeWorker);

                m.WhereBuild();

                while(true) {
                    newpos = m.GetInputPosition();

                    if(Math.abs(newpos[0]-worker_list.get(0).GetX())>1 && Math.abs(newpos[1]-worker_list.get(1).GetY())>1 && b.GetLevelBox(newpos)==4)
                        b.DoBuild(newpos);

                    m.WhereMove();

                    while(true) {
                        newpos = m.GetInputPosition();

                        if (!b.GetStateBox(newpos) && !(b.GetLevelBox(newpos) == 4) && (b.GetLevelBox(newpos) - b.GetLevelBox(activeWorker.GetPosition())) <=0) {
                                break;
                        }
                    }
                        break;
                }

                Power(worker_list, newpos, b);
            }
        }
        return false;
    }

    @Override
    public boolean Power(ArrayList<Worker> worker_list,int[] newpos,BoardGame b) {
        /*
        POWER: se il W NON sale di livello nella mossa,
        PUO costruire sia prima sia dopo averla effettuata */

        int[] oldpos=worker_list.get(0).GetPosition();

        worker_list.get(0).SetPosition(newpos);
        b.ChangeState(newpos,worker_list.get(0).GetProprietary().GetColor());
        b.ChangeState(oldpos);
        return true;
    }


}
