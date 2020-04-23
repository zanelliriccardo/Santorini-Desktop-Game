package it.polimi.ingsw.riccardoemelissa;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiEchoServer {
    private int port;
    private static int numplayer=0;
    private static GameState game;
    private ExecutorClientCommand cmd_executor =new ExecutorClientCommand();
    public MultiEchoServer(int port)
    {
        this.port = port;
    }

    public void startServer() {
        ServerSocket serverSocket;
        ExecutorService executor=null;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e.getMessage()); // porta non disponibile
            return;
        }

        System.out.println("Server ready");

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Scanner in = new Scanner(socket.getInputStream());
                PrintWriter out = new PrintWriter(socket.getOutputStream());

                out.println("Choose game mode:");
                out.flush();
                out.println("1) 2 players");
                out.flush();
                out.println("2) 3 players");
                out.flush();
                String mod = in.nextLine();
                in.close();
                out.close();

                if (mod == "1") {
                    numplayer=2;
                    executor = Executors.newFixedThreadPool(numplayer);
                }
                else if(mod == "2") {
                    numplayer=3;
                    executor = Executors.newFixedThreadPool(numplayer);
                }
                else
                    continue;

                ClientHandler c=new ClientHandler(socket);
                game.GetBoard().addObserver(c);
                c.addObserver(cmd_executor);
                executor.submit(c);

                while (game.GetPlayerNumber()==0);

                for(int i=1;i<numplayer;i++)
                    try {
                        socket = serverSocket.accept();
                        ClientHandler c2=new ClientHandler(socket);
                        game.GetBoard().addObserver(c2);
                        c2.addObserver(cmd_executor);
                        executor.submit(c2);
                    }
                    catch(IOException e) {
                        break; // entrerei qui se serverSocket venisse chiuso
                    }



                while (!game.GameReady()) { }

                game.NextTurn();
                notifyAll();
                if(true) //inserire controllo su fine partita
                    break;
            } catch (IOException e) {
                break; // entrerei qui se serverSocket venisse chiuso
            }
        }

        executor.shutdown();

        try {
            serverSocket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }
    }
}
