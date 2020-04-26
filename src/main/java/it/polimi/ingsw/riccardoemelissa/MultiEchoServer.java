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

                ClientHandler firstClient=new ClientHandler(socket);
                game.GetBoard().addObserver(firstClient);
                executor.submit(firstClient);

                while (game.GetPlayerNumber()==1);

                for(int i=1;i<numplayer;i++)
                    try {
                        socket = serverSocket.accept();
                        ClientHandler otherClient=new ClientHandler(socket);
                        game.GetBoard().addObserver(otherClient);
                        executor.submit(otherClient);
                    }
                    catch(IOException e) {
                        break; // entrerei qui se serverSocket venisse chiuso
                    }

                while (!game.GameReady()) { }

                game.NextTurn();
                notifyAll();
                if(game.getGameOver()) //inserire controllo su fine partita
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
