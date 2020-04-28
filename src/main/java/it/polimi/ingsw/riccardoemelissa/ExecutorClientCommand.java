package it.polimi.ingsw.riccardoemelissa;

import elements.BoardGame;
import elements.CustomObserver;
import elements.Player;
import elements.Worker;

import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

public class ExecutorClientCommand implements CustomObserver {

    private GameState game;

    @Override
    public void update(Object arg)
    {
        Command cmd=(Command) arg;
        if(cmd.GetType()==CommandType.DISCONNECTED)
        {
            game.EndGame((Player) cmd.GetObj());
            game=null;
        }

        //gestione comandi da cambiare con switch

        if(cmd.GetType()==CommandType.SETPOWER)
        {
            game.getActivePlayer().GetGodCard().setIn_action((boolean) cmd.GetObj());
            game.GetBoard().custom_notifyAll();
        }
        if(cmd.GetType()==CommandType.BOARDCHANGE)
        {
            game.UpdateBoard((BoardGame) cmd.GetObj());
            game.GetBoard().custom_notifyAll();
        }
        if(cmd.GetType()==CommandType.MODE)
        {
            game.SetNumPlayer((int) cmd.GetObj());
        }
        else if(CommandType.CHANGE_TURN==cmd.GetType())
        {
            game.NextTurn();
            game.GetBoard().custom_notifyAll();
        }
        else if(CommandType.NICKNAME==cmd.GetType())
        {
            game.NewPlayer((String)cmd.GetObj());
        }
        else if(CommandType.NEWWORKER==cmd.GetType())
        {
            game.SetNewWorker((Worker)cmd.GetObj());//da cambiare
            game.GetBoard().custom_notifyAll();
        }
        else if(CommandType.MOVE==cmd.GetType())
        {
            //ricontrollare se mossa possibile però qua mossa singola se si riesce
            if(!game.IsPossibleMove((Worker)cmd.GetObj(),cmd.GetPos()))
            {
                update(new Command(CommandType.LOSE, null, null));//dafare
                return;
            }
            ((Worker)cmd.GetObj()).GetProprietary().GetGodCard().Move(game.GetBoard(),(Worker)cmd.GetObj(),cmd.GetPos());
            game.GetBoard().custom_notifyAll();
        }
        else if(CommandType.BUILD==cmd.GetType())
        {
            //ricontrollare se build possibile però qua build singola se si riesce
            if(!game.IsPossibleBuild((Worker)cmd.GetObj(),cmd.GetPos()))
            {
                update(new Command(CommandType.LOSE, null, null));//dafare
                return;
            }
            ((Worker)cmd.GetObj()).GetProprietary().GetGodCard().Build(game.GetBoard(),(Worker)cmd.GetObj(),cmd.GetPos());
            game.GetBoard().custom_notifyAll();
        }
        else if(CommandType.RESET==cmd.GetType())
        {
            game.undoTurn();//da implementare molto facile per 3 punti in più
        }

    }
}
