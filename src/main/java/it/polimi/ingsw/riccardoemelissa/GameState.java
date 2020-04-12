package it.polimi.ingsw.riccardoemelissa;

import elements.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.*;

import java.util.*;

public class GameState {
    private Player[] players=new Player[1];
    //private God[] god;
    private Player[] podium;
    private Worker[] workers;
    private int count = 0;
    private String[] gods_name = {"Apollo", "Artemis", "Athena", "Atlas", "Demeter", "Hephaestus", "Minotaur", "Pan", "Prometheus"};
    private int trace = -1;
    private Box[][] boxes=new Box[5][5];
    private BoardGame b;

    public GameState(int numP)
    {
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

    public synchronized void MemorizeNickname(String str)//non sono sicuro su syncronized
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

    public void SetProprietaryWorker() {

        for(int i=0;i<workers.length;i=i+2)
        {
            workers[i]=new Worker(players[i]);
            workers[i+1]=new Worker(players[i]);
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

    public Player TurnTrace ()
    {
        //utilizzare lista o for each cosi evitiamo questo metodo
       if (trace < players.length)
           trace ++;
       else trace = 0;
       return players[trace];
    }

    public String GetActivePlayer()
    {
        return TurnTrace().GetNickname();
    }
}
