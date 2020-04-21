package client;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.awt.*;
import java.awt.TextField;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Application {

    private static Parent root;
    @FXML
    public TextField nickname;

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        primaryStage.setTitle("Santorini");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        //gui iniziale

    }


    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 1337);
        Scanner socketIn = new Scanner(socket.getInputStream());
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());

        ListenerBoard listening_boardupdate = new ListenerBoard(socket);
        listening_boardupdate.start();

        Controller listen_server=new Controller(socket,root);
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
}
