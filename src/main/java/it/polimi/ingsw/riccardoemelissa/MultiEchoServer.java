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
    protected static int numplayer=0;
    public MultiEchoServer(int port)
    {
        this.port = port;
    }

    public void startServer2() { //non usare questo, l altro è meglio
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;
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
                executor.submit(new ClientHandler(socket));
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

                executor.submit(new ClientHandler(socket));

                for(int i=1;i<numplayer;i++)
                    try {
                        socket = serverSocket.accept();
                        executor.submit(new ClientHandler(socket));
                    }
                    catch(IOException e) {
                        break; // entrerei qui se serverSocket venisse chiuso
                    }

                while (App.g.GameReady()) { }

                App.g.NextTurn();
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
