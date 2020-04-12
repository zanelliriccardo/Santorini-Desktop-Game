package elements;


import it.polimi.ingsw.riccardoemelissa.Turn;

public class Apollo implements God {
    @Override
    public boolean CheckMoment(Player ActivePlayer,Player CardOwner,Object obj) {
        if(ActivePlayer.GetNickname()==CardOwner.GetNickname() && obj.getClass().equals(Turn.class));
            return true;
    }

    @Override
    public void Power(Worker worker) {
        /*
        POWER: "il W PUO' spostarsi anche in una box già occupata,
        in tal caso il W_AVVERSARIO sarebbe obbligato a spostarsi nella pos iniziale del MIO W"
         */

        int [] initial_position = new int [2]; //inizializzo un array per salvarmi la pos iniziale del W
        initial_position[0] = worker.GetX();
        initial_position[1] = worker.GetY();

        /* CODICE MOVE:
        - Cambio la condizione FREE delle box : se non è cupola
        - Se tra le box possibili ce n'è una o più già occupate, lo comunico
         */

        // Nel caso in cui scelga una box già occupata --> SCAMBIO POSIZIONI










    }


}
