package client;

import elements.BoardGame;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ListenerBoard extends Thread  {
    ObjectInputStream in;

    public ListenerBoard(Socket s) throws IOException
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

    @FXML
    public void selectedCell(MouseEvent mouseEvent)
    {
        Node source=(Node)mouseEvent.getSource();
        int rowIndex=(GridPane.getRowIndex(source))==null ? 0:(GridPane.getRowIndex(source));
        int colIndex=(GridPane.getColumnIndex(source))==null ? 0:(GridPane.getColumnIndex(source)) ;

    }

}
