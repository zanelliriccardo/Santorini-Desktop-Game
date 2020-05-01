package client;

import it.polimi.ingsw.riccardoemelissa.GameProxy;
import javafx.scene.Parent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ListenerServer extends Thread {
    private ObjectInputStream in;
    private ControllerBoard controller;

    public ListenerServer(ObjectInputStream ois,ControllerBoard controller)
    {
        in=ois;
        this.controller=controller;
    }

    public void run()
    {

        while (true)
        {
            System.out.println("ok");
            GameProxy fromServer=null;
            fromServer.addObserver(controller);
            try {
                fromServer=(GameProxy) in.readObject();
                if(fromServer!=null)
                {
                    fromServer.notifyAll();
                }
            }
            catch (IOException | ClassNotFoundException e)
            {
                e.printStackTrace();
            }

        }
    }

}
