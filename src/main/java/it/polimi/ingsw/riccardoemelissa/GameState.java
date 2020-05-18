package it.polimi.ingsw.riccardoemelissa;

import it.polimi.ingsw.riccardoemelissa.reader.JsonReader;
import it.polimi.ingsw.riccardoemelissa.elements.*;
import it.polimi.ingsw.riccardoemelissa.elements.Box;


import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.*;

public class GameState {
    private static ArrayList<Player> players = new ArrayList<Player>();
    private static Worker[] workers;
    private static int trace = 0;
    private static BoardGame b=new BoardGame();
    private static boolean gameover=false;
    private static int num_players=10;
    private static ArrayList<String> gods=new ArrayList<String>();
    private static ArrayList<String> godsImagePath=new ArrayList<>();
    private static final ArrayList<String> colors= new ArrayList<String>(){
        {
            add("AQUAMARINE");
            add("MAGENTA");
            add("GOLD");
        }
    };
    private static Worker activeWorker=null;

    /**
     * return the player number
     */
    public static int GetPlayerNumber()
    {
        return players.size();
    }

    /**
     * @return list of players in game
     */
    public static ArrayList<Player> GetPlayers()
    {
        return players;
    }

    /**
     *
     * @param nick : nickname of the searched player
     * @return index of the player in the list
     */
    public static int GetIndexPlayer(String nick)
    {
        for (int i = 0; i < players.size(); i++)
            if (players.get(i).GetNickname().equals(nick))
                return i;

        return -1;
    }

    /**
     * this method control when every player is connected
     *
     * @return true if every player is connected, then the game starts
     */
    public static boolean GameReady()
    {
        for (Player p : players)
        {
            if (p.GetNickname().compareTo("nome") == 0)
                return false;
        }
        return true;
    }

    public static Worker GetWorkerToMove(String nick, int n)
    {
        return workers[GetIndexPlayer(nick) + n];
    }

    /**
     * shuffle the players
     */
    public static void SetTurnOrder()
    {
        Collections.shuffle(players); //mescola array
    }

    /**
     * initialize the god cards using json
     */
    public static void GodFactory () {
        JsonReader read_god = new JsonReader();
        String[] gods_json  = new String[0];
        try {
            gods_json = read_god.GodsInGame(players.size());
        } catch (FileNotFoundException | URISyntaxException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < players.size()*3; i=i+3)
        {
            try {
                players.get(i/3).SetGodCard((God) Class.forName(gods_json[i]).getConstructor().newInstance());
            } catch (InstantiationException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
            System.out.println("Classe : " + players.get(i/3).GetGodCard().getClass());
            players.get(i/3).setGodImagePath(gods_json[i+1]);
            players.get(i/3).GetGodCard().setOpponentTrue(gods_json[i+2]);

            System.out.println("God card : " + players.get(i/3).getGodImagePath() + "\nOpponent turn : "+ players.get(i/3).GetGodCard().GetOpponentTurn());
        }
    }

    /**
     * change turn
     */
    public static void NextTurn ()
    {
       if (trace < players.size()-1)
           trace ++;
       else
           trace = 0;

        b.setActivePlayer(players.get(trace));
    }

    /**
     * @return the player
     */
    public static Player getActivePlayer()
    {
        if(players.isEmpty())
            return null;
        return players.get(trace);
    }

    /**
     * @return the board
     */
    public static BoardGame GetBoard()
    {
        return b;
    }

    /**
     * set game over
     */
    public static void EndGame()
    {
        b.setGameOver(true);
    }

    private static void setGameOver(boolean b) {
        gameover=b;
    }

    /**
     * initialize everything requires a number of player
     *
     * @param n number of player in the game
     */
    public static void SetNumPlayer(int n) {
        num_players = n;

        for(int i =0; i<n; i++)
        {
            players.add(new Player("nome"));//cambiare nome
            players.get(i).SetColor(colors.get(i));
        }

        GodFactory();
        System.out.println("Player 1 : " + players.get(0).getGodImagePath() + "\n Player 2 : " + players.get(1).getGodImagePath());

        Box[][] boxes = new Box[5][5];
        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes.length; j++)
                boxes[i][j] = new Box(true, 0);
        }

