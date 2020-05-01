package it.polimi.ingsw.riccardoemelissa;

import elements.CustomObservable;
import elements.CustomObserver;
import elements.Player;
import elements.Worker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class ClientHandler extends CustomObservable implements Runnable, CustomObserver {
    private String nickname;
    private Socket socketConnection;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private GameState game=new GameState();
    ExecutorClientCommand cmd_executor=new ExecutorClientCommand();

    public ClientHandler(Socket socket) throws IOException
    {
        ois=new ObjectInputStream(socket.getInputStream());
        oos=new ObjectOutputStream(socket.getOutputStream());
    }

    /**
     * This method receives command from client
     */
    public void run() {
        Command cmd = null;
        cmd.addObserver(cmd_executor);
        if(!socketConnection.isClosed()&&socketConnection.isConnected())
            while (true) {
                try {
                    //server in attesa di messaggi
                    cmd = (Command) ois.readObject();

                    cmd.custom_notifyAll();

                    if(game.GetPlayers()==null) {
                        ois.close();
                        oos.close();
                        socketConnection.close();
                    }
                }
                catch (IOException | ClassNotFoundException e) {
                    new Command(CommandType.DISCONNECTED,nickname,null).custom_notifyAll();
                }
            }
        //errore per chiusura connessione
        new Command(CommandType.DISCONNECTED,nickname,null).custom_notifyAll();
    }

    /**
     * It manages the board update
     *
     *
     * @param arg
     */
    @Override
    public void update(Object arg)
    {
        final GameProxy toClient=new GameProxy(game.GetBoard(),game.getActivePlayer(),game.GetPlayers());

        while (true)
        {
            try {
                oos.writeObject(toClient);
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
