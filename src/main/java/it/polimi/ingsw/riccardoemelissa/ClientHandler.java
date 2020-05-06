package it.polimi.ingsw.riccardoemelissa;

import elements.CustomObservable;
import elements.CustomObserver;
import elements.Player;
import elements.Worker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class ClientHandler extends CustomObservable implements Runnable, CustomObserver {
    private String nickname;
    private Socket socketConnection;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private static GameState game=new GameState();
    ExecutorClientCommand cmd_executor=new ExecutorClientCommand();

    public ClientHandler(Socket socket)
    {
       socketConnection=socket;
    }

    /*public ClientHandler(Socket socket) throws IOException
    {
        ois=new ObjectInputStream(socket.getInputStream());
        oos=new ObjectOutputStream(socket.getOutputStream());
    }*/

    public ClientHandler(int port) {
        try
        {
            Socket socket=(new ServerSocket(port)).accept();
            ois=new ObjectInputStream(socket.getInputStream());
            oos=new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This method receives command from it.polimi.ingsw.riccardoemelissa.client
     */
    public void run() {
        if(!socketConnection.isClosed()&&socketConnection.isConnected())
            while (true) {
                try {
                    //server in attesa di messaggi
                    ois=new ObjectInputStream(socketConnection.getInputStream());
                    Command cmd = (Command) ois.readObject();

                    System.out.println(cmd.GetType());

                    new ExecutorClientCommand().update(cmd);
                    //cmd_executor.update(cmd);

                    if(game.GetPlayers()==null) {
                        ois.close();
                        oos.close();
                        socketConnection.close();
                    }
                }
                catch (IOException | ClassNotFoundException e) {
                    cmd_executor.update(new Command(CommandType.DISCONNECTED,nickname,null));
                }
            }
        //errore per chiusura connessione
        cmd_executor.update(new Command(CommandType.DISCONNECTED,nickname,null));

    }

    /**
     * It manages the board update
     *
     *
     * @param arg
     */
    @Override
    public void update(Object arg)//usare game come riferimneto cosi tolgo game da variabile in questa classe, da cambiare quindi nache il controllo dsopra che utiliazza game
    {
        final GameProxy toClient=new GameProxy(game.GetBoard(),game.getActivePlayer(),game.GetPlayers());

        while (true)
        {
            try {
                oos=new ObjectOutputStream(socketConnection.getOutputStream());
                oos.writeObject(toClient);
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
