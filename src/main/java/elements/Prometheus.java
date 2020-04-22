package elements;

import it.polimi.ingsw.riccardoemelissa.GameState;
import it.polimi.ingsw.riccardoemelissa.Message;

import java.util.ArrayList;

public class Prometheus extends God {
    private boolean opponent_turn = false;

    @Override
    public boolean Move(BoardGame b, ArrayList<Worker> worker_list, int[] newpos) {
        GameState game = new GameState();

        int[] oldpos = worker_list.get(0).GetPosition();
        int n = 0;

        ArrayList<int[]> adj_moves = AdjacentBox(worker_list.get(0).GetPosition());

        for (int[] pos : adj_moves) {
            if ((b.GetLevelBox(pos) - b.GetLevelBox(oldpos)) > 1)
                n++;
        }
        if (n == 0) {
            int[] pos = FirstBuild();

            if (Build(b, worker_list.get(0), pos))
                if (DoMove(b, worker_list, newpos))
                    return true;
        } else if (DoMove(b, worker_list, newpos))
            return true;

        return false;
    }

    public int[] FirstBuild() {
        return new int[]{0, 0};
    }

    public boolean DoMove(BoardGame b, ArrayList<Worker> worker_list, int[] newpos) {
        ArrayList<God> opponents_action = checkOpponentCondition();
        int[] oldpos = worker_list.get(0).GetPosition();
        int n = 0;

        if (CheckAdjacentBox(newpos, worker_list.get(0).GetPosition())) {
            for (God g : opponents_action) {
                if (g.Move(b, worker_list, newpos) && b.IsAPossibleMove(newpos, oldpos)) {
                    n++;
                }
            }

            if (n == opponents_action.size()) {
                SetPosition(worker_list, newpos, b);
                return true;
            }
        }
        return false;
    }
}
