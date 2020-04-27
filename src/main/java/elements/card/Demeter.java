package elements.card;

import elements.BoardGame;
import elements.God;
import elements.GodCardType;
import elements.Worker;

import java.util.ArrayList;

public class Demeter extends God {
    private boolean opponent_turn = false;
    private GodCardType type=GodCardType.MOVE;

    private int[] old_position=null;
    private boolean first_build=true;


    private boolean in_action=false;
    @Override
    public GodCardType Build(BoardGame b, Worker active_Worker, int[] pos)
    {
        if(in_action)
        {
            old_position = pos;
            super.Build(b,active_Worker,pos);
            in_action=false;
            this.type=GodCardType.BUILD;
            return GodCardType.OK;
        }
        else
        {
            super.Build(b, active_Worker, pos);
            type=GodCardType.ENDTURN;
            return GodCardType.OK;
        }
    }

    @Override
    public ArrayList<int[]> adjacentBoxNotOccupiedNotDome(BoardGame b, int[] worker_pos) {
        ArrayList<int[]> possibleBox=super.adjacentBoxNotOccupiedNotDome(b, worker_pos);
        if(!in_action&&old_position!=null&&type==GodCardType.BUILD)
            possibleBox.remove(old_position);

        return possibleBox;
    }
}