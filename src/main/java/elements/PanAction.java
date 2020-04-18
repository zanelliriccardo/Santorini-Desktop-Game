package elements;

import it.polimi.ingsw.riccardoemelissa.Message;
import it.polimi.ingsw.riccardoemelissa.Turn;

import java.util.ArrayList;

public class PanAction implements God {
    @Override
    public boolean CheckMoment(Worker activeWorker, Player CardOwner, String str, int[] newpos, BoardGame b, Message m) {
        if(activeWorker.GetProprietary().GetNickname()==CardOwner.GetNickname() && str.compareTo("build")==0);
        return true;
    }

    @Override
    public boolean Power(ArrayList<Worker> worker_list,int[] newpos,BoardGame b) {

        /* POWER : se il W scende di 2 o più livelli --> VITTORIA

        CODICE:
        - salvo livello della box iniziale
        - salvo livello della nuova box
        - differenza = livello_nuova - livello_iniziale
        - se diff >= 2 --> PODIUM
         */
        return true;

    }


}
