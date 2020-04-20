package client;

import elements.BoardGame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Listener extends Thread  {
    ObjectInputStream in;

    public Listener(Socket s) throws IOException
    {
        in=new ObjectInputStream(s.getInputStream());
    }

    public void run()
    {

        while (true)
        {
            BoardGame b=null;
            try {
                b=(BoardGame) in.readObject();

                if(b!=null)
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