        b.setBoxes(boxes);
        System.out.println("Inizializzazione array giocatori con dim = " + num_players);
        workers = new Worker[n * 2];

    }

    /**
     * set nickname of a new player
     * @param str nickname
     */
    public static void NewPlayer(String str)
    {
        for (Player p : players) {
            if(p.GetNickname().compareTo("nome")==0)
            {
                p.SetNickname(str.trim()+"#"+GetIndexPlayer("nome"));
                break;
            }
        }
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

    /**
     * check is gameover
     *
     * @return
     */
    public static boolean getGameOver()
    {
        return gameover;
    }

    /**
     * to do
     */
    public static void undoTurn() {
    }

    /**
     * to do
     * @param player
     */
    public static void RemovePlayer(Player player)
    {
        players.remove(GetIndexPlayer(player.GetNickname()));
        if(trace>=players.size())
            trace=0;
    }

    public static void UpdateBoard(BoardGame board)
    {
        b=board;
    }

    /**
     * this method do a contains for an int array
     *
     * @param activeWorker
     * @param pos
     * @return
     */
    public static boolean IsPossibleMove(Worker activeWorker, int[] pos)
    {
        ArrayList<int[]> possibleCells_activeWorker =new ArrayList<>();
        possibleCells_activeWorker= checkMoves(b,activeWorker);

        for ( int[] i : possibleCells_activeWorker)
        {
            if(Arrays.equals(i, pos))
                return true;
        }
        return false;
    }

    /**
     * this method do a contains for an int array
     *
     * @param activeWorker
     * @param pos
     * @return
     */
    public static boolean IsPossibleBuild(Worker activeWorker, int[] pos)
    {
        ArrayList<int[]> possibleCells_activeWorker =new ArrayList<>();
        possibleCells_activeWorker= checkBuilds(b,activeWorker);

        for ( int[] i : possibleCells_activeWorker)
        {
            if(Arrays.equals(i, pos))
                return true;
        }
        return false;
    }

    /**
     * control if the move is allowed
     *
     * @param board
     * @param worker_toMove
     * @return
     */
    public static ArrayList<int[]> checkMoves(BoardGame board, Worker worker_toMove)
    {
        ArrayList<int[]> possiblemoves= worker_toMove.GetProprietary().GetGodCard().adjacentBoxNotOccupiedNotDome(board, worker_toMove.GetPosition());

        possiblemoves.removeIf(pos -> board.GetLevelBox(pos) - board.GetLevelBox(worker_toMove.GetPosition()) > 1);

        //for (int[] pos: possiblemoves)
        for(int i = 0; i < possiblemoves.size(); i++)
        {
            for (Player opponent : players)
            {
                //System.out.println(" Nomi da confrontare " + opponent.GetNickname() + " , " + getActivePlayer().GetNickname());
                //System.out.println("Ris confronto : " + (opponent.GetNickname().compareTo(getActivePlayer().GetNickname())!=0));
                //System.out.println(" opp turn : " + opponent.GetGodCard().GetOpponentTurn());
                if((opponent.GetNickname().compareTo(getActivePlayer().GetNickname())!=0)&&opponent.GetGodCard().GetOpponentTurn())//check is an opponent && check opponent card act in active player turn
                    if(opponent.GetGodCard().Move(board, worker_toMove,possiblemoves.get(i)/*pos*/)==CommandType.ERROR) {//check move is possible for opponent card
                        System.out.println("Posizione da rimuovere é : ( " + Arrays.toString(possiblemoves.get(i)));
                        //possiblemoves.remove(pos);
                        possiblemoves.remove(i);
                    }
            }
        }
        return possiblemoves;
    }

    /**
     * control if the build is allowed
     * @param board
     * @param builder
     * @return
     */
    public static ArrayList<int[]> checkBuilds(BoardGame board, Worker builder)
    {
        ArrayList<int[]> possiblebuild=builder.GetProprietary().GetGodCard().adjacentBoxNotOccupiedNotDome(board,builder.GetPosition());

        possiblebuild.removeIf(pos -> board.GetLevelBox(pos) == 4);

        for (int[] pos: possiblebuild)
        {
            for (Player opponent : players)
            {
                if((opponent.GetNickname().compareTo(getActivePlayer().GetNickname())==0)&&opponent.GetGodCard().GetOpponentTurn())//check is an opponent && check opponent card act in active player turn
                    if(opponent.GetGodCard().Build(board,builder,pos)==CommandType.ERROR)//check build is possible for opponent card
                        possiblebuild.remove(pos);
            }
        }
        return possiblebuild;
    }

    /**
     * return the list of possible moves, if is empty the player lose
     * @return
     */
    public static ArrayList<int[]> possibleMoves()
    {
        ArrayList<Worker> workers=getWorkers();
        ArrayList<int[]> possiblemoves = new ArrayList<>();

        for (Worker w : workers)
        {
            possiblemoves.addAll(checkMoves(b,w));
        }
        return possiblemoves;
    }

    /**
     * return the worker of the active player
     * @return
     */
    public static ArrayList<Worker> getWorkers()
    {
        ArrayList<Worker> workers=new ArrayList<Worker>();

        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if(b.GetStateBox(i,j))
                    continue;
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
            if (p.GetNickname().compareTo(nickname) == 0)
                return p;
        }
        return null;
    }

    public static int GetNumPlayers() {
        return num_players;
    }


    public static void setActiveWorker(int[] getPos) {
        if(getPos==null)
            activeWorker=null;
        else
            activeWorker=GameState.GetBoard().GetOccupant(getPos);
    }

    public static Worker getActiveWorker() {
        return activeWorker;
    }
}
