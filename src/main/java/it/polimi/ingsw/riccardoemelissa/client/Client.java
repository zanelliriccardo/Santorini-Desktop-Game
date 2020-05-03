package it.polimi.ingsw.riccardoemelissa.client;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.TextField;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

public class Client extends Application implements Observer {

    private static Parent root;
    private static ControllerBoard controller;
    private static ListenerServer listener;
    private static Socket socket;

    @FXML
    public TextField nickname;

    @Override
    public void start(Stage primaryStage) throws Exception {

        System.out.println("Loading board");
        //root=new Pane();
        //controller.start();
        FXMLLoader loader=new FXMLLoader();
        //System.out.println(Client.class.getClassLoader().getResource("it/polimi/ingsw/riccardoemelissa/client/start.fxml"));
        //InputStream input =Client.class.getClassLoader().getResourceAsStream("it/polimi/ingsw/riccardoemelissa/client/start.fxml");
        //InputStream input = resource.openStream();

        /*String path = Client.class.getPackage().getName().replace(".", "/");
        System.out.println(path);
        InputStream input = Client.class.getClassLoader().getResourceAsStream(path + "/start.fxml");

         */

        InputStream inputStream = getClass().getResource("start.fxml").openStream();

        loader.setController(new ControllerBoard(socket));

        try {
//            root = loader.load(input);
            root = loader.load(inputStream);
            //root = FXMLLoader.load(resource);
        }
        catch (LoadException e)
        {
            e.printStackTrace();
        }
        //root = FXMLLoader.load(getClass().getResource("/start.fxml"));//errore qua

        /*root = new Pane();
        Scene scene=new Scene(root);
        Image background_image=new Image("@../images/start_image.jpg");
        ImageView background=new ImageView((Element) background_image);
        HBox box=new HBox();
        box.getChildren().a;
        background.setSize(600,400);
        root.getChildrenUnmodifiable().add((Node)background);*/

        primaryStage.setScene(new Scene(root,600,400));
        primaryStage.setTitle("Santorini");

        primaryStage.show();
    }

    public static void main(String[] args) {

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


