package it.polimi.ingsw.riccardoemelissa;

import elements.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class GameState {
    private static Player[] players=new Player[1];
    private static Worker[] workers;
    private static int trace = -1;
    private static BoardGame b;
    private static Worker activeworker;
    private boolean gameover=false;

    /**
     *
     * @return
     */
    public int GetPlayerNumber()
    {
        return players.length;
    }

    public Player[] GetPlayers()
    {
        return players;
    }

    public int GetIndexPlayer(String nick)
    {
        for (int i = 0; i < players.length; i++)
            if (players[i].GetNickname() == nick)
                return i;

        return -1;
    }

    public boolean GameReady()
    {
        for (int i = 0; i < players.length; i++)
            if (players[i] == null)
                return false;
        return true;
    }

    public Worker GetWorkerToMove(String nick, int n)
    {
        return workers[GetIndexPlayer(nick) + n];
    }

    public void SetProprietaryWorker() {

        for (int i = 0; i < workers.length; i = i + 2) {
            workers[i] = new Worker(players[i]);
            workers[i + 1] = new Worker(players[i]);
        }
    }

    public void SetTurnOrder() {
        ArrayList<Player> array = new ArrayList<Player>(); //creo array

        for (int i = 0; i < players.length; i++) {
            array.add(players[i]);
        }
        Collections.shuffle(array); //mescola array
        players = array.toArray(new Player[]{});
    }

    //generazione di diversi numeri casuali in numero uguale a quello dei giocatori
    //e da un range uguale al numero di carte
    /*public int[] uniqueRandomNumbers() {
        ArrayList<Integer> list = new ArrayList<Integer>();

        int[] random = new int[players.length];

        for (int i = 1; i < gods_name.length; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        for (int i = 0; i < players.length; i++) {
            random[i] = list.get(i);
        }
        return random;
    }

    public void GodsChosen() {
        int[] random_numbers = uniqueRandomNumbers();

        for (int i = 0; i < random_numbers.length; i++) {
            //players[i].SetGodCard(GodFactory.getGod(gods_name[random_numbers[i]]));
        }
    } */

    public void GodFactory () throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        JsonReader read_god = new JsonReader();
        String[] gods  = read_god.GodsInGame(players.length);

        for (int i = 0; i< gods.length; i++)
        {
            //players[i].SetGodCard((God)Class.forName(gods[i]).newInstance());
            players[i].SetGodCard((God)Class.forName(gods[i]).getDeclaredConstructor().newInstance());
        }
    }

    public void NextTurn ()
    {
       if (trace < players.length)
           trace ++;
       else trace = 0;
    }

    public Player GetActivePlayer()
    {
        while(players[trace]==null)
            NextTurn();
        return players[trace];
    }

    public Worker GetActiveWorker()
    {
        return activeworker;
    }

    public BoardGame GetBoard()
    {
        return b;
    }

    public void SetActiveWorker(Worker worker)
    {
        activeworker=worker;
    }

    public boolean EndGame(Player activePlayer)
    {
        setGameOver(true);
        if(activePlayer==null)
            return true;
        else
            return false;

    }

    private void setGameOver(boolean b) {
        gameover=b;
    }

    public void SetNumPlayer(int num_player) {
        Box[][] boxes = new Box[5][5];
        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes.length; j++)
                boxes[i][j] = new Box(true, 0);
        }
        b = new BoardGame(boxes);

        players = new Player[num_player];
        workers = new Worker[num_player * 2];

        try {
            GodFactory();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void NewPlayer(String str)
    {
        for(int i=0;i<players.length;i++)
            if(players[i]==null)
                players[i]=new Player(str);
    }

    public void SetNewWorker(Worker ActiveWorker)
    {
        int index=GetIndexPlayer((ActiveWorker.GetProprietary().GetNickname()));
        if(workers[index*2]==null)
            workers[index*2]=ActiveWorker;
        workers[index*2+1]=ActiveWorker;
    }

    public boolean CheckMove(Worker getActiveWorker, int[] getPos)
    {
        for (Player opponent : players)
        {
            if((opponent.GetNickname().compareTo(GetActivePlayer().GetNickname())==0)&&opponent.GetGodCard().GetOpponentTurn())//check is an opponent && check opponent card act in active player turn
                //if(!opponent.GetGodCard().Move(b,getActiveWorker,getPos));//check move is possible for opponent card
                    return false;
        }
        if(!b.IsAdjacentBox(getActiveWorker.GetPosition(),getPos))//check newposition is adjacent at  actual worker position
            return false;
        return true;
    }

    public boolean checkBuild(Worker getActiveWorker, int[] getPos)
    {
        return b.IsAdjacentBox(activeworker.GetPosition(),getPos);
    }

    public boolean getGameOver()
    {
        return gameover;
    }

    public void undoTurn() {
    }

    public void RemovePlayer()
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if(b.GetOccupant(i,j).GetProprietary().GetNickname().compareTo(GetActivePlayer().GetNickname())==0)
                    b.removeWorker(i,j);
            }
        }

        for (int i = 0; i < players.length; i++)
        {
            if(i==players.length-1)
                players[i]=null;
            if(players[i].GetNickname().compareTo(GetActivePlayer().GetNickname())==0)
                players[i]=players[i+1];
        }


    }

    public void UpdateBoard(BoardGame board)
    {
        b=board;
    }
}
