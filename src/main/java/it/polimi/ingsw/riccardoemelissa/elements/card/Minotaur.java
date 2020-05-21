package it.polimi.ingsw.riccardoemelissa.elements.card;

import it.polimi.ingsw.riccardoemelissa.elements.*;
import it.polimi.ingsw.riccardoemelissa.CommandType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Minotaur extends God implements Serializable {
    private PowerType type=PowerType.PASSIVE;

    /**
     * do move following apollo rules
     *
     * if position is occupied, the method call method for manage worker position
     *
     * @param b : board
     * @param active_worker : worker chosen to do the move
     * @param newpos : position chosen by player
     * @return
     */
    @Override
    public CommandType move(BoardGame b, Worker active_worker, int[] newpos)
    {
        if (!b.getStateBox(newpos))
        {
            setMinotaurPosition(active_worker, newpos, b);
            super.setCardType(GodCardType.BUILD);
        }
        else
            super.move(b,active_worker,newpos);

        return CommandType.MOVE;
    }

    /**
     * get the box where is possible moves in
     *
     * get the box where is possible moves in, following minotaur rules
     *
     * @param b : board
     * @param worker_pos : actual position of worker
     * @return
     */
    @Override
    public ArrayList<int[]> adjacentBoxNotOccupiedNotDome(BoardGame b, int[] worker_pos) {
        ArrayList<int[]> adj_boxes = new ArrayList<>();

        if(super.getCardType().isMove())
            for (int x = worker_pos[0] - 1; x <= worker_pos[0] + 1; x++) {
                for (int y = worker_pos[1] - 1; y <= worker_pos[1] + 1; y++) {

                    if (x == worker_pos[0] && y == worker_pos[1])
                        continue;

                    if (x > 4 || x < 0)
                        continue;

                    if (y > 4 || y < 0)
                        continue;

                    if(b.getLevelBox(x,y)==4)
                        continue;

                    if(b.getStateBox(x,y))
                    {
                        int[] pos = new int[]{x,y};
                        adj_boxes.add(pos);
                        continue;
                    }

                    if(Objects.requireNonNull(b.getOccupant(x,y)).getProprietary().getNickname().compareTo(b.getOccupant(worker_pos).getProprietary().getNickname())==0)
                        continue;

                    int[] newpos_opponent = new int[]{(x - worker_pos[0]) + x, (y - worker_pos[1]) + y};

                    if(newpos_opponent[0]>4||newpos_opponent[1]>4||newpos_opponent[0]<0||newpos_opponent[1]<0)
                        continue;

                    if(!b.getStateBox(newpos_opponent))
                        continue;



                    int[] pos = new int[]{x,y};

                    adj_boxes.add(pos);
                }
            }
        else
            adj_boxes= super.adjacentBoxNotOccupiedNotDome(b,worker_pos);
        return adj_boxes;
    }

    /**
     * manage worker position following minotaur rules
     *
     * @param active_worker : worker chosen to move by player
     * @param newpos : position chosen by player
     * @param b : board
     */
    public void setMinotaurPosition(Worker active_worker, int[] newpos, BoardGame b)
    {
        int[] newpos_opponent = new int[]{(newpos[0] - active_worker.getX()) + newpos[0], (newpos[1] - active_worker.getY()) + newpos[1]};

        b.getOccupant(newpos).setPosition(newpos_opponent);
        b.setOccupant(newpos_opponent,b.getOccupant(newpos));
        b.setOccupant(newpos,active_worker);
        b.removeWorker(active_worker.getPosition());
        active_worker.setPosition(newpos);
    }

    /**
     * set the status of the power
     * @param powerSet
     */
    @Override
    public void setIn_action(PowerType powerSet) {
        if(!type.isPassive())
            type=powerSet;
    }

    /**
     * get the status of the power
     * @return
     */
    @Override
    public PowerType getIn_action() {
        return type;
    }

    /**
     * reset the God card
     */
    @Override
    public void resetCard() {
        super.setCardType(GodCardType.MOVE);
        setIn_action(PowerType.DISABLE);
    }
}