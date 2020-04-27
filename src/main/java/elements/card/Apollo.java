package elements.card;


import elements.BoardGame;
import elements.God;
import elements.GodCardType;
import elements.Worker;
import it.polimi.ingsw.riccardoemelissa.Command;
import it.polimi.ingsw.riccardoemelissa.GameState;

import java.util.ArrayList;

public class Apollo extends God {
    private boolean opponent_turn = false;
    private GodCardType type=GodCardType.MOVE;

    @Override
    public GodCardType Move(BoardGame b, Worker active_worker, int[] newpos)
    {
        if (!b.GetStateBox(newpos))
            SetApolloPosition(active_worker, newpos, b);
        else
            super.SetPosition(active_worker, active_worker.GetPosition(), newpos, b);

        this.type=GodCardType.BUILD;
        return GodCardType.OK;
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

                if(b.GetLevelBox(pos)==4)
                    continue;

                adj_boxes.add(pos);
            }
        }
        return adj_boxes;
    }

    public void SetApolloPosition(Worker active_worker, int[] newpos, BoardGame b)
    {
        b.GetOccupant(newpos).SetPosition(active_worker.GetPosition());//da controllare
        b.setOccupant(active_worker.GetPosition(),b.GetOccupant(newpos));

        active_worker.SetPosition(newpos);
        b.setOccupant(newpos,active_worker);
    }

    @Override
    public void doPower(GameState game, Command cmd)
    {
        SetApolloPosition((Worker) cmd.GetObj(),cmd.GetPos(),game.GetBoard());
    }
}

