package elements.card;

import elements.BoardGame;
import elements.God;
import elements.GodCardType;
import elements.Worker;

import java.util.ArrayList;

public class Minotaur extends God {
    private boolean opponent_turn = false;
    private GodCardType type=GodCardType.MOVE;

    @Override
    public GodCardType Move(BoardGame b, Worker active_worker, int[] newpos)
    {
        if (!b.GetStateBox(newpos))
            SetMinotaurPosition(active_worker, newpos, b);
        else
            SetPosition(active_worker, active_worker.GetPosition(), newpos, b);

        return GodCardType.BUILD;
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

                int[] newpos_opponent = new int[]{(x - worker_pos[0]) + x, (y - worker_pos[1]) + y};

                if(newpos_opponent[0]>4||newpos_opponent[1]>4)
                    continue;

                if(!b.GetStateBox(newpos_opponent))
                    continue;

                if(b.GetLevelBox(pos)==4)
                    continue;

                adj_boxes.add(pos);
            }
        }
        return adj_boxes;
    }

    public void SetMinotaurPosition (Worker active_worker, int[] newpos, BoardGame b)
    {
        int[] newpos_opponent = new int[]{(newpos[0] - active_worker.GetX()) + newpos[0], (newpos[1] - active_worker.GetY()) + newpos[1]};

            b.GetOccupant(newpos).SetPosition(newpos_opponent);
            b.ChangeState(newpos_opponent, b.GetOccupant(newpos).GetProprietary().GetColor());

            b.ChangeState(active_worker.GetPosition());
            active_worker.SetPosition(newpos);
            b.ChangeState(newpos, active_worker.GetProprietary().GetColor());
    }

}