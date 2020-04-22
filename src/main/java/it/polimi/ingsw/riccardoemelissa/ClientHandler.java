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

public class ClientHandler implements Runnable,Observer {
    private String nickname;
    private Message m;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private GameState game;

    public ClientHandler(Socket socket) throws IOException {

        m=new Message(socket);
        oos=new ObjectOutputStream(socket.getOutputStream());
    }

    public void run() {
        try {
            //server in attesa di messaggi
            Command c=(Command)ois.readObject();
            
        }
        catch (IOException | ClassNotFoundException e) {System.err.println(e.getMessage());}
    }

    @Override
    public void update(Observable o, Object arg)
    {
        final BoardGame client_board = (BoardGame) arg;

        while (true) {
            try {
                oos.writeObject(client_board);
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
