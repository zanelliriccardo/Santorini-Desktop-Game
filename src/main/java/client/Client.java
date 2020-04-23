package client;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.TextField;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class Client extends Application implements Observer {

    private static Parent root;
    @FXML
    public TextField nickname;

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = FXMLLoader.load(getClass().getResource("filefxml/menu.fxml"));
        primaryStage.setTitle("Santorini");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        //gui iniziale

    }


    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 1337);
        Scanner socketIn = new Scanner(socket.getInputStream());
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());

        ListenerServer listening_boardupdate = new ListenerServer(socket);
        listening_boardupdate.start();

        ControllerBoard listen_server=new ControllerBoard(socket,root);
        listen_server.start();


        launch(args);


    }



    @FXML
    public void startGame(MouseEvent mouseEvent) throws IOException {

        changeScene("mode.fxml");//portarlo su scelta 1 o 2 giocatori
    }

    private void changeScene(String s) throws IOException {
        root= FXMLLoader.load(getClass().getResource(s));
    }

    @FXML
    public void selectedCell(MouseEvent mouseEvent)
    {
        Node source=(Node)mouseEvent.getSource();
        int rowIndex=(GridPane.getRowIndex(source))==null ? 0:(GridPane.getRowIndex(source));
        int colIndex=(GridPane.getColumnIndex(source))==null ? 0:(GridPane.getColumnIndex(source)) ;

    }

    @Override
    public void update(Observable o, Object arg)
    {

    }
}
