package elements;

import it.polimi.ingsw.riccardoemelissa.Turn;

import java.util.ArrayList;

public class Demeter implements God {
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
        POWER: "il W PUO' costruire una volta in più,
        NON NELLA STESSA BOX
         */

        /*
        CODICE:
        -metodo build
        -message: "Vuoi costruire ancora?"
        -se risp = SI --> metodo build, ESCLUDENDO DALLE BOX POSSIBILI QUELLA IN CUI HA COSTRUITO LA PRIMA VOLTA
         */

    }


}
