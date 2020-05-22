package it.polimi.ingsw.riccardoemelissa;

import it.polimi.ingsw.riccardoemelissa.elements.*;

import java.util.ArrayList;

public class ExecutorClientCommand {

    /**
     * Manage command received from clients
     *
     * This method take an argument that made by a command type(it corresponding to an action
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
                GameState.setNumPlayer((int) cmd.getObj());
                GameState.getBoard().custom_notifyAll();
                break;
            case NICKNAME:
                GameState.newPlayer((String)cmd.getObj());
                //GameState.getBoard().custom_notifyAll();
                break;
            case NEWWORKER:
                ((Worker)cmd.getObj()).setProprietary(GameState.getActivePlayer());
                ((Worker)cmd.getObj()).setPosition(cmd.getPos());
                GameState.getBoard().setOccupant(cmd.getPos(),(Worker)cmd.getObj());

                ArrayList<Worker> workers=new ArrayList<Worker>();

                for (int i = 0; i < 5; i++)
                {
                    for (int j = 0; j < 5; j++)
                    {
                        if(!GameState.getBoard().getStateBox(i,j))
                            if(GameState.getBoard().getOccupant(i,j).getProprietary().getNickname().compareTo(GameState.getActivePlayer().getNickname())==0)
                                workers.add(GameState.getBoard().getOccupant(i,j));
                    }
                }

                if(workers.size()==2)
                    GameState.nextTurn();

                GameState.getBoard().custom_notifyAll();
                break;
            case DISCONNECTED:
                GameState.endGame();
                GameState.getBoard().custom_notifyAll();
                break;
            case MOVE:
                if(!GameState.isPossibleMove((Worker)cmd.getObj(),cmd.getPos())) {
                    GameState.removePlayer(GameState.getActivePlayer());
                    return;
                }
                Worker move_worker=GameState.getBoard().getOccupant((((Worker) cmd.getObj()).getPosition()));
                move_worker.getProprietary().getGodCard().setIn_action(((Worker) cmd.getObj()).getProprietary().getGodCard().getIn_action());

                move_worker.getProprietary().getGodCard().move(GameState.getBoard(),move_worker,cmd.getPos());

                if(checkMoves(GameState.getBoard(),move_worker).isEmpty()&&GameState.getActivePlayer().getGodCard().getCardType().isMove())
                    lose();
                if(checkBuilds(GameState.getBoard(),move_worker).isEmpty()&&GameState.getActivePlayer().getGodCard().getCardType().isBuild())
                    lose();

                if(GameState.getBoard().getLevelBox(move_worker.getPosition())==3||GameState.getActivePlayer().getGodCard().getCardType().isWin())
                {
                    move_worker.getProprietary().getGodCard().setCardType(GodCardType.WIN);
                    GameState.endGame();
                    GameState.setActiveWorker(null);
                }
                else
                    GameState.setActiveWorker(cmd.getPos());

                GameState.getBoard().custom_notifyAll();
                break;
            case BUILD:
                if(!GameState.isPossibleBuild((Worker)cmd.getObj(),cmd.getPos())) {
                    GameState.removePlayer(GameState.getActivePlayer());
                    return;
                }
                Worker build_worker=GameState.getBoard().getOccupant((((Worker) cmd.getObj()).getPosition()));
                build_worker.getProprietary().getGodCard().setIn_action(((Worker) cmd.getObj()).getProprietary().getGodCard().getIn_action());

                build_worker.getProprietary().getGodCard().build(GameState.getBoard(),build_worker,cmd.getPos());

                if(checkMoves(GameState.getBoard(),build_worker).isEmpty()&&GameState.getActivePlayer().getGodCard().getCardType().isMove())
                    lose();
                if(checkBuilds(GameState.getBoard(),build_worker).isEmpty()&&GameState.getActivePlayer().getGodCard().getCardType().isBuild())
                    lose();

                GameState.setActiveWorker(((Worker)cmd.getObj()).getPosition());
                GameState.getBoard().custom_notifyAll();
                break;
            case CHANGE_TURN:
                GameState.getBoard().getActivePlayer().getGodCard().resetCard();
                GameState.nextTurn();

                if(GameState.possibleMoves().isEmpty()&&GameState.getBoard().getActivePlayer().getGodCard().getCardType().isMove())
                    lose();
                else
                   GameState.getBoard().getActivePlayer().getGodCard().resetCard();


                GameState.setActiveWorker(null);
                GameState.getBoard().custom_notifyAll();
                break;
        }

        //GameState.GetBoard().custom_notifyAll();
    }

    public void lose()
    {
        GameState.setActiveWorker(null);
        GameState.getActivePlayer().getGodCard().setCardType(GodCardType.LOSE);
        ArrayList<Worker> workerToDelete= GameState.getBoard().getWorkers();

        for (Worker w : workerToDelete) {
            GameState.getBoard().removeWorker(w.getPosition());
        }
        GameState.getBoard().custom_notifyAll();

        GameState.removePlayer(GameState.getActivePlayer());

        if(GameState.getPlayers().size()==1) {
            GameState.getPlayers().get(0).getGodCard().setCardType(GodCardType.WIN);
            GameState.endGame();
        }
    }

    public ArrayList<int[]> checkBuilds(BoardGame board, Worker builder)
    {
        ArrayList<int[]> possiblebuild=builder.getProprietary().getGodCard().adjacentBoxNotOccupiedNotDome(board,builder.getPosition());

        possiblebuild.removeIf(pos -> board.getLevelBox(pos) == 4);

        for (int[] pos: possiblebuild)
        {
            for (Player opponent : GameState.getPlayers())
            {
                if((opponent.getNickname().compareTo(GameState.getActivePlayer().getNickname())!=0)&&opponent.getGodCard().getOpponentTurn())//check is an opponent && check opponent card act in active player turn
                    if(opponent.getGodCard().build(board,builder,pos)==CommandType.ERROR)//check build is possible for opponent card
                        possiblebuild.remove(pos);
            }
        }
        return possiblebuild;
    }

    public ArrayList<int[]> checkMoves(BoardGame board, Worker worker_toMove)
    {
        ArrayList<int[]> possiblemoves= worker_toMove.getProprietary().getGodCard().adjacentBoxNotOccupiedNotDome(board, worker_toMove.getPosition());

        possiblemoves.removeIf(pos -> board.getLevelBox(pos) - board.getLevelBox(worker_toMove.getPosition()) > 1);

        ArrayList<int[]> removes=new ArrayList<>();

        for (int[] pos: possiblemoves)
        {
            for (Player opponent : GameState.getPlayers())
            {
                if((opponent.getNickname().compareTo(GameState.getBoard().getActivePlayer().getNickname())!=0)&&opponent.getGodCard().getOpponentTurn())//check is an opponent && check opponent card act in active player turn
                    if(opponent.getGodCard().move(board, worker_toMove,pos)==CommandType.ERROR)//check move is possible for opponent card
                        removes.add(pos);
            }
        }
        possiblemoves.removeAll(removes);
        return possiblemoves;
    }
}
