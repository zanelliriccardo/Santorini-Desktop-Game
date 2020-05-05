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

    /**
     * apply artemis rules if power is active
     *
     * if is the first move of turn, saves the position and during the next move
     * of this turn check they is different
     *
     * @param b : board
     * @param active_worker : worker chosen to do the move
     * @param newpos : position chosen by player
     * @return
     */
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

    /**
     * get adjacent box where possible moves in, follow artemis rules
     *
     * @param b : board
     * @param worker_pos : actual position of worker
     * @return
     */
    @Override
    public ArrayList<int[]> adjacentBoxNotOccupiedNotDome(BoardGame b, int[] worker_pos) {
        ArrayList<int[]> possibleBox=super.adjacentBoxNotOccupiedNotDome(b, worker_pos);
        if(in_action&&old_position!=null&&type==GodCardType.MOVE)
            possibleBox.remove(old_position);

        return possibleBox;
    }
}
