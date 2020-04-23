package elements.card;

import elements.BoardGame;
import elements.God;
import elements.Worker;

public class Athena extends God {
    private boolean opponent_turn;
    private boolean in_action;

    public Athena()
    {
        opponent_turn = true;
        in_action = false;
    }

    /*public String GetOwner()
    {
        GameState game = new GameState();
        String owner = "";

        for (int i = 0; i < game.GetPlayerNumber(); i++)
        {
            if (game.GetPlayers()[i].GetGodCard().equals("Athena"))
                owner = game.GetPlayers()[i].GetNickname();

        }
        return owner;
    }

     */

    /*
    If Athena can act during the current turn, CanAct return true
     */
    public boolean SetInAction ( int[] old_pos, int[] new_pos, BoardGame b)
    {
            if ((b.GetLevelBox(new_pos) - b.GetLevelBox(old_pos)) == 1)
                return true;
            return false;
    }

    @Override
    public boolean Move (BoardGame b, Worker active_worker, int[] newpos) {
        if(active_worker.GetProprietary().GetGodCard() instanceof Athena) {
            super.Move(b, active_worker, newpos);
            in_action = SetInAction(active_worker.GetPosition(), newpos, b);
        }

        if (in_action)
        {
            if (AthenaAction(active_worker.GetPosition(), newpos, b))
                    return true;
        }
        return false;
    }

    /*
    If Athena allows the action, Action returns true
     */
        public boolean AthenaAction (int[] old_pos, int[] new_pos, BoardGame b)
        {
            if ((b.GetLevelBox(new_pos) - b.GetLevelBox(old_pos)) < 1)
                return true;
            return false;
        }

    }