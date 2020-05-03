package client;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.TextField;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.*;

public class Client extends Application implements Observer {

    private static Parent root;
    private static ControllerBoard controller;
    private static ListenerServer listener;
    @FXML
    public TextField nickname;

    @Override
    public void start(Stage primaryStage) throws Exception {

        System.out.println("Loading board");
        //root=new Pane();
        //controller.start();
        FXMLLoader loader=new FXMLLoader();
        System.out.println(getClass().getClassLoader().getResource("client/start.fxml"));
        URL resource=getClass().getClassLoader().getResource("client/start.fxml");

        loader.setLocation(resource);
        root=loader.load();

        //root = FXMLLoader.load(getClass().getResource("/start.fxml"));//errore qua

        primaryStage.setTitle("Santorini");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {
        Socket socket = null;

        try {
            socket = new Socket(InetAddress.getLocalHost(), 35500);
        } catch (IOException e) {
            System.out.println("socket");
        }
        System.out.println("Connected to server");

        controller=new ControllerBoard(socket);

        listener = new ListenerServer(socket,controller);
        listener.start();

        launch(args);
    }


    @Override
    public void update(Observable o, Object arg)
    {

    }
}


