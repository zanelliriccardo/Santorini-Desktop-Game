package it.polimi.ingsw.riccardoemelissa;

import elements.Player;
import elements.Worker;

import javax.naming.NoInitialContextException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class ClientHandler implements Runnable,Observer {
    private String nickname;
    private Message m;
    private ObjectOutputStream oos;
    private GameState game;

    public ClientHandler(Socket socket) throws IOException {

        m=new Message(socket);
        oos=new ObjectOutputStream(socket.getOutputStream());
    }

    public void run() {
        try {
            if(m==null)
            {
                return;
            }

            nickname=ChooseNickname(m);
            m.OutMessage("Waiting for other players...");

            while (true)
            {
                try {
                    wait(500);
                }
                catch (InterruptedException i) {
                    if (nickname.compareTo(game.GetActivePlayer())==0) {
                        {
                            int[] pos1;
                            int[] pos2;

                            m.OutMessage("Choose where position first worker");
                            pos1=m.GetInputPosition();

                            if(!game.GetBoard().GetStateBox(pos1))//fare metodo
                            {
                                m.OutMessage("Posizione worker n°1 già occupata");
                                continue;
                            }

                            m.OutMessage("Choose where position second worker");
                            pos2=m.GetInputPosition();

                            if(!game.GetBoard().GetStateBox(pos2))
                            {
                                m.OutMessage("Posizione worker n°2 già occupata");
                                continue;
                            }
                            if(!game.SetInitialWorkerPosition(game.GetIndexPlayer(nickname), pos1, pos2))
                                continue;;
                            break;
                        }
                    }
                }
            }

            game.NextTurn();

            m.OutMessage("Waiting for your turn...");

            while(true) {
                try
                {
                    wait(500);
                }
                catch (Exception e) {
                    if (nickname.compareTo(game.GetActivePlayer())==0) {
                        Worker activeWorker = null;
                        int[] pos=new int[2];

                        //ArrayList<int[][]> possiblemovesfirstw=App.g.PossibleMoves( App.g.GetIndexPlayer(nickname)*2);//intervallo boardgame da controllare
                        //ArrayList<int[][]> possiblemovessecondw=App.g.PossibleMoves( App.g.GetIndexPlayer(nickname)*2+1);

                        /*if(possiblemovesfirstw==null&&possiblemovessecondw==null)//se non mossa possibile --> SCONFITTA
                        {
                            App.g.Lose(nickname);
                            App.g.NextTurn();
                            break;
                        }*/

                        game.GetBoard();
                        m.OutMessage("Your turn!");

                        while (true) {
                            m.OutMessage("Which worker do you want to move?");
                            int num_worker=m.WhichWorker();
                            if(num_worker==0)
                            {
                                m.OutMessage("Error, please retry");
                                continue;
                            }
                            activeWorker = game.GetWorkerToMove(nickname, n-1);
                            game.SetActiveWorker(activeWorker);
                        }



                        while (true) {
                            pos=m.GetInputPosition();

                            if(game.DoMove(pos,activeWorker))
                            {
                                break;
                            }
                        }

                        //build

                        if(App.g.CheckPower("build"))
                        {

                        }

                        while (true) {
                            m.WhereBuild(/*out*/);

                            pos=m.GetInputPosition();

                            if(App.g.DoBuild(pos,activeWorker))
                            {
                                m.Success(out);
                                break;
                            }
                            m.Failure(out);
                        }

                        //end turn da fare in grafica
                        App.g.NextTurn();
                    }
                }

                in.close();
                out.close();
                socket.close();
            }
        }
        catch (IOException e) {System.err.println(e.getMessage());}
    }

    public String ChooseNickname(Message m)
    {
        String str;
        while(true) {
            m.OutMessage("Enter nickname: ");
            str = m.inputString();
            if(App.g.CheckNickname(str))
                break;
            m.NicknameNotAvailable(out);
        }
        App.g.MemorizeNickname(str);
        return str;
    }

    @Override
    public void update(Observable o, Object arg)
    {
        game = (GameState) arg;
        while (true) {
            try {
                oos.writeObject(game.GetBoard());
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
