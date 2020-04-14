package elements;

import it.polimi.ingsw.riccardoemelissa.Turn;

public class Pan implements God {
    @Override
    public boolean CheckMoment(Player ActivePlayer,Player CardOwner,String str) {
        if(ActivePlayer.GetNickname()==CardOwner.GetNickname() && str.compareTo("build")==0);
        return true;
    }

    @Override
    public void Power(Worker worker) {

        /* POWER : se il W scende di 2 o più livelli --> VITTORIA

        CODICE:
        - salvo livello della box iniziale
        - salvo livello della nuova box
        - differenza = livello_nuova - livello_iniziale
        - se diff >= 2 --> PODIUM
         */

    }


}
