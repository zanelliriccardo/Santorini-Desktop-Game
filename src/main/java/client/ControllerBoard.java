package client;

import elements.CustomObserver;
import it.polimi.ingsw.riccardoemelissa.Command;
import it.polimi.ingsw.riccardoemelissa.CommandType;
import it.polimi.ingsw.riccardoemelissa.GameProxy;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.swing.plaf.nimbus.AbstractRegionPainter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class ControllerBoard implements CustomObserver
{
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Parent root;
    private GameProxy from_server;
    private Stage primary_stage;
    private Scene input_scene;

    public ControllerBoard(Socket s, Parent root) throws IOException
    {
        primary_stage=new Stage();
        input_scene =new Scene(root);
        in=new ObjectInputStream(s.getInputStream());
        out=new ObjectOutputStream(s.getOutputStream());
        this.root=root;
    }

    @FXML
    public void startGame(MouseEvent mouseEvent) throws IOException {

        changeScene("mode.fxml");//portarlo su scelta 1 o 2 giocatori
    }

    private void changeScene(String s) throws IOException
    {
        root= FXMLLoader.load(getClass().getResource(s));
    }

    @FXML
    public void selectedCell(MouseEvent mouseEvent)
    {
        Node source=(Node)mouseEvent.getSource();
        int rowIndex=(GridPane.getRowIndex(source))==null ? 0:(GridPane.getRowIndex(source));
        int colIndex=(GridPane.getColumnIndex(source))==null ? 0:(GridPane.getColumnIndex(source)) ;
        int[] new_position= new int[]{rowIndex,colIndex};
    }

    @Override
    public void update(Object obj) {
        from_server=(GameProxy) obj;
        //fare guiii
        for(int i = 0;i < 5; i++)
            for (int j = 0; j < 5; j++)
            {

            }
    }

    public void messageToServer(CommandType cmd_type,int[] new_pos) {
        Command cmd_toserver=new Command(cmd_type,from_server.getActivePlayer(),new_pos);
        while (true) {
            try {
                out.writeObject(cmd_toserver);
                break;
            }
            catch (Throwable io){}
        }
    }

    public void messageToServer(CommandType cmd_type) {
        Command cmd_toserver=new Command(cmd_type,from_server.getActivePlayer(),null);
        while (true) {
            try {
                out.writeObject(cmd_toserver);
                break;
            }
            catch (IOException io){}
        }
    }
}
