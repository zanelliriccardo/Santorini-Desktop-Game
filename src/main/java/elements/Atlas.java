package elements;

import it.polimi.ingsw.riccardoemelissa.Turn;

public class Atlas implements God {
    @Override
    public boolean CheckMoment(Player ActivePlayer,Player CardOwner,String str) {
        if(ActivePlayer.GetNickname()==CardOwner.GetNickname() && str.compareTo("build")==0);
        return true;
    }

    @Override
    public void Power(Worker worker) {

    }


}
