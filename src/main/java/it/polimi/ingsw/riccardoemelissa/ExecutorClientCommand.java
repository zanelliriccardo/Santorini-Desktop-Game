package it.polimi.ingsw.riccardoemelissa;

import it.polimi.ingsw.riccardoemelissa.elements.*;

import java.util.ArrayList;

public class ExecutorClientCommand {

    private static GameState game=new GameState();


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
                //System.out.println("Ora devo creare giocatore");
                GameState.NewPlayer((String)cmd.GetObj());
                //System.out.println("Creazione giocatore");
                for (Player p : GameState.GetPlayers())
                    System.out.println(p.GetNickname());
                GameState.GetBoard().custom_notifyAll();
                break;
            case NEWWORKER:
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

                if(workers.size()==2)//sipuofarein modo piu intelligg
                    GameState.NextTurn();

                GameState.GetBoard().custom_notifyAll();

                break;
            case SETPOWER:
                GameState.getActivePlayer().GetGodCard().setIn_action((PowerType) cmd.GetObj());
                break;
            case DISCONNECTED://da rivedere
                GameState.EndGame();
                game=null;
                break;
            case MOVE:
                if(!GameState.IsPossibleMove((Worker)cmd.GetObj(),cmd.GetPos())) {
                    update(new Command(CommandType.LOSE, null, null));//dafare
                    return;
                }
                ((Worker)cmd.GetObj()).GetProprietary().GetGodCard().Move(GameState.GetBoard(),(Worker)cmd.GetObj(),cmd.GetPos());
                if(((Worker)cmd.GetObj()).GetProprietary().GetGodCard().getCardType()== GodCardType.WIN)
                    this.update(new Command(CommandType.WIN, GameState.getActivePlayer(),null));
                break;
            case BUILD:
                if(!GameState.IsPossibleBuild((Worker)cmd.GetObj(),cmd.GetPos())) {
                    this.update(new Command(CommandType.LOSE, null, null));//dafare
                    return;
                }
                ((Worker)cmd.GetObj()).GetProprietary().GetGodCard().Build(GameState.GetBoard(),(Worker)cmd.GetObj(),cmd.GetPos());
                break;
            case WIN:
                GameState.EndGame();
                break;
            case LOSE:
                GameState.RemovePlayer();
                break;
            case NEXTTURN:
                GameState.NextTurn();
                break;
            case RESET:
                GameState.undoTurn();
                break;
        }

        //GameState.GetBoard().custom_notifyAll();
    }
}
