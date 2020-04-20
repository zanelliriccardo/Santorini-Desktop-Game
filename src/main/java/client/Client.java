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

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("board.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        //gui iniziale

    }


    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 1337);
        System.out.println("Connection established");

        Scanner socketIn = new Scanner(socket.getInputStream());
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());

        Listener listening = new Listener(socket);
        listening.start();

        launch(args);

    }

    @FXML
    public void selectedCell(javafx.scene.input.MouseEvent mouseEvent)
    {
        Node source=(Node)mouseEvent.getSource();
        int colIndex=(GridPane.getColumnIndex(source))==null ? 0:(GridPane.getColumnIndex(source)) ;
        int rowIndex=(GridPane.getRowIndex(source))==null ? 0:(GridPane.getRowIndex(source));

    }

    public void startGame(MouseEvent mouseEvent)
    {
        
    }
}
