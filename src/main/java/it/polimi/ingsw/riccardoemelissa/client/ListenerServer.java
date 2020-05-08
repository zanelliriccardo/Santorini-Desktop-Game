package it.polimi.ingsw.riccardoemelissa.client;

import it.polimi.ingsw.riccardoemelissa.GameProxy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ListenerServer extends Thread {
    private GameProxy game;
    private Socket socket;

    public ListenerServer(Socket s, GameProxy client) {
        socket=s;
        this.game=client;
    }

    public void run()
    {

        while (true)
        {
            try {
                ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
                synchronized (game) {
                    game = (GameProxy) in.readObject();
                }
                System.out.println("ricevuto update dal server(classe listener)");
            }
            catch (IOException | ClassNotFoundException e)
            {
                e.printStackTrace();
            }

        }


    }

    public GameProxy getGameProxy() {
        return game;
    }
}
