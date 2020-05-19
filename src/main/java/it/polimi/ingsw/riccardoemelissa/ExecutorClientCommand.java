package it.polimi.ingsw.riccardoemelissa;

import it.polimi.ingsw.riccardoemelissa.elements.*;

import java.util.ArrayList;
import java.util.Arrays;

public class ExecutorClientCommand {

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

        switch (cmd.getType())
        {
            case MODE:
                GameState.SetNumPlayer((int) cmd.getObj());
                GameState.GetBoard().custom_notifyAll();
                break;
            case NICKNAME:
                GameState.NewPlayer((String)cmd.getObj());
                GameState.GetBoard().custom_notifyAll();
                break;
            case NEWWORKER:
                ((Worker)cmd.getObj()).setProprietary(GameState.getActivePlayer());
                ((Worker)cmd.getObj()).SetPosition(cmd.getPos());
                GameState.GetBoard().setOccupant(cmd.getPos(),(Worker)cmd.getObj());

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
                break;
            case DISCONNECTED://da rivedere
                GameState.EndGame();
                break;
            case MOVE:
                if(!GameState.IsPossibleMove((Worker)cmd.getObj(),cmd.getPos())) {
                    GameState.RemovePlayer(GameState.getActivePlayer());
                    return;
                }
                Worker move_worker=GameState.GetBoard().GetOccupant((((Worker) cmd.getObj()).GetPosition()));
                move_worker.GetProprietary().GetGodCard().setIn_action(((Worker) cmd.getObj()).GetProprietary().GetGodCard().getIn_action());

                move_worker.GetProprietary().GetGodCard().Move(GameState.GetBoard(),move_worker,cmd.getPos());

                System.out.println("move");

                if(checkMoves(GameState.GetBoard(),move_worker).isEmpty()&&GameState.getActivePlayer().GetGodCard().getCardType().isMove())
                    lose();
                if(checkBuilds(GameState.GetBoard(),move_worker).isEmpty()&&GameState.getActivePlayer().GetGodCard().getCardType().isBuild())
                    lose();

                if(GameState.GetBoard().GetLevelBox(move_worker.GetPosition())==3||GameState.getActivePlayer().GetGodCard().getCardType().isWin())
                {
                    move_worker.GetProprietary().GetGodCard().setCardType(GodCardType.WIN);
                    GameState.EndGame();
                    GameState.setActiveWorker(null);
                }
                else
                    GameState.setActiveWorker(cmd.getPos());

                GameState.GetBoard().custom_notifyAll();
                break;
            case BUILD:
                if(!GameState.IsPossibleBuild((Worker)cmd.getObj(),cmd.getPos())) {
                    GameState.RemovePlayer(GameState.getActivePlayer());
                    return;
                }
                Worker build_worker=GameState.GetBoard().GetOccupant((((Worker) cmd.getObj()).GetPosition()));
                build_worker.GetProprietary().GetGodCard().setIn_action(((Worker) cmd.getObj()).GetProprietary().GetGodCard().getIn_action());

                build_worker.GetProprietary().GetGodCard().Build(GameState.GetBoard(),build_worker,cmd.getPos());

                System.out.println("build");

                if(checkMoves(GameState.GetBoard(),build_worker).isEmpty()&&GameState.getActivePlayer().GetGodCard().getCardType().isMove())
                    lose();
                if(checkBuilds(GameState.GetBoard(),build_worker).isEmpty()&&GameState.getActivePlayer().GetGodCard().getCardType().isBuild())
                    lose();

                GameState.GetBoard().custom_notifyAll();
                break;
            case CHANGE_TURN:
                GameState.GetBoard().getActivePlayer().GetGodCard().resetCard();
                GameState.NextTurn();

                if(GameState.possibleMoves().isEmpty()&&GameState.GetBoard().getActivePlayer().GetGodCard().getCardType().isMove())
                    lose();
                else
                   GameState.GetBoard().getActivePlayer().GetGodCard().resetCard();


                GameState.setActiveWorker(null);
                GameState.GetBoard().custom_notifyAll();
                break;
        }

