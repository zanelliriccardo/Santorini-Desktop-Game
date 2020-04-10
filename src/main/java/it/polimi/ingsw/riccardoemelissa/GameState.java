package it.polimi.ingsw.riccardoemelissa;

import elements.Player;
import elements.Worker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.*;

import java.util.*;

public class GameState {
    private Player[] players;
    private String[] god;
    private Player[] podium;
    private Worker[] workers;
    private int count = 0;
    private String[] gods_name = {"Apollo", "Artemis", "Athena", "Atlas", "Demeter", "Hephaestus", "Minotaur", "Pan", "Prometheus"};
    private int trace = 0;

    public String ChooseNickname() {
        System.out.println("Enter your nickname\n");
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader myInput = new BufferedReader(reader);
        String str = new String();
        try {
            str = myInput.readLine();
        } catch (IOException e) {
            System.out.println("An error has occured: " + e);
            System.exit(-1);
        }
        return str;
    }

    public void NewPlayer() {
        if (count == 0) {
            System.out.println("Enter the number of players");
            InputStreamReader reader = new InputStreamReader(System.in);
            BufferedReader myInput = new BufferedReader(reader);
            int n = 0;
            try {
                n = Integer.parseInt(myInput.readLine());
            } catch (IOException e) {
                System.out.println("An error has occured: " + e);
                System.exit(-1);
            }
            System.out.println("You decided to play with " + n + " players ");

            players = new Player[n];
            workers = new Worker[2 * n];
        }

        String str = ChooseNickname();

        if (CheckNickname(str)) {
            System.out.println("Your nickname is : " + str);

            Player p = new Player(str);
            players[count] = p;
            count++;
        } else {
            System.out.println("This nickname is already taken");
            str = ChooseNickname();
            while (!CheckNickname(str)) {
                System.out.println("This nickname is already taken");
                str = ChooseNickname();
            }
        }
    }

    public boolean CheckNickname(String nickname) {
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
        workers[0].SetProprietary(players[0]);
        workers[1].SetProprietary(players[0]);
        workers[2].SetProprietary(players[1]);
        workers[3].SetProprietary(players[1]);

        if (players.length == 3) {
            workers[4].SetProprietary(players[2]);
            workers[5].SetProprietary(players[2]);
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
    public int[] RandomNumbers()
    {
        Random r = new Random();
        r.setSeed(System.currentTimeMillis());
        int [] random_numbers = new int[players.length];

        for (int i = 0; i < players.length; i++)
        {
            random_numbers[i]= r.nextInt(9);
        }
        return random_numbers;
    }

    public boolean CheckRandomNumbers (int[] a)
    {
        for (int i = 0; i<players.length; i++)
        {
            for(int j = i+1; j< players.length; j++)
                if (a[i]==a[j])
                    return false;

        }
        return true;
    }

    public void GodsChosen ()
    {
        int [] random_numbers = RandomNumbers();

        if (CheckRandomNumbers(random_numbers)) {
            for (int i = 0; i < players.length; i++) {
                god[i] = gods_name[random_numbers[i]];
            }
        }
    }

    public void Start ()
     {
        System.out.println("Enter initial position");
        System.out.println("x : ");
         int x = Coordinate();

         System.out.println("y : ");
         int y = Coordinate();

        System.out.println("You decided to position your worker in the box ( " + x + "," + y + " )");
    }

    public int Coordinate ()
    {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader myInput = new BufferedReader(reader);
        int n = 0;
        try {
            n = Integer.parseInt(myInput.readLine());
        } catch (IOException e) {
            System.out.println("An error has occured: " + e);
            System.exit(-1);
        }
        return n;
    }

    public Player TurnTrace ()
    {
       if (trace < players.length)
           trace ++;
       else trace = 0;
       return players[trace];
    }

}
