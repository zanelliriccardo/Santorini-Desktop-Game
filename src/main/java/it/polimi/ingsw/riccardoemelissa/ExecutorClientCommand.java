package it.polimi.ingsw.riccardoemelissa;

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
        if(CommandType.CHANGE_TURN==cmd.GetType())
        {
            game.NextTurn();
        }
        if(CommandType.NICKNAME==cmd.GetType())
        {
            game.NewPlayer((String)cmd.GetObj());
            //game.GetActivePlayer().SetNickname((String)cmd.GetObj());
        }
        if(CommandType.NEWWORKER==cmd.GetType())
        {
            game.SetNewWorker(cmd.GetActiveWorker());
        }
        if(CommandType.MOVE==cmd.GetType())
        {
            if(game.CheckOpponentGod(cmd.GetActiveWorker(),cmd.GetPos()))
                game.GetActivePlayer().GetGodCard().Move(game.GetBoard(),cmd.GetActiveWorker(),cmd.GetPos());
        }
        if(CommandType.BUILD==cmd.GetType())
        {
            //aggiungere costruzione possibile da altri god
            game.GetActivePlayer().GetGodCard().Build(game.GetBoard(),cmd.GetActiveWorker(),cmd.GetPos());
        }

    }
}
