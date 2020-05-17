package it.polimi.ingsw.riccardoemelissa;

import it.polimi.ingsw.riccardoemelissa.elements.*;

import java.util.ArrayList;

public class ExecutorClientCommand {

    private static GameState game=new GameState();

    /**
     * manage command received from clients
     *
     * this method take an argument that made by a command type(it corresponding to an action
     * to do on @Gamestate),an obj that will be converted into a specific class and a position
     * for the command types that require them
     * @param arg
     */
    public synchronized void update(Object arg)
    {
        Command cmd=(Command) arg;

        switch (cmd.GetType())
        {
            case MODE:
                GameState.SetNumPlayer((int) cmd.GetObj());
                GameState.GetBoard().custom_notifyAll();
                break;
            case NICKNAME:
                GameState.NewPlayer((String)cmd.GetObj());
                for (Player p : GameState.GetPlayers())
                    System.out.println(p.GetNickname());
                GameState.GetBoard().custom_notifyAll();
                break;
            case NEWWORKER:
                ((Worker)cmd.GetObj()).setProprietary(GameState.getActivePlayer());
                ((Worker)cmd.GetObj()).SetPosition(cmd.GetPos());
                GameState.GetBoard().setOccupant(cmd.GetPos(),(Worker)cmd.GetObj());

                ArrayList<Worker> workers=new ArrayList<Worker>();

                for (int i = 0; i < 5; i++)
                {
                    for (int j = 0; j < 5; j++)
                    {
                        if(!GameState.GetBoard().GetStateBox(i,j))
                            if(GameState.GetBoard().GetOccupant(i,j).GetProprietary().GetNickname().compareTo(GameState.getActivePlayer().GetNickname())==0)
                                workers.add(GameState.GetBoard().GetOccupant(i,j));
                    }
                }

                if(workers.size()==2)
                    GameState.NextTurn();

                GameState.GetBoard().custom_notifyAll();
                break;/*
            case SETPOWER://inutilizzato
                GameState.getActivePlayer().GetGodCard().setIn_action((PowerType) cmd.GetObj());
                break;
                */
            case DISCONNECTED://da rivedere
                GameState.EndGame();
                game=null;
                break;
            case MOVE:
                if(!GameState.IsPossibleMove((Worker)cmd.GetObj(),cmd.GetPos())) {
                    GameState.RemovePlayer(GameState.getActivePlayer());
                    return;
                }
                Worker move_worker=GameState.GetBoard().GetOccupant((((Worker) cmd.GetObj()).GetPosition()));
                move_worker.GetProprietary().GetGodCard().setIn_action(((Worker) cmd.GetObj()).GetProprietary().GetGodCard().getIn_action());

                move_worker.GetProprietary().GetGodCard().Move(GameState.GetBoard(),move_worker,cmd.GetPos());

                System.out.println("move");

                if(GameState.checkMoves(GameState.GetBoard(),move_worker).isEmpty()&&GameState.getActivePlayer().GetGodCard().getCardType().isMove())
                    lose();
                if(GameState.checkBuilds(GameState.GetBoard(),move_worker).isEmpty()&&GameState.getActivePlayer().GetGodCard().getCardType().isBuild())
                    lose();

                if(GameState.GetBoard().GetLevelBox(move_worker.GetPosition())==3||GameState.getActivePlayer().GetGodCard().getCardType().isWin())
                {
                    move_worker.GetProprietary().GetGodCard().setCardType(GodCardType.WIN);
                    GameState.EndGame();
                    GameState.setActiveWorker(null);
                }
                else
                    GameState.setActiveWorker(cmd.GetPos());

                GameState.GetBoard().custom_notifyAll();
                break;
            case BUILD:
                if(!GameState.IsPossibleBuild((Worker)cmd.GetObj(),cmd.GetPos())) {
                    GameState.RemovePlayer(GameState.getActivePlayer());
                    return;
                }
                Worker build_worker=GameState.GetBoard().GetOccupant((((Worker) cmd.GetObj()).GetPosition()));
                build_worker.GetProprietary().GetGodCard().setIn_action(((Worker) cmd.GetObj()).GetProprietary().GetGodCard().getIn_action());

                build_worker.GetProprietary().GetGodCard().Build(GameState.GetBoard(),build_worker,cmd.GetPos());

                System.out.println("build");

                if(GameState.checkMoves(GameState.GetBoard(),build_worker).isEmpty()&&GameState.getActivePlayer().GetGodCard().getCardType().isMove())
                    lose();
                if(GameState.checkBuilds(GameState.GetBoard(),build_worker).isEmpty()&&GameState.getActivePlayer().GetGodCard().getCardType().isBuild())
                    lose();

                GameState.GetBoard().custom_notifyAll();
                break;/*
            case WIN://inutilizzato
                if(GameState.getPlayer((String)cmd.GetObj()).GetGodCard().getCardType().isWin())
                {
                    GameState.EndGame();
                }
                GameState.setActiveWorker(null);
                GameState.GetBoard().custom_notifyAll();
                break;
            case LOSE://inutilizzato
                if(GameState.getPlayer((String)cmd.GetObj()).GetGodCard().getCardType()==GodCardType.LOSE)
                {
                    GameState.RemovePlayer(GameState.getPlayer((String)cmd.GetObj()));
                }
                GameState.GetBoard().custom_notifyAll();
                break;
                */
            case CHANGE_TURN:
                GameState.NextTurn();
                if(GameState.possibleMoves().isEmpty()&&GameState.getActivePlayer().GetGodCard().getCardType().isMove())
                    lose();
                else
                    GameState.getActivePlayer().GetGodCard().resetCard();

                GameState.setActiveWorker(null);
                GameState.GetBoard().custom_notifyAll();
                break;
            case RESET:
                GameState.undoTurn();
                break;
        }

        //GameState.GetBoard().custom_notifyAll();
    }

    private void lose()
    {
        GameState.getActivePlayer().GetGodCard().setCardType(GodCardType.LOSE);
        GameState.RemovePlayer(GameState.getActivePlayer());
        GameState.setActiveWorker(null);
        if(GameState.GetNumPlayers()==1) {
            GameState.GetPlayers().get(0).GetGodCard().setCardType(GodCardType.WIN);
            GameState.EndGame();
        }
    }
}
