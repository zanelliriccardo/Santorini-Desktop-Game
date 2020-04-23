package elements;

import com.sun.javafx.scene.shape.ArcHelper;
import it.polimi.ingsw.riccardoemelissa.GameState;

import java.util.ArrayList;

public abstract class God {

    private boolean opponent_turn;
    private boolean in_action;

    //worker_list: 1 -> ACTIVE_WORKER, 2-> WORKER_AVV

    public boolean Move(BoardGame b, Worker active_worker, int[] newpos)
    {
        ArrayList<God> opponents_action = checkOpponentCondition();
        int[] oldpos = active_worker.GetPosition();
        int n=0;

        if(CheckAdjacentBox(newpos, active_worker.GetPosition())) {
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
