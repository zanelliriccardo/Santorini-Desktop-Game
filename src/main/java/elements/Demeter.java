package elements;

import it.polimi.ingsw.riccardoemelissa.Message;
import it.polimi.ingsw.riccardoemelissa.Turn;

import java.util.ArrayList;

public class Demeter implements God {
    private boolean opponent_turn = false;
    private String type= "build";

    @Override
    public boolean CheckMoment(Worker activeWorker, Player CardOwner, String str, int[] newpos, BoardGame b, Message m) {
        if(activeWorker.GetProprietary().GetNickname()==CardOwner.GetNickname() && str.compareTo("build")==0);
        return true;
    }

    @Override
    public boolean Power(ArrayList<Worker> worker_list,int[] newpos,BoardGame b) {
        /*
        POWER: "il W PUO' costruire una volta in più,
        NON NELLA STESSA BOX
         */

        /*
        CODICE:
        -metodo build
        -message: "Vuoi costruire ancora?"
        -se risp = SI --> metodo build, ESCLUDENDO DALLE BOX POSSIBILI QUELLA IN CUI HA COSTRUITO LA PRIMA VOLTA
         */
        return true;

    }


}
