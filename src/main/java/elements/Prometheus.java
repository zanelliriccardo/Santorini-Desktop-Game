package elements;

import it.polimi.ingsw.riccardoemelissa.GameState;
import it.polimi.ingsw.riccardoemelissa.Message;

import java.util.ArrayList;

public class Prometheus extends God {
    private boolean opponent_turn = false;

    @Override
    public boolean Move(BoardGame b, ArrayList<Worker> worker_list, int[] newpos)
    {
        GameState game = new GameState();

        int index = worker_list.get(0).GetID();

        ArrayList<int[]> possiblemoves = game.PossibleMoves(index);

        if(possiblemoves.size() == 0)
        {
            int [] pos = FirstBuild();

            if(Build(b, worker_list, pos))
                if(DoMove(b, worker_list, newpos))
                    return true;
        }

        else if(DoMove(b, worker_list, newpos))
            return true;

        return false;
    }

    public int[] FirstBuild ()
    {
        return new int[]{0, 0};
    }

    public boolean DoMove (BoardGame b, ArrayList<Worker> worker_list, int[] newpos)
    {
        ArrayList<God> opponents_action = checkOpponentCondition();
        int[] oldpos = worker_list.get(0).GetPosition();
        int n = 0;

        for (God g : opponents_action) {
            if (g.Move(b, worker_list, newpos) && b.IsAPossibleMove(newpos, oldpos)) {
                n++;
            }
        }

        if (n == opponents_action.size()) {
            SetPosition(worker_list, newpos, b);
            return true;
        }
        return false;
    }
}
