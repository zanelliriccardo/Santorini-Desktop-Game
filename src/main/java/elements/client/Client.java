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
    private static ControllerBoard controller;
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

        ListenerServer listening_boardupdate = new ListenerServer(socket,root);
        listening_boardupdate.start();

        ControllerBoard controller=new ControllerBoard(socket,root);
        launch(args);
    }


    @Override
    public void update(Observable o, Object arg)
    {

    }
}


