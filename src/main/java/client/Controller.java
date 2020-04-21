package client;

import elements.BoardGame;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Controller extends Thread
{
    ObjectInputStream in;
    private Scanner inString;
    private PrintWriter outString;
    Parent root;

    public Controller(Socket s, Parent root) throws IOException
    {
        in=new ObjectInputStream(s.getInputStream());
        inString=new Scanner( s.getInputStream());
        outString=new PrintWriter(s.getOutputStream());
        this.root=root;
    }

    public void run()
    {
        while (true)
        {
            Stage input=new Stage();
            Scene input_scene=new Scene(root);

        }
    }


}
