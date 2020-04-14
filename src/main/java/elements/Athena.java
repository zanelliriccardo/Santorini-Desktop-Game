package elements;

import it.polimi.ingsw.riccardoemelissa.Turn;

import java.util.ArrayList;

public class Athena implements God {
    private boolean opponent_turn = true;
    private String type= "move";

    private boolean ActivePower=false;

    @Override
    public boolean CheckMoment(Player ActivePlayer,Player CardOwner,String str) {
        if(ActivePlayer.GetNickname()!=CardOwner.GetNickname() && str.compareTo("move")==0);
        return true;
    }

    @Override
    public void Power(Worker worker, ArrayList<int[]> possiblemoves) {

    }


}
