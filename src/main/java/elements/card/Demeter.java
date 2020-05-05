package elements.card;

import elements.BoardGame;
import elements.God;
import elements.GodCardType;
import elements.Worker;
import it.polimi.ingsw.riccardoemelissa.CommandType;

import java.util.ArrayList;

public class Demeter extends God {
    private boolean opponent_turn = false;
    private GodCardType type=GodCardType.MOVE;

    private int[] old_position=null;

    private boolean in_action=false;

    /**
     * apply demeter rules if power is active
     *
     * if is the first build of turn, saves the position and during the next build
     * of this turn check they is different
     *
     * @param b : board
     * @param active_Worker
     * @param pos -> the build position given by the player belongs to an adjacent box
     * @return
     */
    @Override
    public CommandType Build(BoardGame b, Worker active_Worker, int[] pos)
    {
        if(old_position==null)
        {
            old_position = pos;
            super.Build(b,active_Worker,pos);

            if(in_action) this.type=GodCardType.BUILD;
            else this.type=GodCardType.ENDTURN;

            return CommandType.BUILD;
        }
        else
        {
            old_position=null;
            return super.Build(b, active_Worker, pos);
        }
    }

    /**
     * get adjacent box where possible moves in following demeter rules
     *
     * @param b : board
     * @param worker_pos : actual position of worker
     * @return
     */
    @Override
    public ArrayList<int[]> adjacentBoxNotOccupiedNotDome(BoardGame b, int[] worker_pos) {
        ArrayList<int[]> possibleBox=super.adjacentBoxNotOccupiedNotDome(b, worker_pos);
        if(in_action&&old_position!=null&&type==GodCardType.BUILD)
            possibleBox.remove(old_position);

        return possibleBox;
    }
}