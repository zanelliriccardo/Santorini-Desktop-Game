package it.polimi.ingsw.riccardoemelissa;



import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;


public class MultiEchoServer {
    private int port;

    private ExecutorClientCommand cmd_executor =new ExecutorClientCommand();

    public MultiEchoServer(int port)
    {
        this.port = port;
    }

    /**
     * Accept connection from a client and start thread to manage it, so
     * if gameover close all connection and wait for another player who wants start a game
     * @throws IOException
     */
    public void startServer() throws IOException {
        System.out.println("Server ready");

        while (true) {
            ServerSocket serverSocket=new ServerSocket(port);

            try {
                Socket socket = serverSocket.accept();
                ClientHandler firstClient=new ClientHandler(socket);
                GameState.getBoard().addObserver(firstClient);
                Thread firstThread=new Thread(firstClient);
                firstThread.start();

                serverSocket.close();

                while (GameState.getNumPlayers()==10) {
                     System.out.println(GameState.getNumPlayers());
                }

                serverSocket=new ServerSocket(port,GameState.getNumPlayers());

                ArrayList<Socket> sockets=new ArrayList<Socket>();
                ArrayList<ClientHandler> clients=new ArrayList<ClientHandler>();
                ArrayList<Thread> threads=new ArrayList<Thread>();

                for(int i = 0; i<GameState.getNumPlayers()-1; i++)
                    try {
                        sockets.add(serverSocket.accept());
                        clients.add(new ClientHandler(sockets.get(i)));
                        GameState.getBoard().addObserver(clients.get(i));
                        threads.add(new Thread(clients.get(i)));
                        threads.get(i).start();
                    }
                    catch(IOException e) {
                        break;
                    }

                while (!GameState.gameReady()) {}
                Thread t = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        while (true) {
                            try {
                                GameState.getBoard().custom_notifyAll();
                                Thread.sleep(3000);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });
                t.start();
                serverSocket.close();

                Collections.shuffle(GameState.getPlayers());
                GameState.getBoard().custom_notifyAll();

                while (!GameState.getGameOver()) {}

                GameState.reset();
                threads.clear();
                socket.close();
                for (Socket s: sockets) {
                    s.close();
                }

            } catch (IOException|NullPointerException e) {
                break;
            }
            serverSocket.close();
        }


    }
}
