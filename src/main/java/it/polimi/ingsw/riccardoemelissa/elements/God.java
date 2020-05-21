package it.polimi.ingsw.riccardoemelissa.elements;

import it.polimi.ingsw.riccardoemelissa.CommandType;
import it.polimi.ingsw.riccardoemelissa.GameState;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class God implements Serializable {
    private GodCardType request;
    private boolean opponent_turn;

    public God()
    {
        request =GodCardType.MOVE;
    }
    /**
     * Move the active worker
     *
     * The method accepts the active worker and the position where he would like to move.
     * "newpos" is a permitted move, so here's the move by changing the active worker's position.
     * @param b : board
     * @param active_worker : worker chosen to do the move
     * @param newpos : position chosen by player
     * @return
     */
    public CommandType move(BoardGame b, Worker active_worker, int[] newpos)
    {
        setPosition(GameState.getBoard().getOccupant(active_worker.getPosition()), active_worker.getPosition(), newpos, b);
        request =GodCardType.BUILD;
        return CommandType.MOVE;
    }

    /**
     * This method sets the new worker's position
     * @param active_worker : worker chosen to do the move
     * @param oldpos : initial position
     * @param newpos : new position choose by the player
     * @param b
     */
    public void setPosition(Worker active_worker, int[] oldpos, int[] newpos, BoardGame b)
    {
        GameState.getBoard().getBoard()[oldpos[0]][oldpos[1]].removeOccupant();//funziona ma da mettere meglio
        active_worker.setPosition(newpos);
        GameState.getBoard().getBoard()[newpos[0]][newpos[1]].changeState(active_worker);
    }

    /**
     * This method is used to build:
     * if the pos respects the classic conditions of build,
     * the worker does his build and the method returns true
     * @param b : board
     * @param activeWorker : worker chosen to do the build
     * @param pos -> the build position given by the player belongs to an adjacent box
     * @return
     */
    public CommandType build(BoardGame b, Worker activeWorker, int[] pos)
    {
        int[] workerpos = activeWorker.getPosition();

        GameState.getBoard().doBuild(pos);
        this.request =GodCardType.ENDTURN;
        return CommandType.BUILD;
    }

    public boolean getOpponentTurn()
    {
        return opponent_turn;
    }

    public GodCardType getCardType(){return request;}

    /**
     * get adjacent box where possible moves in
     *
     * @param b : board
     * @param worker_pos : actual position of worker
     * @return
     */
    public ArrayList<int[]> adjacentBoxNotOccupiedNotDome(BoardGame b, int[] worker_pos)
    {
        ArrayList<int[]> adj_boxes = new ArrayList<>();


        for (int x = worker_pos[0] - 1; x <= worker_pos[0] + 1; x++) {
            for (int y = worker_pos[1] - 1; y <= worker_pos[1] + 1; y++) {
                if (x == worker_pos[0] && y == worker_pos[1])
                    continue;

                if (x > 4 || x < 0)
                    continue;

                if (y > 4 || y < 0)
                    continue;

                if(!b.getStateBox(x,y))
                    continue;

                if(b.getLevelBox(x,y)==4)
                    continue;
                int[] pos=new int[]{x,y};
                adj_boxes.add(pos);
            }
        }
        return adj_boxes;
    }

    public abstract void setIn_action(PowerType powerSet);

    public abstract PowerType getIn_action();

    /**
     * set to default value
     */
    public abstract void resetCard();

    public void setOpponentTrue(String s) {
        opponent_turn= s.equals("true");
    }

    public void setCardType(GodCardType type) {
        this.request =type;
    }
}
