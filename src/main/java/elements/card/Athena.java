package elements.card;

import elements.BoardGame;
import elements.God;
import elements.GodCardType;
import elements.Worker;
import it.polimi.ingsw.riccardoemelissa.CommandType;

public class Athena extends God {
    private boolean opponent_turn=true;
    private GodCardType type=GodCardType.MOVE;

    private boolean in_action=false;

    public boolean SetInAction(int[] old_pos, int[] new_pos, BoardGame b) {
        if ((b.GetLevelBox(new_pos) - b.GetLevelBox(old_pos)) == 1)
            return true;
        return false;
    }

    @Override
    public CommandType Move(BoardGame b, Worker active_worker, int[] newpos)
    {
        if (active_worker.GetProprietary().GetGodCard() instanceof Athena)
        {
            in_action = SetInAction(active_worker.GetPosition(), newpos, b);
            super.Move(b, active_worker, newpos);
        }
        else if (in_action)
        {
            return RespectAthenaAction(active_worker.GetPosition(), newpos, b);
        }
        return CommandType.BUILD;
    }

    public CommandType RespectAthenaAction(int[] old_pos, int[] new_pos, BoardGame b) {
        if ((b.GetLevelBox(new_pos) - b.GetLevelBox(old_pos)) < 1)
            return CommandType.ERROR;
        return CommandType.MOVE;
    }

    @Override
    public void resetCard(GodCardType move) {
        type=move;
    }
}