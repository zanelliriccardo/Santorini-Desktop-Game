package elements;

import com.sun.javafx.scene.shape.ArcHelper;
import it.polimi.ingsw.riccardoemelissa.GameState;

import java.util.ArrayList;

public abstract class God {

    private boolean opponent_turn;
    private boolean in_action;

    /**
     *This method is used to move the worker:
     * if the newpos respects the classic conditions of move,
     * the worker does his move and the method returns true
     * @param b
     * @param active_worker : worker chosen to do the move
     * @param newpos : the new worker's position given by the player belongs to an adjacent box and the move is allow by opponents' god cards
     * @return
     */
    public boolean Move(BoardGame b, Worker active_worker, int[] newpos)
    {
        if(b.IsAPossibleMove(newpos, active_worker.GetPosition())) {
            SetPosition(active_worker, active_worker.GetPosition(), newpos, b);
            return true;
        }
        else return false;
    }

    /**
     * This method sets the new worker's position
     * @param active_worker : worker chosen to do the move
     * @param oldpos : initial position
     * @param newpos : new positon choose by the player
     * @param b
     */
    public void SetPosition (Worker active_worker, int[] oldpos, int[] newpos, BoardGame b)
    {
        active_worker.SetPosition(newpos);
        b.ChangeState(newpos, active_worker.GetProprietary().GetColor());
        b.ChangeState(oldpos);
    }

    /**
     * This method is used to build:
     * if the pos respects the classic conditions of build,
     * the worker does his build and the method returns true
     * @param b
     * @param activeWorker : worker chosen to do the build
     * @param pos -> the build position given by the player belongs to an adjacent box
     * @return
     */
    public boolean Build(BoardGame b, Worker activeWorker, int[] pos)
    {
            int[] workerpos = activeWorker.GetPosition();

            if(b.IsAPossibleBuild(pos,workerpos))
            {
                b.DoBuild(pos);
                return true;
            }
            else return false;
    }

    /*public ArrayList<God> checkOpponentCondition()
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

     */
}
