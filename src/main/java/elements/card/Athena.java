package elements.card;

import elements.BoardGame;
import elements.God;
import elements.GodCardType;
import elements.Worker;

public class Athena extends God {
    private boolean opponent_turn=true;
    private GodCardType type=GodCardType.PASSIVE;

    private boolean in_action=false;

    /*
    If Athena can act during the current turn, CanAct return true
     */
    public boolean SetInAction(int[] old_pos, int[] new_pos, BoardGame b) {
        if ((b.GetLevelBox(new_pos) - b.GetLevelBox(old_pos)) == 1)
            return true;
        return false;
    }

    @Override
    public GodCardType Move(BoardGame b, Worker active_worker, int[] newpos)
    {
        if (active_worker.GetProprietary().GetGodCard() instanceof Athena)
        {
            in_action = SetInAction(active_worker.GetPosition(), newpos, b);
            super.Move(b, active_worker, newpos);
            return GodCardType.OK;
        } else if (in_action)
        {
            return RespectAthenaAction(active_worker.GetPosition(), newpos, b);
        }
        return GodCardType.OK;
    }

    /*
    If Athena allows the action, Action returns true
     */
    public GodCardType RespectAthenaAction(int[] old_pos, int[] new_pos, BoardGame b) {
        if ((b.GetLevelBox(new_pos) - b.GetLevelBox(old_pos)) < 1)
            return GodCardType.OK;
        return GodCardType.NOTPOSSIBLE;
    }
}