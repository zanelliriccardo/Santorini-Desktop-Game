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
     * Return the player number
     */
    public static int getPlayerNumber()
    {
        return players.size();
    }

    /**
     * @return list of players in game
     */
    public static ArrayList<Player> getPlayers()
    {
        return players;
    }

    /**
     *
     * @param nick : nickname of the searched player
     * @return index of the player in the list
     */
    public static int getIndexPlayer(String nick)
    {
        for (int i = 0; i < players.size(); i++)
            if (players.get(i).getNickname().equals(nick))
                return i;

        return -1;
    }

    /**
     * This method control when every player is connected
     *
     * @return true if every player is connected, then the game starts
     */
    public static boolean gameReady()
    {
        for (Player p : players)
        {
            if (p.getNickname().compareTo("nome") == 0)
                return false;
        }
        return true;
    }

    /**
     * Initialize the god cards using json
     */
    public static void godFactory() {
        JsonReader read_god = new JsonReader();
        String[] gods_json  = new String[0];
        try {
            gods_json = read_god.godsInGame(players.size());
        } catch (FileNotFoundException | URISyntaxException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < players.size()*3; i=i+3) {
            try {
                players.get(i / 3).setGodCard((God) Class.forName(gods_json[i]).getConstructor().newInstance());
            } catch (InstantiationException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
            players.get(i / 3).setGodImagePath(gods_json[i + 1]);
            players.get(i / 3).getGodCard().setOpponentTrue(gods_json[i + 2]);
        }
    }

    /**
     * Change turn
     */
    public static void nextTurn()
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
    public static BoardGame getBoard()
    {
        return b;
    }

    /**
     * Set game over
     */
    public static void endGame()
    {
        b.setGameOver(true);
    }

    /**
     * initialize everything requires a number of player
     *
     * @param n number of player in the game
     */
    public static void setNumPlayer(int n) {
        num_players = n;

        for(int i =0; i<n; i++)
        {
            players.add(new Player("nome"));
            players.get(i).setColor(colors.get(i));
        }

        godFactory();

        Box[][] boxes = new Box[5][5];
        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes.length; j++)
                boxes[i][j] = new Box(true, 0);
        }

        b.setBoxes(boxes);
        workers = new Worker[n * 2];

    }

    /**
     * Set nickname of a new player
     *
     * @param str nickname
     */
    public static void newPlayer(String str)
    {
        for (Player p : players) {
            if(p.getNickname().compareTo("nome")==0)
            {
                p.setNickname(str.trim()+"#"+ getIndexPlayer("nome"));
                break;
            }
        }
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
     * Remove a player
     * @param player
     */
    public static void removePlayer(Player player)
    {
        players.remove(getIndexPlayer(player.getNickname()));
        if(trace>=players.size())
            trace=0;
    }

    /**
     * This method do a contains for an int array
     *
     * @param activeWorker
     * @param pos
     * @return
     */
    public static boolean isPossibleMove(Worker activeWorker, int[] pos)
    {
        ArrayList<int[]> possibleCells_activeWorker;
        possibleCells_activeWorker= checkMoves(b,activeWorker);

        for ( int[] i : possibleCells_activeWorker)
        {
            if(Arrays.equals(i, pos))
                return true;
        }
        return false;
    }

    /**
     * This method do a contains for an int array
     *
     * @param activeWorker
     * @param pos
     * @return
     */
    public static boolean isPossibleBuild(Worker activeWorker, int[] pos)
    {
        ArrayList<int[]> possibleCells_activeWorker;
        possibleCells_activeWorker= checkBuilds(b,activeWorker);

        for ( int[] i : possibleCells_activeWorker)
        {
            if(Arrays.equals(i, pos))
                return true;
        }
        return false;
    }

    /**
     * Control if the move is allowed
     *
     * @param board
     * @param worker_toMove
     * @return
     */
    public static ArrayList<int[]> checkMoves(BoardGame board, Worker worker_toMove)
    {
        ArrayList<int[]> possiblemoves= worker_toMove.getProprietary().getGodCard().adjacentBoxNotOccupiedNotDome(board, worker_toMove.getPosition());

        possiblemoves.removeIf(pos -> board.getLevelBox(pos) - board.getLevelBox(worker_toMove.getPosition()) > 1);

        ArrayList<int[]> removes=new ArrayList<>();

        for (int[] pos: possiblemoves)
        {
            for (Player opponent : getPlayers())
            {
                if((opponent.getNickname().compareTo(b.getActivePlayer().getNickname())!=0)&&opponent.getGodCard().getOpponentTurn())//check is an opponent && check opponent card act in active player turn
                    if(opponent.getGodCard().move(board, worker_toMove,pos)==CommandType.ERROR)//check move is possible for opponent card
                        removes.add(pos);
            }
        }
        possiblemoves.removeAll(removes);
        return possiblemoves;
    }

    /**
     * Control if the build is allowed
     * @param board
     * @param builder
     * @return
     */
    public static ArrayList<int[]> checkBuilds(BoardGame board, Worker builder)
    {
        ArrayList<int[]> possiblebuild=builder.getProprietary().getGodCard().adjacentBoxNotOccupiedNotDome(board,builder.getPosition());

        possiblebuild.removeIf(pos -> board.getLevelBox(pos) == 4);

        for (int[] pos: possiblebuild)
        {
            for (Player opponent : players)
            {
                if((opponent.getNickname().compareTo(getActivePlayer().getNickname())!=0)&&opponent.getGodCard().getOpponentTurn())//check is an opponent && check opponent card act in active player turn
                    if(opponent.getGodCard().build(board,builder,pos)==CommandType.ERROR)//check build is possible for opponent card
                        possiblebuild.remove(pos);
            }
        }
        return possiblebuild;
    }

    /**
     * Return the list of possible moves, if is empty the player lose
     *
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
     * Return the worker of the active player
     *
     * @return
     */
    public static ArrayList<Worker> getWorkers()
    {
        ArrayList<Worker> workers=new ArrayList<Worker>();
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if(!b.getStateBox(i,j))
                    if(b.getOccupantProprietary(i,j).getNickname().compareTo(b.getActivePlayer().getNickname())==0)
                        workers.add(b.getOccupant(i,j));
            }
        }
        return workers;
    }

    public static Player getPlayer(String nickname)
    {
        for (Player p : players)
        {
            if (p.getNickname().compareTo(nickname) == 0)
                return p;
        }
        return null;
    }

    public static int getNumPlayers() {
        return num_players;
    }


    public static void setActiveWorker(int[] getPos) {
        if(getPos==null)
            activeWorker=null;
        else
            activeWorker=GameState.getBoard().getOccupant(getPos);
    }

    public static Worker getActiveWorker() {
        return activeWorker;
    }

    public static void reset()
    {
        players = new ArrayList<Player>();
        Worker[] workers;
        trace = 0;
        b=new BoardGame();
        gameover=false;
        num_players=10;
        gods=new ArrayList<String>();
        godsImagePath=new ArrayList<>();
    }
}
