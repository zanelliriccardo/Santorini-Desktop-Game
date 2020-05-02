package client;

import it.polimi.ingsw.riccardoemelissa.GameProxy;
import javafx.scene.Parent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ListenerServer extends Thread {
    private static ControllerBoard controller;
    private Socket socket;

    public ListenerServer(Socket s, ControllerBoard controller) {
        socket=s;
        this.controller=controller;
    }

    public void run()
    {

        while (true)
        {
            System.out.println("ok");

            try {
                ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
                GameProxy fromServer=(GameProxy) in.readObject();

                controller.update(fromServer);
            }
            catch (IOException | ClassNotFoundException e)
            {
                e.printStackTrace();
            }

        }
    }

}
