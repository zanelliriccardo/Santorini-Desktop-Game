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

    /*public GameState(int numP) {
        Box[][] boxes = new Box[5][5];
        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes.length; j++)
                boxes[i][j] = new Box(true, 0);
        }
        b = new BoardGame(boxes);

        players = new Player[numP];
        podium = new Player[numP];
        workers = new Worker[numP * 2];
    }*/

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

    public void MemorizeNickname(String str)
    {
        for (int i = 0; i < players.length; i++)
            if (players[i] == null)
                players[i] = new Player(str);
    }

    public boolean CheckNickname(String nickname) {
        //da rifare evitando count

        return true;
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

    public void GodFactory (String[] gods) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
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

    public boolean SetInitialWorkerPosition(int getIndexPlayer, int[] pos1, int[] pos2)
    {
        if(b.GetStateBox(pos1)||b.GetStateBox(pos2))
            return false;

        b.setOccupant(pos1,workers[getIndexPlayer]);
        b.setOccupant(pos1,workers[getIndexPlayer+1]);
        workers[getIndexPlayer].SetPosition(pos1[0],pos1[1]);
        workers[getIndexPlayer+1].SetPosition(pos2[0],pos2[1]);
        return true;
    }

    public boolean DoMove(int[] newpos,Worker activeWorker)
    {
        int[] oldpos=activeWorker.GetPosition();

        if(b.IsAPossibleMove(newpos,oldpos))
        {
            activeWorker.SetPosition(newpos);
            b.ChangeState(newpos,activeWorker.GetProprietary().GetColor());
            b.ChangeState(oldpos);
            return true;
        }

        return false;
    }

    public boolean IsAPossibleMove(int[] newpos,int[] oldpos)
    {
        return b.IsAPossibleMove(newpos,oldpos);
    }

    public boolean DoBuild(int[] pos, Worker activeWorker)
    {
        int[] workerpos=activeWorker.GetPosition();

        if(b.IsAPossibleBuild(pos,workerpos))
        {
            b.DoBuild(pos);
            return true;
        }
        else
            return false;
    }

    public boolean HaveAPossibleMove(String nickname)
    {
        if(b.IsABlockedWorker(workers[GetIndexPlayer(nickname)].GetPosition())) {
            if (b.IsABlockedWorker(workers[GetIndexPlayer(nickname) + 1].GetPosition()))
                return false;
        }
        return true;
    }

    public boolean CheckPower(String str)
    {
        for(int i=0;i<players.length;i++) {
            //if(!players[i].GetGodCard().CheckMoment(players[trace], players[i], str))
                //return false;
        }

        return true;
    }

    public Worker GetOccupant(int[] pos)
    {
        return b.GetOccupant(pos);
    }


    public ArrayList PossibleMoves(int index)
    {
        ArrayList<int[]> possiblemoves = new ArrayList<int []>();

        ArrayList<Worker> worker_list=new ArrayList<Worker>();
        worker_list.add(workers[index]);

        int[] workerPosition=worker_list.get(0).GetPosition();

        for(int x=workerPosition[0]-1;x<=workerPosition[0]+1;x++)
            for(int y=workerPosition[1]-1;y<=workerPosition[1]+1;y++)
            {
                if(x==workerPosition[0]&&y==workerPosition[1])
                    continue;

                if(x>4||x<0)
                    continue;

                if(y>4||y<0)
                    continue;

            }
        return possiblemoves;
    }

    public BoardGame GetBoard()
    {
        return b;
    }

    public void SetActiveWorker(Worker worker)
    {
        activeworker=worker;
    }

    public void setNumplayer(int numplayer)
    {
        Box[][] boxes=new Box[5][5];
        for(int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes.length; j++)
                boxes[i][j] = new Box(true, 0);
        }

        b=new BoardGame(boxes);

        players = new Player[numplayer];
        workers = new Worker[numplayer*2];
    }

    public void EndGame()
    {

    }

    public void SetNumPlayer(int num_player)
    {
        Box[][] boxes = new Box[5][5];
        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes.length; j++)
                boxes[i][j] = new Box(true, 0);
        }
        b = new BoardGame(boxes);

        players = new Player[num_player];
        workers = new Worker[num_player * 2];
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

    public boolean CheckOpponentGod(Worker getActiveWorker, int[] getPos)
    {
        for (Player opponent : players)//!=giocatore attivo da fare
        {
            if(!opponent.GetGodCard().Move(b,getActiveWorker,getPos));
                return false;
        }
        return true;
    }
}
