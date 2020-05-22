package it.polimi.ingsw.riccardoemelissa;

import it.polimi.ingsw.riccardoemelissa.elements.CustomObservable;
import it.polimi.ingsw.riccardoemelissa.elements.CustomObserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler extends CustomObservable implements Runnable, CustomObserver {
    private String nickname;
    private Socket socketConnection;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    ExecutorClientCommand cmd_executor=new ExecutorClientCommand();

    public ClientHandler(Socket socket)
    {
       socketConnection=socket;
    }

    public ClientHandler(int port) {
        try
        {
            Socket socket=(new ServerSocket(port)).accept();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This method receives command from client
     */
    public void run() {
        update(new Object());
        if(!socketConnection.isClosed()&&socketConnection.isConnected())
            while (true) {
                try {
                    //server in attesa di messaggi

                    ois=new ObjectInputStream(socketConnection.getInputStream());
                    Command cmd = (Command) ois.readObject();

                    if(cmd.getType()==CommandType.DISCONNECTED)
                    {
                        GameState.getBoard().removeObserver(this);
                        socketConnection.close();
                    }

                    new ExecutorClientCommand().update(cmd);

                    if(GameState.getPlayers()==null) {
                        ois.close();
                        oos.close();
                        socketConnection.close();
                    }
                }
                catch (IOException | ClassNotFoundException e) { break;}
            }
    }

    /**
     * Send the board update to client
     *
     * @param arg
     */
    @Override
    public void update(Object arg)
    {
        GameProxy toClient;

        GameState.getBoard().setActivePlayer(GameState.getActivePlayer());
        toClient = new GameProxy(GameState.getBoard(), GameState.getActiveWorker(), GameState.getPlayers());

        try {
            oos=new ObjectOutputStream(socketConnection.getOutputStream());
            oos.writeObject(toClient);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
