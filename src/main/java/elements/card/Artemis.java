package elements.card;

import elements.BoardGame;
import elements.God;
import elements.Worker;
import it.polimi.ingsw.riccardoemelissa.Message;

import java.io.IOException;
import java.util.ArrayList;

public class Artemis extends God {
    private boolean opponent_turn = false;

    @Override
    public boolean Move(BoardGame b, ArrayList<Worker> worker_list, int[] newpos) {
        ArrayList<God> opponents_action = checkOpponentCondition();
        int[] oldpos = worker_list.get(0).GetPosition();
        int n = 0;

        if(CheckAdjacentBox(newpos, worker_list.get(0).GetPosition()))
        {
            for (God g : opponents_action)
            {
            if (g.Move(b, worker_list, newpos) && b.IsAPossibleMove(newpos, oldpos))
                n++;
            }

            if (n == opponents_action.size())
            SetPosition(worker_list, newpos, b);

            if(ArtemisAction(b, worker_list, oldpos))
            return true;
    }
        return false;
    }


    public boolean MoveAgain ()
    {
        //CLIENT
            return true;
    }

    public int[] GetNewPosition ()
    {
        //CLIENT
        int[] newpos = new int[2];
        newpos[0] = 0;
        newpos[1] = 1;
        return newpos;
    }

    public boolean ArtemisAction (BoardGame b, ArrayList<Worker> worker_list, int[] oldpos)
    {
        if (MoveAgain())
        {
            int[] newpos = GetNewPosition();
            ArrayList<God> opponents_action = checkOpponentCondition();
            int n = 0;

            if(CheckAdjacentBox(newpos, worker_list.get(0).GetPosition())) {
                for (God g : opponents_action) {
                    if (g.Move(b, worker_list, newpos)) {
                        if (!b.GetStateBox(newpos))
                            return false;
                        if (b.GetLevelBox(newpos) == 4)
                            return false;
                        if ((b.GetLevelBox(newpos) - b.GetLevelBox(worker_list.get(0).GetPosition())) > 1)
                            return false;
                        if (newpos[0] == oldpos[0] && newpos[1] == oldpos[1])
                            return false;
                    }
                    n++;
                }

                if (n == opponents_action.size()) {
                    SetPosition(worker_list, newpos, b);
                    return true;
                }
            }
        }
        return false;
    }
}
