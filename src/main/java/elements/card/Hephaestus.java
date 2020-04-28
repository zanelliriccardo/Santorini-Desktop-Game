package elements.card;

import elements.BoardGame;
import elements.God;
import elements.GodCardType;
import elements.Worker;
import it.polimi.ingsw.riccardoemelissa.CommandType;

import java.util.ArrayList;

public class Hephaestus extends God {
    private boolean opponent_turn = false;
    private GodCardType type=GodCardType.MOVE;

    private boolean in_action=false;

    @Override
    public CommandType Build(BoardGame b, Worker activeWorker, int[] pos) {
        super.Build(b,activeWorker,pos);
        return super.Build(b,activeWorker,pos);
    }

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

                if(!b.GetStateBox(pos))
                    continue;

                if(b.GetLevelBox(pos)==4)
                    continue;

                if(b.GetLevelBox(pos)>1&&in_action)
                    continue;

                adj_boxes.add(pos);
            }
        }
        return adj_boxes;
    }
}
