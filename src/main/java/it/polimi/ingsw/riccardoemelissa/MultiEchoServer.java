package it.polimi.ingsw.riccardoemelissa;



import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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

        System.out.println("Server ready");

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                ClientHandler firstClient=new ClientHandler(socket);
                GameState.GetBoard().addObserver(firstClient);
                Thread firstThread=new Thread(firstClient);
                firstThread.start();
                System.out.println("First player connected");

                serverSocket.close();

                while (GameState.GetNumPlayers()==10) {
                     System.out.println(GameState.GetNumPlayers());
                }
                System.out.println("choose to play with: "+ GameState.GetNumPlayers());

                serverSocket=new ServerSocket(port,GameState.GetNumPlayers());

                ArrayList<Socket> sockets=new ArrayList<Socket>();
                ArrayList<ClientHandler> clients=new ArrayList<ClientHandler>();
                ArrayList<Thread> threads=new ArrayList<Thread>();

                for(int i=0;i<GameState.GetNumPlayers()-1;i++)
                    try {
                        sockets.add(serverSocket.accept());
                        clients.add(new ClientHandler(sockets.get(i)));
                        GameState.GetBoard().addObserver(clients.get(i));
                        threads.add(new Thread(clients.get(i)));
                        threads.get(i).start();
                        System.out.println("Another player connected(num:"+(i+1));
                    }
                    catch(IOException e) {
                        break; // entrerei qui se serverSocket venisse chiuso
                    }

                while (!GameState.GameReady())
                {

                }
                serverSocket.close();
                System.out.println("gioco pronto");
                GameState.GetBoard().custom_notifyAll();

                if(GameState.getGameOver()) //inserire controllo su fine partita
                    break;
            } catch (IOException|NullPointerException e) {
                break; // entrerei qui se serverSocket venisse chiuso
            }
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
