package elements;

import it.polimi.ingsw.riccardoemelissa.Message;

import java.util.ArrayList;

public class Artemis extends God {
    private boolean opponent_turn = false;

    @Override
    public boolean Move(BoardGame b, ArrayList<Worker> worker_list, int[] newpos, Player[] players){
        return true;

    }
}
