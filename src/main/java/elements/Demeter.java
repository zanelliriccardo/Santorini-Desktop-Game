package elements;

import it.polimi.ingsw.riccardoemelissa.Turn;

public class Demeter implements God {
    @Override
    public boolean CheckMoment(Player ActivePlayer,Player CardOwner,String str) {
        if(ActivePlayer.GetNickname()==CardOwner.GetNickname() && str.compareTo("build")==0);
        return true;
    }

    @Override
    public void Power(Worker worker) {
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
