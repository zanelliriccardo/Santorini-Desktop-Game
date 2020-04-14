package elements;

import java.util.ArrayList;

public interface God {
    public boolean CheckMoment(Player ActivePlayer, Player CardOwner, String str);
    public void Power(Worker worker, ArrayList<int[]> possiblemoves);
}

