package elements;

import java.util.ArrayList;

public class Pan extends God {

    @Override
    public boolean Move (BoardGame b, ArrayList<Worker> worker_list, int[] newpos)
    {
        ArrayList<God> opponents_action = checkOpponentCondition();
        int[] oldpos = worker_list.get(0).GetPosition();
        int n=0;

        for(God g : opponents_action)
        {
            if (g.Move(b, worker_list, newpos) && b.IsAPossibleMove(newpos, oldpos))
            {
                n++;
            }
        }

        if(n == opponents_action.size())
        {
            SetPosition(worker_list, newpos, b);

            if(b.GetLevelBox(oldpos) - b.GetLevelBox(newpos) > 2)
                //VINTO

            return true;
        }
        return false;






    }


}
