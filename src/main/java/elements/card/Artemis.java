package elements.card;

import elements.BoardGame;
import elements.God;
import elements.GodCardType;
import elements.Worker;

import java.util.ArrayList;

public class Artemis extends God {
    private boolean opponent_turn = false;
    private GodCardType type=GodCardType.MOVE;

    private int[] old_position=null;
    private boolean in_action=false;

    @Override
    public GodCardType Move(BoardGame b, Worker active_worker, int[] newpos)
    {
        if(in_action)
        {
            old_position = active_worker.GetPosition();
            super.Move(b,active_worker,newpos);
            in_action=false;
            this.type=GodCardType.MOVE;
            return GodCardType.OK;
        }
        else
        {
            super.Move(b, active_worker, newpos);
            type=GodCardType.BUILD;
            return GodCardType.OK;
        }
    }

    /**
     * This method asks to player if he wants to move again the active worker
     * @return
     */
    public boolean MoveAgain ()
    {
        //CLIENT
            return true;
    }

    /**
     * This method checks if the new position is allow
     * @return
     */
    public boolean CanMoveAgain()
    {
        return true;
    }

    public int[] GetNewPosition ()
    {
        //CLIENT
        int[] newpos = new int[2];
        newpos[0] = 0;
        newpos[1] = 1;
        return newpos; //va controllata in board game, nel caso in cui non sia consentita,
    }

    public void ArtemisAction (BoardGame b, Worker active_worker, int[] oldpos)
    {
        int[] newpos = GetNewPosition();

        if (!b.GetStateBox(newpos))
            return;
        if (b.GetLevelBox(newpos) == 4)
            return;
        if ((b.GetLevelBox(newpos) - b.GetLevelBox(active_worker.GetPosition())) > 1)
            return;
        if (newpos[0] == oldpos[0] && newpos[1] == oldpos[1])
            return;

        else
        {
            SetPosition(active_worker, active_worker.GetPosition(), newpos, b);
        }
    }

    @Override
    public ArrayList<int[]> adjacentBoxNotOccupiedNotDome(BoardGame b, int[] worker_pos) {
        ArrayList<int[]> possibleBox=super.adjacentBoxNotOccupiedNotDome(b, worker_pos);
        if(!in_action&&old_position!=null&&type==GodCardType.MOVE)
            possibleBox.remove(old_position);

        return possibleBox;
    }
}
