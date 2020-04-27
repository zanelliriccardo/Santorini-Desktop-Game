package elements;

import it.polimi.ingsw.riccardoemelissa.Command;
import it.polimi.ingsw.riccardoemelissa.GameProxy;
import it.polimi.ingsw.riccardoemelissa.GameState;

import java.util.ArrayList;

public abstract class God {

    private boolean opponent_turn;
    private GodCardType type;
    private ArrayList<GodCardType> turn=new ArrayList<>();
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
    public GodCardType Move(BoardGame b, Worker active_worker, int[] newpos)
    {
        SetPosition(active_worker, active_worker.GetPosition(), newpos, b);
        this.type=GodCardType.BUILD;
        return GodCardType.OK;
    }

    /**
     * This method sets the new worker's position
     * @param active_worker : worker chosen to do the move
     * @param oldpos : initial position
     * @param newpos : new position choose by the player
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
    public GodCardType Build(BoardGame b, Worker activeWorker, int[] pos)
    {
        int[] workerpos = activeWorker.GetPosition();

        b.DoBuild(pos);
        this.type=GodCardType.MOVE;
        return GodCardType.OK;
    }

    public boolean GetOpponentTurn(){return opponent_turn;}
    public GodCardType GetType(){return type;}

    public ArrayList<int[]> adjacentBoxNotOccupiedNotDome(BoardGame b, int[] worker_pos)
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

                if(!b.GetStateBox(pos))
                    continue;

                if(b.GetLevelBox(pos)==4)
                    continue;

                adj_boxes.add(pos);
            }
        }
        return adj_boxes;
    }

    public GodCardType getType(){return type;}

    public void setIn_action(boolean in_action){
        this.in_action=in_action;
    }

    public void doPower(GameState game, Command cmd)
    {

    }
}
