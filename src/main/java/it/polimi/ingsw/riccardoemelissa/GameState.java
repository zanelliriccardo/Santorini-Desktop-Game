package it.polimi.ingsw.riccardoemelissa;


import it.polimi.ingsw.riccardoemelissa.elements.*;
import it.polimi.ingsw.riccardoemelissa.elements.Box;
import it.polimi.ingsw.riccardoemelissa.elements.card.Minotaur;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class GameState {
    private static ArrayList<Player> players = new ArrayList<Player>();
    private static Worker[] workers;
    private static int trace = 0;
    private static BoardGame b;
    private static boolean gameover=false;
    private static int num_players;

    public GameState()
    {
        Box[][] boxes=new Box[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                boxes[i][j]=new Box();
            }
        }
        b=new BoardGame(boxes);
    }
    /**
     *
     * @return
     */
    public static int GetPlayerNumber()
    {
        return players.size();
    }

    public static ArrayList<Player> GetPlayers()
    {
        return players;
    }

    public static int GetIndexPlayer(String nick)
    {
        for (int i = 0; i < players.size(); i++)
            if (players.get(i).GetNickname().equals(nick))
                return i;

        return -1;
    }

    public static boolean GameReady()
    {
        for (int i = 0; i < players.size(); i++)
            if (players.get(i) == null)
                return false;
        return true;
    }

    public static Worker GetWorkerToMove(String nick, int n)
    {
        return workers[GetIndexPlayer(nick) + n];
    }

    public static void SetTurnOrder() {
        //ArrayList<Player> array = new ArrayList<Player>(); //creo array

        /*for (int i = 0; i < players.size(); i++) {
            array.add(players.get(i));
        }

         */
        Collections.shuffle(players); //mescola array
        //players = array.toArray(new Player[]{});
    }

    public static void GodFactory () throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        JsonReader read_god = new JsonReader();
        String[] gods  = read_god.GodsInGame(players.size());

        for (int i = 0; i< gods.length; i++)
        {
            System.out.println(gods[i]);
            players.get(i).SetGodCard((God)Class.forName(gods[i]).getConstructor().newInstance());
            //players[i].SetGodCard((God)Class.forName(gods[i]).getConstructor().newInstance());
            //players[i].SetGodCard((God) (getClass().getClassLoader().loadClass(gods[i])).getConstructor().newInstance());
        }
    }

    public static void NextTurn ()
    {
       if (trace < players.size())
           trace ++;
       else trace = 0;
    }

    public static Player getActivePlayer()
    {
        if(players.isEmpty())
            return null;
        return players.get(trace);
    }

    public static BoardGame GetBoard()
    {
        return b;
    }

    public static void EndGame()
    {
        b.setGameOver(true);
        players=null;
    }

    private static void setGameOver(boolean b) {
        gameover=b;
    }

    public static void SetNumPlayer(int n) {
        num_players = n;
        for(int i =0; i<n; i++)
        {
            players.add(new Player("nome")); //cambiare nome
        }

        Box[][] boxes = new Box[5][5];
        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes.length; j++)
                boxes[i][j] = new Box(true, 0);
        }
        b = new BoardGame(boxes);
        //players = new Player[num_player];
        System.out.println("Inizializzazione array giocatori con dim = " + num_players);
        workers = new Worker[n * 2];

        b.custom_notifyAll();

        /*try {//spostare dopo che parte il gioco cioè qunado abbiamo tutti igiocatori
            GodFactory();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }*/

        /*System.out.println(players.length);
        for (int i = 0; i < players.length; i++) {
            God g=new Minotaur();
            players[i].SetGodCard(g);
        }*/
    }

    public static void NewPlayer(String str)
    {
        /*for(int i=0;i<players.size();i++)
            if(players.get(i)==null)

         */
        players.add(new Player(str));
    }

    public static void SetNewWorker(Worker ActiveWorker)
    {
        int index=GetIndexPlayer((ActiveWorker.GetProprietary().GetNickname()));
        if(workers[index*2]==null)
            workers[index*2]=ActiveWorker;
        workers[index*2+1]=ActiveWorker;


    }

    public static boolean CheckMove(Worker getActiveWorker, int[] getPos)
    {
        for (Player opponent : players)
        {
            if((opponent.GetNickname().compareTo(getActivePlayer().GetNickname())==0)&&opponent.GetGodCard().GetOpponentTurn())//check is an opponent && check opponent card act in active player turn
                //if(!opponent.GetGodCard().Move(b,getActiveWorker,getPos));//check move is possible for opponent card
                    return false;
        }
        if(!b.IsAdjacentBox(getActiveWorker.GetPosition(),getPos))//check newposition is adjacent at  actual worker position
            return false;
        return true;
    }

    public static boolean getGameOver()
    {
        return gameover;
    }

    public static void undoTurn() {
    }

    public static void RemovePlayer()
    {
        /*
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if(b.GetOccupant(i,j).GetProprietary().GetNickname().compareTo(getActivePlayer().GetNickname())==0)
                    b.removeWorker(i,j);
            }
        }

        for (int i = 0; i < players.size(); i++)
        {
            if(i==players.size()-1)
                players[i]=null;
            if(players[i].GetNickname().compareTo(getActivePlayer().GetNickname())==0)
                players[i]=players[i+1];
        }

         */


    }

    public static void UpdateBoard(BoardGame board)
    {
        b=board;
    }

    public static boolean IsPossibleMove(Worker activeWorker, int[] pos)
    {
        ArrayList<int[]> possibleCells_activeWorker =new ArrayList<>();
        possibleCells_activeWorker= checkMoves(b,activeWorker);
        return possibleCells_activeWorker.contains(pos);
    }

    public static boolean IsPossibleBuild(Worker activeWorker, int[] pos)
    {
        ArrayList<int[]> possibleCells_activeWorker =new ArrayList<>();
        possibleCells_activeWorker= checkBuilds(b,activeWorker);
        return possibleCells_activeWorker.contains(pos);
    }

    public static ArrayList<int[]> checkMoves(BoardGame board, Worker worker_toMove)
    {
        ArrayList<int[]> possiblemoves= worker_toMove.GetProprietary().GetGodCard().adjacentBoxNotOccupiedNotDome(board, worker_toMove.GetPosition());

        possiblemoves.removeIf(pos -> board.GetLevelBox(pos) - board.GetLevelBox(worker_toMove.GetPosition()) > 1);

        for (int[] pos: possiblemoves)
        {
            for (Player opponent : players)
            {
                if((opponent.GetNickname().compareTo(getActivePlayer().GetNickname())==0)&&opponent.GetGodCard().GetOpponentTurn())//check is an opponent && check opponent card act in active player turn
                    if(opponent.GetGodCard().Move(board, worker_toMove,pos)==CommandType.ERROR);//check move is possible for opponent card
                possiblemoves.remove(pos);
            }
        }
        return possiblemoves;
    }

    public static ArrayList<int[]> checkBuilds(BoardGame board, Worker builder)
    {
        ArrayList<int[]> possiblebuild=board.AdjacentBox(builder.GetPosition());

        possiblebuild.removeIf(pos -> board.GetLevelBox(pos) == 4);

        for (int[] pos: possiblebuild)
        {
            for (Player opponent : players)
            {
                if((opponent.GetNickname().compareTo(getActivePlayer().GetNickname())==0)&&opponent.GetGodCard().GetOpponentTurn())//check is an opponent && check opponent card act in active player turn
                    if(opponent.GetGodCard().Build(board,builder,pos)==CommandType.ERROR);//check build is possible for opponent card
                possiblebuild.remove(pos);
            }
        }
        return possiblebuild;
    }

    public static ArrayList<int[]> possibleMoves()
    {
        ArrayList<Worker> workers=getWorkers();
        ArrayList<int[]> possiblemoves = null;

        for (Worker w : workers)
        {
            possiblemoves.addAll(checkMoves(b,w));
        }
        return possiblemoves;
    }

    public static ArrayList<Worker> getWorkers()
    {
        ArrayList<Worker> workers=new ArrayList<Worker>();

        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if(b.GetOccupant(i,j).GetProprietary().GetNickname().compareTo(getActivePlayer().GetNickname())==0)
                    workers.add(b.GetOccupant(i,j));
            }
        }
        return workers;
    }

    public static Player getPlayer(String nickname)
    {
        for (Player p : players)
        {
            if (p.GetNickname().compareTo(nickname) == 0) return p;

        }
        return null;
    }
}
