package elements;

import it.polimi.ingsw.riccardoemelissa.Turn;

import java.util.ArrayList;

public class Minotaur implements God {
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
        POWER: il W PUO' spostarsi nella box di un W_avversario
        SE la box successiva (box+1), nella stessa direzione, è FREE (non importa a che livello sia)
        il W_avversario va spostato nella box+1

        CODICE:
        - metodo MOVE MODIFICATO -> una box è FREE anche nel caso in cui sia già occupata SSE la box+1 è libera
         */

    }


}
