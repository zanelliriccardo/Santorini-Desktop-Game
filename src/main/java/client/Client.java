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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class Client extends Application implements Observer {

    private static Parent root;
    private static ControllerBoard controller;
    private static ListenerServer listener;
    @FXML
    public TextField nickname;

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = FXMLLoader.load(getClass().getResource("/filefxml/start.fxml"));
        primaryStage.setTitle("Santorini");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {
        Socket socket = null;
        ObjectInputStream ois=null;
        ObjectOutputStream oos=null;

        try {
            socket = new Socket("127.0.0.1", 1337);
        } catch (IOException e) {
            System.out.println("socket");
        }

        try {
             ois=new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("in");
        }

        try {
            oos=new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("out");
        }

        controller=new ControllerBoard(oos);

        System.out.println("ok");

        listener = new ListenerServer(ois,controller);

        System.out.println("ok");

        listener.start();

        System.out.println("ok");
        launch(args);
    }


    @Override
    public void update(Observable o, Object arg)
    {

    }
}


