package client;

import it.polimi.ingsw.riccardoemelissa.MessageToClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ListenerServer extends Thread {
    ObjectInputStream in;

    public ListenerServer(Socket s) throws IOException
    {
        in=new ObjectInputStream(s.getInputStream());
    }

    public void run()
    {

        while (true)
        {
            MessageToClient fromServer=null;
            fromServer.addObserver();
            try {
                fromServer=(MessageToClient) in.readObject();

                if(fromServer!=null)
                {
                    //fare GUIIIIIIIIIIIIIIII
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

}
