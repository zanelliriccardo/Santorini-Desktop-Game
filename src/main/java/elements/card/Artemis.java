package elements.card;

import elements.BoardGame;
import elements.God;
import elements.GodCardType;
import elements.Worker;
import it.polimi.ingsw.riccardoemelissa.CommandType;

import java.util.ArrayList;

public class Artemis extends God {
    private boolean opponent_turn = false;
    private GodCardType type=GodCardType.MOVE;

    private int[] old_position=null;
    private boolean in_action=false;

    @Override
    public CommandType Move(BoardGame b, Worker active_worker, int[] newpos)
    {
        if(old_position==null)
        {
            old_position = active_worker.GetPosition();
            super.Move(b,active_worker,newpos);

            if(in_action) this.type=GodCardType.MOVE;
            else this.type=GodCardType.BUILD;



            return CommandType.MOVE;
        }
        else
        {
            old_position=null;
            return super.Move(b, active_worker, newpos);
        }
    }

    @Override
    public ArrayList<int[]> adjacentBoxNotOccupiedNotDome(BoardGame b, int[] worker_pos) {
        ArrayList<int[]> possibleBox=super.adjacentBoxNotOccupiedNotDome(b, worker_pos);
        if(in_action&&old_position!=null&&type==GodCardType.MOVE)
            possibleBox.remove(old_position);

        return possibleBox;
    }
}
