package elements;

import it.polimi.ingsw.riccardoemelissa.GameState;

import java.util.ArrayList;

public abstract class God {

    private boolean opponent_turn;
    private String type;
    //worker_list: 1 -> ACTIVE_WORKER, 2-> WORKER_AVV
    public boolean Move(BoardGame b, ArrayList<Worker> worker_list, int[] newpos)
    {
            int[] oldpos=worker_list.get(0).GetPosition();
            if(b.IsAPossibleMove(newpos,oldpos))
            {
                worker_list.get(0).SetPosition(newpos);
                b.ChangeState(newpos,worker_list.get(0).GetProprietary().GetColor());
                b.ChangeState(oldpos);
                return true;
            }
        return false;
    }

    public boolean Build(BoardGame b, ArrayList<Worker> worker_list, int[] pos)
    {
            int[] workerpos = worker_list.get(0).GetPosition();

            if(b.IsAPossibleBuild(pos,workerpos))
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
                if(game.GetActivePlayer().GetGodCard().GetOpponentTurn())
                    list.add(game.GetActivePlayer().GetGodCard());

        return list;
    }

    public boolean GetOpponentTurn() {
         return opponent_turn;
    }

    public String GetType() {
        return type;
    }
}
