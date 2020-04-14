package elements;

import it.polimi.ingsw.riccardoemelissa.Turn;

public class Athena implements God {

    private boolean ActivePower=false;

    @Override
    public boolean CheckMoment(Player ActivePlayer,Player CardOwner,String str) {
        if(ActivePlayer.GetNickname()!=CardOwner.GetNickname() && str.compareTo("move")==0);
        return true;
    }

    @Override
    public void Power(Worker worker) {

    }


}
