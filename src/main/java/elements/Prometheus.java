package elements;

import it.polimi.ingsw.riccardoemelissa.Turn;

import java.util.ArrayList;

public class Prometheus implements God {
    private boolean opponent_turn = false;
    private String type= "move";

    @Override
    public boolean CheckMoment(Player ActivePlayer,Player CardOwner,String str) {
        if(ActivePlayer.GetNickname()==CardOwner.GetNickname() && str.compareTo("move")==0);
        return true;
    }

    @Override
    public void Power(Worker worker, ArrayList<int[]> possiblemoves) {

        /*
        POWER: se il W NON sale di livello nella mossa,
        PUO costruire sia prima sia dopo averla effettuata

        CODICE:
        - Message : Vuoi costruire prima della mossa

        - risp: SI
        -metodo costruction
        -metodo MOVE MODIFICATO --> condizione su FREE rimane uguale, level_NEW deve essere <= level_W
        -metodo costruction

        - risp: NO --> gioco classico

         */

    }


}
