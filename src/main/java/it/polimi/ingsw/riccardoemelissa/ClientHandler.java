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
        /*try {
            oos = new ObjectOutputStream(socketConnection.getOutputStream());
            if(GameState.GetPlayerNumber()==1)
                oos.writeObject(new GameProxy(GameState.GetBoard(),null,GameState.GetPlayers()));
            else {
                oos.writeObject(new GameProxy(GameState.GetBoard(), GameState.getActivePlayer(), GameState.GetPlayers()));
                System.out.println("invio board al giocatore");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

         */

        if(!socketConnection.isClosed()&&socketConnection.isConnected())
            while (true) {
                try {
                    //server in attesa di messaggi
                    ois=new ObjectInputStream(socketConnection.getInputStream());
                    Command cmd = (Command) ois.readObject();

                    System.out.println("A Client Handler arriva : " + cmd.GetType());

                    if(cmd.GetType()==CommandType.UPDATE)
                        settingClient(new GameProxy(GameState.GetBoard(),GameState.getActivePlayer(),GameState.GetPlayers()));


                    new ExecutorClientCommand().update(cmd);
                    //cmd_executor.update(cmd);
                    update(new Object());

                    if(GameState.GetPlayers()==null) {
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
    public void update(Object arg)
    {
        GameProxy toClient;

        toClient = new GameProxy(GameState.GetBoard(), GameState.getActivePlayer(), GameState.GetPlayers());

        while (true)
        {
            try {
                oos=new ObjectOutputStream(socketConnection.getOutputStream());
                oos.writeObject(toClient);
                System.out.println("Giocatori al client = " + toClient.getPlayers().size());
                oos.flush();
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void settingClient(GameProxy toClient)
    {
        while (true)
        {
            try {
                oos=new ObjectOutputStream(socketConnection.getOutputStream());
                oos.writeObject(toClient);
                System.out.println("Mando al client (ClientHandler -> settingClient) : giocatori = " + toClient.getPlayers().size());
                oos.flush();
                break;
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}