        //GameState.GetBoard().custom_notifyAll();
    }

    public void lose()
    {
        GameState.setActiveWorker(null);
        GameState.getActivePlayer().GetGodCard().setCardType(GodCardType.LOSE);
        ArrayList<Worker> workerToDelete= GameState.GetBoard().getWorkers();

        for (Worker w : workerToDelete) {
            GameState.GetBoard().removeWorker(w.GetPosition());
        }
        GameState.GetBoard().custom_notifyAll();

        GameState.RemovePlayer(GameState.getActivePlayer());

        if(GameState.GetPlayers().size()==1) {
            GameState.GetPlayers().get(0).GetGodCard().setCardType(GodCardType.WIN);
            GameState.EndGame();
        }
    }

    public ArrayList<int[]> checkBuilds(BoardGame board, Worker builder)
    {
        ArrayList<int[]> possiblebuild=builder.GetProprietary().GetGodCard().adjacentBoxNotOccupiedNotDome(board,builder.GetPosition());

        possiblebuild.removeIf(pos -> board.GetLevelBox(pos) == 4);

        for (int[] pos: possiblebuild)
        {
            for (Player opponent : GameState.GetPlayers())
            {
                if((opponent.GetNickname().compareTo(GameState.getActivePlayer().GetNickname())!=0)&&opponent.GetGodCard().GetOpponentTurn())//check is an opponent && check opponent card act in active player turn
                    if(opponent.GetGodCard().Build(board,builder,pos)==CommandType.ERROR)//check build is possible for opponent card
                        possiblebuild.remove(pos);
            }
        }
        return possiblebuild;
    }

    public ArrayList<int[]> checkMoves(BoardGame board, Worker worker_toMove)
    {
        ArrayList<int[]> possiblemoves= worker_toMove.GetProprietary().GetGodCard().adjacentBoxNotOccupiedNotDome(board, worker_toMove.GetPosition());

        possiblemoves.removeIf(pos -> board.GetLevelBox(pos) - board.GetLevelBox(worker_toMove.GetPosition()) > 1);

        //for (int[] pos: possiblemoves)
        for(int i = 0; i < possiblemoves.size(); i++)
        {
            for (Player opponent : GameState.GetPlayers())
            {
                //System.out.println(" Nomi da confrontare " + opponent.GetNickname() + " , " + getActivePlayer().GetNickname());
                //System.out.println("Ris confronto : " + (opponent.GetNickname().compareTo(getActivePlayer().GetNickname())!=0));
                //System.out.println(" opp turn : " + opponent.GetGodCard().GetOpponentTurn());
                if((opponent.GetNickname().compareTo(GameState.getActivePlayer().GetNickname())!=0)&&opponent.GetGodCard().GetOpponentTurn())//check is an opponent && check opponent card act in active player turn
                    if(opponent.GetGodCard().Move(board, worker_toMove,possiblemoves.get(i)/*pos*/)==CommandType.ERROR) {//check move is possible for opponent card
                        System.out.println("Posizione da rimuovere é : ( " + Arrays.toString(possiblemoves.get(i)));
                        //possiblemoves.remove(pos);
                        possiblemoves.remove(i);
                    }
            }
        }
        return possiblemoves;
    }

    public ArrayList<Worker> getWorkers()
    {
        ArrayList<Worker> workers=new ArrayList<Worker>();

        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if(GameState.GetBoard().GetStateBox(i,j))
                    continue;
                if(GameState.GetBoard().GetOccupant(i,j).GetProprietary().GetNickname().compareTo(GameState.getActivePlayer().GetNickname())==0)
                    workers.add(GameState.GetBoard().GetOccupant(i,j));
            }
        }
        return workers;
    }
}
