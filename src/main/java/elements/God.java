package elements;

import it.polimi.ingsw.riccardoemelissa.Message;

import java.util.ArrayList;
import java.util.Objects;

public interface God {
    public boolean CheckMoment(Worker activeWorker, Player CardOwner, String str, int[] newpos, BoardGame b, Message m);
    public boolean Power(ArrayList<Worker> worker, int[] newpos, BoardGame b);
}

