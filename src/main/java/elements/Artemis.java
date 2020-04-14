package elements;

import it.polimi.ingsw.riccardoemelissa.Turn;

import java.util.ArrayList;

public class Artemis implements God {
    private boolean opponent_turn = false;
    private String type= "move";

    @Override
    public boolean CheckMoment(Player ActivePlayer,Player CardOwner,String str) {
        if(ActivePlayer.GetNickname()==CardOwner.GetNickname() && str.compareTo("move")==0);
        return true;
    }

    @Override
    public void Power(Worker worker, ArrayList<int[]> possiblemoves) {
        /* POWER : il worker può spostarsi 2 volte,
        MA NO RETROCESSIONE A POS INIZIALE
         */

        int [] initial_position = new int [2]; //inizializzo un array per salvarmi la pos iniziale del W
        initial_position[0] = worker.GetX();
        initial_position[1] = worker.GetY();

        //metodo : CODICE MOVE CLASSICO

        //metodo: chiedo se vuole muoversi ancora

        //se SI, richiamo metodo move classico, ESCLUDENDO LA POS INIZIALE

    }


}
