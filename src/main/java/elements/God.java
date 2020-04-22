package elements;

import com.sun.javafx.scene.shape.ArcHelper;
import it.polimi.ingsw.riccardoemelissa.GameState;

import java.util.ArrayList;

public abstract class God {

    private boolean opponent_turn;
    private boolean in_action;

    //worker_list: 1 -> ACTIVE_WORKER, 2-> WORKER_AVV


    public ArrayList<int[]> AdjacentBox (int[] worker_pos)
    {
        ArrayList<int[]> adj_boxes = new ArrayList<>();
        int[] pos = new int[2];

        for (int x = worker_pos[0] - 1; x <= worker_pos[0] + 1; x++) {
            for (int y = worker_pos[1] - 1; y <= worker_pos[1] + 1; y++) {
                if (x == worker_pos[0] && y == worker_pos[1])
                    continue;

                if (x > 4 || x < 0)
                    continue;

                if (y > 4 || y < 0)
                    continue;

                pos[0] = x;
                pos[1] = y;

                adj_boxes.add(pos);
            }
        }
        return adj_boxes;
    }

    public boolean CheckAdjacentBox (int[] newpos, int[] worker_pos)
    {
        ArrayList<int[]> adj_boxes = AdjacentBox(worker_pos);

        if (adj_boxes.contains(newpos))
            return true;
        return false;
    }


    public boolean Move(BoardGame b, ArrayList<Worker> worker_list, int[] newpos)
    {
        ArrayList<God> opponents_action = checkOpponentCondition();
        int[] oldpos = worker_list.get(0).GetPosition();
        int n=0;

        if(CheckAdjacentBox(newpos, worker_list.get(0).GetPosition())) {
            for (God g : opponents_action) {
                if (g.Move(b, worker_list, newpos) && b.IsAPossibleMove(newpos, oldpos)) {
                    n++;
                }
            }

            if (n == opponents_action.size()) {
                SetPosition(worker_list, newpos, b);
                return true;
            }
        }
        return false;
    }

    public void SetPosition (ArrayList<Worker> worker_list, int[] newpos, BoardGame b)
    {
        worker_list.get(0).SetPosition(newpos);
        b.ChangeState(newpos,worker_list.get(0).GetProprietary().GetColor());
        b.ChangeState(worker_list.get(0).GetPosition());
    }

    public boolean Build(BoardGame b, Worker activeWorker, int[] pos)
    {
            int[] workerpos = activeWorker.GetPosition();

            if(CheckAdjacentBox(pos, activeWorker.GetPosition()) && b.IsAPossibleBuild(pos,workerpos))
            {
                b.DoBuild(pos);
                return true;
            }
            else
                return false;

    }

    public ArrayList<God> checkOpponentCondition()
    {
        ArrayList<God> list=new ArrayList<God>();
        GameState game = new GameState();
        for (int i =0;i<game.GetPlayerNumber();i++)
            if(!game.GetPlayers()[i].GetNickname().equals(game.GetActivePlayer().GetNickname()))
                if(game.GetActivePlayer().GetGodCard().GetOpponentTurn() && game.GetActivePlayer().GetGodCard().GetInAction() )
                    list.add(game.GetActivePlayer().GetGodCard());

        return list;
    }

    public boolean GetOpponentTurn() {
         return opponent_turn;
    }

    public boolean GetInAction() {
        return in_action;
    }
}
