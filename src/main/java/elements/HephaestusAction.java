package elements;

import it.polimi.ingsw.riccardoemelissa.Message;
import it.polimi.ingsw.riccardoemelissa.Turn;

import java.util.ArrayList;

public class HephaestusAction implements God {
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
        POWER: "il W PUO' costruire due volte nella stessa box,
        NO CUPOLA

        CODICE:
        - metodo build
        - se LEVEL_BOX_SCELTA <= 2 --> message : "Vuoi costruire ancora?"
        - SI --> aggiungo un blocco alla box scelta
         */
        return true;

    }


}
