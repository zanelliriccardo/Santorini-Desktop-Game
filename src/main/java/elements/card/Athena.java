package elements.card;

import elements.BoardGame;
import elements.God;
import elements.Worker;
import it.polimi.ingsw.riccardoemelissa.GameState;
import it.polimi.ingsw.riccardoemelissa.Message;

import java.util.ArrayList;

public class Athena extends God {
    private boolean opponent_turn;
    private boolean in_action;

    public Athena()
    {
        opponent_turn = true;
        in_action = false;
    }

    public String GetOwner()
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

    /*
    If Athena can act during the current turn, CanAct return true
     */
    public boolean CanAct ( int[] old_pos, int[] new_pos, BoardGame b)
    {
            if ((b.GetLevelBox(new_pos) - b.GetLevelBox(old_pos)) == 1)
                return true;
            return false;
    }

    @Override
    public boolean Move (BoardGame b, ArrayList <Worker> worker_list, int[] newpos) {
        GameState game = new GameState();
        for (int i = 0; i < game.GetPlayerNumber(); i++) {
            if (game.GetPlayers()[i].GetNickname().equals(GetOwner())) {
                if (CanAct(worker_list.get(0).GetPosition(), newpos, b)) {
                    in_action = true;
                } else {
                    in_action = false;
                }
            }
            else if (in_action)
            {
                if (Action(worker_list.get(0).GetPosition(), newpos, b))
                    return true;
            }
        }
        return false;
    }

    /*
    If Athena allows the action, Action returns true
     */
        public boolean Action ( int[] new_pos, int[] old_pos, BoardGame b)
        {
            if ((b.GetLevelBox(new_pos) - b.GetLevelBox(old_pos)) < 1)
                return true;
            return false;
        }

    }