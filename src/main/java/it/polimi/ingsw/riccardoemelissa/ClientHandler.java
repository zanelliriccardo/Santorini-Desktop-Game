package it.polimi.ingsw.riccardoemelissa;

import elements.BoardGame;
import elements.Player;
import elements.Worker;

import javax.naming.NoInitialContextException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class ClientHandler extends Observable implements Runnable,Observer {
    private String nickname;
    private Message m;
    private Socket socketConnection;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private GameState game;

    public ClientHandler(Socket socket) throws IOException {

        m=new Message(socket);
        oos=new ObjectOutputStream(socket.getOutputStream());
    }

    /**
     * This method receives command from client
     */
    public void run() {
        if(!socketConnection.isClosed()&&socketConnection.isConnected())
            while (true) {
                try {
                    //server in attesa di messaggi
                    Command cmd = (Command) ois.readObject();
                    if(cmd!=null)
                        notifyObservers(cmd);
                } catch (IOException | ClassNotFoundException e) {
                    notifyObservers(new Command(CommandType.DISCONNECTED,null,null));
                }
            }
        //errore per chiusura connesione
    }

    /**
     * It manages the board update
     *
     *
     *
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg)
    {
        final MessageToClient toClient=new MessageToClient(game.GetBoard(),game.GetActivePlayer());

        while (true) {
            try {
                oos.writeObject(toClient);//sostituire con toclient
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
