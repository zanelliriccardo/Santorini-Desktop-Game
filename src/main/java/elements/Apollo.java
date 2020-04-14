package elements;


import it.polimi.ingsw.riccardoemelissa.App;
import it.polimi.ingsw.riccardoemelissa.Turn;

import javax.sound.midi.VoiceStatus;

public class Apollo implements God {
    @Override
    public boolean CheckMoment(Player ActivePlayer,Player CardOwner,String str) {
        if(ActivePlayer.GetNickname()==CardOwner.GetNickname() && str.compareTo("move")==0);
            return true;
    }

    @Override
    public void Power(Worker worker, int[] pos) {
        /*
        POWER: "il W PUO' spostarsi anche in una box già occupata,
        in tal caso il W_AVVERSARIO sarebbe obbligato a spostarsi nella pos iniziale del MIO W"
         */



        /* CODICE MOVE:
        - Cambio la condizione FREE delle box : se non è cupola
        - Se tra le box possibili ce n'è una o più già occupate, lo comunico
         */

        // Nel caso in cui scelga una box già occupata --> SCAMBIO POSIZIONI










    }


}
