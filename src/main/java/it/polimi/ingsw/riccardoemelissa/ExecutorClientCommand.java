package it.polimi.ingsw.riccardoemelissa;

import elements.*;
import it.polimi.ingsw.riccardoemelissa.exception.EndGameException;

import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

public class ExecutorClientCommand {

    private GameState game;


    public void update(Object arg)
    {
        Command cmd=(Command) arg;

        switch (cmd.GetType())
        {
            case MODE:
                game.SetNumPlayer((int) cmd.GetObj());
                break;
            case NICKNAME:
                game.NewPlayer((String)cmd.GetObj());
                break;
            case NEWWORKER:
                game.GetBoard().setOccupant(cmd.GetPos(),(Worker)cmd.GetObj());
                break;
            case SETPOWER:
                game.getActivePlayer().GetGodCard().setIn_action((boolean) cmd.GetObj());
                break;
            case BOARDCHANGE:
                game.UpdateBoard((BoardGame) cmd.GetObj());//se passiamo la board da client a server in questo momento no prof sconsiglia
                break;
            case DISCONNECTED://da rivedere
                game.EndGame();
                game=null;
                break;
            case MOVE:
                if(!game.IsPossibleMove((Worker)cmd.GetObj(),cmd.GetPos())) {
                    update(new Command(CommandType.LOSE, null, null));//dafare
                    return;
                }
                ((Worker)cmd.GetObj()).GetProprietary().GetGodCard().Move(game.GetBoard(),(Worker)cmd.GetObj(),cmd.GetPos());
                if(((Worker)cmd.GetObj()).GetProprietary().GetGodCard().GetType()== GodCardType.WIN)
                    this.update(new Command(CommandType.WIN,game.getActivePlayer(),null));
                break;
            case BUILD:
                if(!game.IsPossibleBuild((Worker)cmd.GetObj(),cmd.GetPos())) {
                    this.update(new Command(CommandType.LOSE, null, null));//dafare
                    return;
                }
                ((Worker)cmd.GetObj()).GetProprietary().GetGodCard().Build(game.GetBoard(),(Worker)cmd.GetObj(),cmd.GetPos());
                break;
            case WIN:
                game.EndGame();
                break;
            case LOSE:
                game.RemovePlayer();
                break;
            case RESET:
                game.undoTurn();
                break;
        }

        game.GetBoard().custom_notifyAll();
    }
}
