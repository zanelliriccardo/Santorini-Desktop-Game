package elements.card;

import elements.BoardGame;
import elements.God;
import elements.GodCardType;
import elements.Worker;

import java.util.ArrayList;

public class Prometheus extends God {
    private boolean opponent_turn = false;
    private GodCardType type=GodCardType.MOVE;

    private boolean in_action=false;

    @Override
    public GodCardType Move(BoardGame b, Worker active_worker, int[] newpos)
    {
        super.Move(b, active_worker,newpos);
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

                if(b.GetLevelBox(pos)>b.GetLevelBox(worker_pos)&&in_action)
                    continue;

                adj_boxes.add(pos);
            }
        }
        return adj_boxes;
    }

    public boolean BuildBeforeMove() //MSG CLIENT
    {
        return true;
    }

    public boolean PrometheusAction(BoardGame b, Worker active_worker)
    {
        if(BuildBeforeMove())
        {
            int[] pos = FirstBuild();

            if(b.IsAPossibleBuild(pos, active_worker.GetPosition()))
                return true;
            else return false;
        }
        else return false;
    }

    public int[] FirstBuild() {
        return new int[]{0, 0};
    }

    @Override
    public void setIn_action(boolean in_action) {
        this.in_action = in_action;
        this.type=GodCardType.BUILD;
    }
}
