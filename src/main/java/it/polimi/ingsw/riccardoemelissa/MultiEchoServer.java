package it.polimi.ingsw.riccardoemelissa;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executor;
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

    public void startServer() throws IOException {
        ServerSocket serverSocket=new ServerSocket(port);
        ExecutorService executor = null;

        game=new GameState();
        System.out.println("Server ready");

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                ClientHandler firstClient=new ClientHandler(socket);
                Thread firstThread=new Thread(firstClient);
                game.GetBoard().addObserver(firstClient);
                firstThread.start();

                System.out.println("First player connected");

                while (game.GetPlayerNumber()==1);

                for(int i=1;i<numplayer;i++)
                    try {
                        socket = serverSocket.accept();
                        ClientHandler otherClient=new ClientHandler(socket);
                        game.GetBoard().addObserver(otherClient);
                        Thread otherThread=new Thread(firstClient);
                        otherThread.start();
                        System.out.println("Another player connected");
                    }
                    catch(IOException e) {
                        break; // entrerei qui se serverSocket venisse chiuso
                    }

                while (!game.GameReady()) { }

                game.NextTurn();
                notifyAll();
                if(game.getGameOver()) //inserire controllo su fine partita
                    break;
            } catch (IOException|NullPointerException e) {
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
