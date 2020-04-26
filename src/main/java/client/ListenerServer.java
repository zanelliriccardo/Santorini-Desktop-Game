package client;

import it.polimi.ingsw.riccardoemelissa.GameProxy;
import javafx.scene.Parent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ListenerServer extends Thread {
    private ObjectInputStream in;
    private ControllerBoard controller;
    public ListenerServer(Socket s, Parent root) throws IOException
    {
        in=new ObjectInputStream(s.getInputStream());
        controller=new ControllerBoard(s,root);
    }

    public void run()
    {

        while (true)
        {
            GameProxy fromServer=null;
            fromServer.addObserver(controller);
            try {
                fromServer=(GameProxy) in.readObject();
                if(fromServer!=null)
                {
                    fromServer.notifyAll();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

}
