package elements;

import it.polimi.ingsw.riccardoemelissa.Turn;

public class Minotaur implements God {
    @Override
    public boolean CheckMoment(Player ActivePlayer,Player CardOwner,Object obj) {
        if(ActivePlayer.GetNickname()==CardOwner.GetNickname() && obj.getClass().equals(Turn.class));
        return true;
    }

    @Override
    public void Power(Worker worker) {

        /*
        POWER: il W PUO' spostarsi nella box di un W_avversario
        SE la box successiva (box+1), nella stessa direzione, è FREE (non importa a che livello sia)
        il W_avversario va spostato nella box+1

        CODICE:
        - metodo MOVE MODIFICATO -> una box è FREE anche nel caso in cui sia già occupata SSE la box+1 è libera
         */

    }


}
