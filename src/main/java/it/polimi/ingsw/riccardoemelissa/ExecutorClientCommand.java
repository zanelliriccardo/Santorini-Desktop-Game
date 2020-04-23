package it.polimi.ingsw.riccardoemelissa;

import elements.Worker;

import java.util.Observable;
import java.util.Observer;

public class ExecutorClientCommand implements Observer {

    private GameState game;

    @Override
    public void update(Observable o, Object arg)
    {
        Command cmd=(Command) arg;
        if(cmd.GetType()==CommandType.DISCONNECTED)
        {
            game.EndGame();//da implementare
            game=null;
        }

        //gestione comandi
        if(cmd.GetType()==CommandType.MODE)
        {
            game.SetNumPlayer((int)cmd.GetObj());
        }
        else if(CommandType.CHANGE_TURN==cmd.GetType())
        {
            game.NextTurn();
        }
        else if(CommandType.NICKNAME==cmd.GetType())
        {
            game.NewPlayer((String)cmd.GetObj());
        }
        else if(CommandType.NEWWORKER==cmd.GetType())
        {
            game.SetNewWorker((Worker) cmd.GetObj());
        }
        else if(CommandType.MOVE==cmd.GetType())
        {
            if(game.CheckMove((Worker)cmd.GetObj(),cmd.GetPos()))
                game.GetActivePlayer().GetGodCard().Move(game.GetBoard(),(Worker)cmd.GetObj(),cmd.GetPos());
        }
        else if(CommandType.BUILD==cmd.GetType())
        {
            //aggiungere costruzione possibile da altri god
            if(game.checkBuild((Worker)cmd.GetObj(),cmd.GetPos()))
                game.GetActivePlayer().GetGodCard().Build(game.GetBoard(),(Worker)cmd.GetObj(),cmd.GetPos());
        }

    }
}
