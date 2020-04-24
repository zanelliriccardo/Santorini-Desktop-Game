package elements.card;

import elements.BoardGame;
import elements.God;
import elements.Worker;
import it.polimi.ingsw.riccardoemelissa.GameState;

import java.util.ArrayList;

public class Prometheus extends God {
    private boolean opponent_turn = false;

    @Override
    public boolean Move(BoardGame b, Worker active_worker, int[] newpos) {

        ArrayList<int[]> adj_moves = b.AdjacentBox(active_worker.GetPosition());
        int n=0;

        for (int[] pos : adj_moves) {
            if ((b.GetLevelBox(pos) - b.GetLevelBox(active_worker.GetPosition())) > 1)
                n++;
        }

        if (n == 0 && PrometheusAction(b, active_worker)) {
            int[] pos = FirstBuild();
            b.DoBuild(pos);
        }

        if(super.Move(b, active_worker,newpos))
            return true;
        else return false;
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
}
