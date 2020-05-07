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

    private ExecutorClientCommand cmd_executor =new ExecutorClientCommand();

    public MultiEchoServer(int port)
    {
        this.port = port;
    }

    public void startServer() throws IOException {
        ServerSocket serverSocket=new ServerSocket(port);
        ExecutorService executor = null;

        System.out.println("Server ready");

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                ClientHandler firstClient=new ClientHandler(socket);
                Thread firstThread=new Thread(firstClient);
                GameState.GetBoard().addObserver(firstClient);
                firstThread.start();

                System.out.println("First player connected");

                while (true) {
                    if (GameState.GetPlayers().size()>0)
                        break;
                }
                System.out.println("choose to play with: "+ GameState.GetPlayerNumber());

                for(int i=1;i<GameState.GetNumPlayers();i++)
                    try {
                        socket = serverSocket.accept();
                        ClientHandler otherClient=new ClientHandler(socket);
                        GameState.GetBoard().addObserver(otherClient);
                        Thread otherThread=new Thread(firstClient);
                        otherThread.start();
                        System.out.println("Another player connected");
                    }
                    catch(IOException e) {
                        break; // entrerei qui se serverSocket venisse chiuso
                    }

                while (!GameState.GameReady()) { }

                GameState.NextTurn();

                if(GameState.getGameOver()) //inserire controllo su fine partita
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
        }
    }
}
