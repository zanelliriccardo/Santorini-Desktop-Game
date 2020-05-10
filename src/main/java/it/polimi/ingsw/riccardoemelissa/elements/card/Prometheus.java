package it.polimi.ingsw.riccardoemelissa.elements.card;

import it.polimi.ingsw.riccardoemelissa.elements.BoardGame;
import it.polimi.ingsw.riccardoemelissa.elements.God;
import it.polimi.ingsw.riccardoemelissa.elements.GodCardType;

import java.io.Serializable;
import java.util.ArrayList;

public class Prometheus extends God implements Serializable {
    private boolean opponent_turn = false;
    private GodCardType type=GodCardType.MOVE;

    private boolean in_action=false;

    public Prometheus()
    {
        opponent_turn=false;
        type=GodCardType.MOVE;
    }
    /**
     * manage turn following prometheus rules
     *
     * if power is active before move active player do a build
     *
     * @param b : board
     * @param worker_pos : actual position of worker
     * @return
     */
    @Override
    public ArrayList<int[]> adjacentBoxNotOccupiedNotDome(BoardGame b, int[] worker_pos) {
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

                if(b.GetLevelBox(pos)>b.GetLevelBox(worker_pos)&&in_action&&type==GodCardType.MOVE)
                    continue;

                adj_boxes.add(pos);
            }
        }
        return adj_boxes;
    }

    @Override
    public void setIn_action(boolean in_action) {
        this.in_action = in_action;
        this.type=GodCardType.BUILD;
    }
}
