package it.polimi.ingsw.riccardoemelissa;

import elements.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.*;

import java.util.*;

public class GameState {
    private static final int dimensiongame=5;
    private Player[] players=new Player[1];
    private Player[] podium;
    private Worker[] workers;
    private int count = 0;
    private String[] gods_name = {"Apollo", "Artemis", "Athena", "Atlas", "Demeter", "Hephaestus", "Minotaur", "Pan", "Prometheus"};
    private int trace = -1;
    private BoardGame b;

    public GameState(int numP)
    {
        Box[][] boxes=new Box[dimensiongame][dimensiongame];
        for(int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes.length; j++)
                boxes[i][j] = new Box(true, 0);
        }
        b=new BoardGame(boxes);

        players = new Player[numP];
        podium = new Player[numP];
        //god = new God[numP];
        workers = new Worker[numP*2];
    }

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
        for(int i=0;i<players.length;i++)
            if(players[i].GetNickname()==nick)
                return i;

        return -1;
    }

    public boolean GameReady()
    {
        for(int i=0;i<players.length;i++)
            if(players[i]==null)
                return false;

        return true;
    }

    public Worker GetWorkerToMove(String nick, int n)
    {
        return workers[GetIndexPlayer(nick)+n];
    }

    public void MemorizeNickname(String str)
    {
        for (int i=0;i<players.length;i++)
            if(players[i]==null)
                players[i]=new Player(str);
    }

    //public void NewPlayer()
    {
        /*if (count == 0) {
            System.out.println("Enter the number of players");
            InputStreamReader reader = new InputStreamReader(System.in);
            BufferedReader myInput = new BufferedReader(reader);
            int n = 0;
            try {
                n = Integer.parseInt(myInput.readLine());
            } catch (IOException e) {
                System.out.println("An error has occurred: " + e);
                System.exit(-1);
            }
            System.out.println("You decided to play with " + n + " players ");

            players = new Player[n];
            workers = new Worker[2 * n];
        }
        */

       /* String str = ChooseNickname(in, out);

        if (CheckNickname(str)) {
            System.out.println("Your nickname is : " + str);

            Player p = new Player(str);
            players[count] = p;
            count++;
        } else {
            System.out.println("This nickname is already taken");
            str = ChooseNickname(in, out);
            while (!CheckNickname(str)) {
                System.out.println("This nickname is already taken");
                str = ChooseNickname(in, out);
            }
        }*/
    }

    public boolean CheckNickname(String nickname)
    {
        for (int i = 0; i < count; i++) {
            if (nickname.equals(players[i]))
                return false;
        }
        return true;
    }

    /*public void SetTurnOrder() {
        Random r = new Random();
        int first = r.nextInt(players.length);

        Player temp = players[0];
        players[0] = players[first];
        players[first] = temp;

        if (players.length == 3) {
            int second = r.nextInt(1) + 1;
            temp = players[1];
            players[1] = players[second];
            players[second] = temp;
        }
    }
     */

    public void SetProprietaryWorker()
    {

        for(int i=0;i<workers.length;i=i+2)
        {
            workers[i]=new Worker(players[i]);
            workers[i+1]=new Worker(players[i]);
        }
    }

    public void SetTurnOrder()
    {
        ArrayList<Player> array = new ArrayList<Player>(); //creo array

        for (int i = 0; i < players.length; i++) {
            array.add(players[i]);
        }
        Collections.shuffle(array); //mescola array
        players = array.toArray(new Player[]{});
    }

    //god cards are chosen in a random way. The server draws three numbers between 0 and 8 which correspond to the nine god's names
    /*public int[] RandomNumbers()
    {
        Random r = new Random(System.currentTimeMillis());

        int [] random_numbers = new int[players.length];

        for (int i = 0; i < players.length; i++)
        {
            random_numbers[i]= r.nextInt(9);
        }
        return random_numbers;
    }
    */

    /*public boolean CheckRandomNumbers (int[] a)
    {
        for (int i = 0; i<players.length; i++)
        {
            for(int j = i+1; j< players.length; j++)
                if (a[i]==a[j])
                    return false;

        }
        return true;
    }*/

    //generazione di diversi numeri casuali in numero uguale a quello dei giocatori
    //e da un range uguale al numero di carte
    public int[] uniqueRandomNumbers()
    {
        ArrayList<Integer> list = new ArrayList<Integer>();

        int[] random=new int[players.length];

        for (int i=1; i<gods_name.length; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        for (int i=0; i<players.length; i++) {
            random[i]=list.get(i);
        }
        return random;
    }

    public void GodsChosen ()
    {
        int [] random_numbers = uniqueRandomNumbers();

        for (int i = 0; i < random_numbers.length; i++) {
            players[i].SetGodCard(GodFactory.getGod(gods_name[random_numbers[i]]));
        }
    }

    public void StartWorker ()
    {
        System.out.println("Enter initial position");

        System.out.println("x : ");
        int x = readCoordinate();

        System.out.println("y : ");
        int y = readCoordinate();

        System.out.println("You decided to position your worker in the box ( " + x + "," + y + " )");
    }

    public int readCoordinate ()
    {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader myInput = new BufferedReader(reader);
        int n = 0;
        try {
            n = Integer.parseInt(myInput.readLine());
        } catch (IOException e) {
            System.out.println("An error has occurred: " + e);
            System.exit(-1);
        }
        return n;
    }

    public void NextTurn ()
    {
       if (trace < players.length)
           trace ++;
       else trace = 0;
    }

    public String GetActivePlayer()
    {
        while(players[trace]==null)
            NextTurn();
        return players[trace].GetNickname();
    }

    public void SetInitialWorkerPosition(int getIndexPlayer, int[] pos1, int[] pos2)
    {
        String color="\u001B[3"+(getIndexPlayer+1)+"m";
        workers[getIndexPlayer].SetColor(color);
        workers[getIndexPlayer+1].SetColor(color);
        workers[getIndexPlayer].SetPosition(pos1[0],pos1[1]);
        workers[getIndexPlayer+1].SetPosition(pos2[0],pos2[1]);
        b.ChangeState(pos1,color);
        b.ChangeState(pos2,color);
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
        else
            return false;
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

    public void GetBoard()
    {
        String[] numbers = {" ", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        for (int i = 0 ; i <= dimensiongame ; i++){
            for (int j = 0 ; j <= dimensiongame ; j++){

                if (i == 0)
                {
                    System.out.print(numbers[j]);
                }
                else if (j == 0)
                {
                    System.out.print(numbers[i]);
                }
                else {
                    if(!b.GetStateBox(i,j))
                        System.out.println(b.GetColor(i,j));

                    if(b.GetLevelBox(i,j)==4)
                        System.out.print("D");
                    else
                        System.out.print(b.GetLevelBox(i,j));

                    System.out.println("\u001B[0m");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public boolean Win(int[] pos)
    {
        return b.GetLevelBox(pos)==3;
    }

    public void AddWinner(Player winner)
    {
        int i=0;
        for(;i<players.length;i++)
            if(players[i].GetNickname()==winner.GetNickname()) {
                for (int j = 0; j < podium.length; j++) {
                    podium[j] = winner;
                }
                break;
            }


        for(;i<players.length-1;i++)
        {
            players[i]=players[i+1];
        }

        players[i]=null;
    }

    public boolean HaveAPossibleMove(String nickname)
    {
        if(b.IsABlockedWorker(workers[GetIndexPlayer(nickname)].GetPosition())) {
            if (b.IsABlockedWorker(workers[GetIndexPlayer(nickname) + 1].GetPosition()))
                return true;
        }
        return false;
    }
}
