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

    @Override
    public ArrayList<int[]> adjacentBoxNotOccupiedNotDome(BoardGame b, int[] worker_pos) {
        ArrayList<int[]> possibleBox=super.adjacentBoxNotOccupiedNotDome(b, worker_pos);
        if(!in_action&&old_position!=null&&type==GodCardType.MOVE)
            possibleBox.remove(old_position);

        return possibleBox;
    }
}
