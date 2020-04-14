package elements;

import it.polimi.ingsw.riccardoemelissa.Turn;

import java.util.ArrayList;

public class Hephaestus implements God {
    private boolean opponent_turn = false;
    private String type= "build";

    @Override
    public boolean CheckMoment(Player ActivePlayer,Player CardOwner,String str) {
        if(ActivePlayer.GetNickname()==CardOwner.GetNickname() && str.compareTo("build")==0);
        return true;
    }

    @Override
    public void Power(Worker worker, ArrayList<int[]> possiblemoves) {

        /*
        POWER: "il W PUO' costruire due volte nella stessa box,
        NO CUPOLA

        CODICE:
        - metodo build
        - se LEVEL_BOX_SCELTA <= 2 --> message : "Vuoi costruire ancora?"
        - SI --> aggiungo un blocco alla box scelta
         */

    }


}
