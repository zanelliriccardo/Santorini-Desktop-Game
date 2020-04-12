package elements;

import it.polimi.ingsw.riccardoemelissa.Turn;

public class Hephaestus implements God {
    @Override
    public boolean CheckMoment(Player ActivePlayer,Player CardOwner,Object obj) {
        if(ActivePlayer.GetNickname()==CardOwner.GetNickname() && obj.getClass().equals(Turn.class));
        return true;
    }

    @Override
    public void Power(Worker worker) {

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
