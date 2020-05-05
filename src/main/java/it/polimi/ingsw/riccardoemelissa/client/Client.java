package it.polimi.ingsw.riccardoemelissa.client;

import elements.GodCardType;
import elements.Worker;
import it.polimi.ingsw.riccardoemelissa.Command;
import it.polimi.ingsw.riccardoemelissa.CommandType;
import it.polimi.ingsw.riccardoemelissa.GameProxy;
import it.polimi.ingsw.riccardoemelissa.client.ControllerBoard;
import it.polimi.ingsw.riccardoemelissa.client.ListenerServer;
import it.polimi.ingsw.riccardoemelissa.exception.SendException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.TextField;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

public class Client extends Application implements Observer {

    private Stage primary;
    private FXMLLoader loader;
    private Parent root;
    private static ControllerBoard controller;
    private static ListenerServer listener;
    private GameProxy from_server;
    private Socket socket;
    @FXML
    public TextField nickname;

    @Override
    public void start(Stage primaryStage) throws Exception {

        primary= primaryStage;
        System.out.println("Loading board");
        //root=new Pane();
        //controller.start();
        loader=new FXMLLoader();
        System.out.println(getClass().getResource("start.fxml"));
        URL resource=getClass().getResource("start.fxml");
        loader.setController(this);
        loader.setLocation(resource);

        try {
            root = loader.load();
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

        primary.setScene(new Scene(root,600,400));
        primary.setTitle("Santorini");

        primary.show();
    }

    public static void main(String[] args) {
        socket = null;

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

    @FXML
    public void startGame(MouseEvent mouseEvent) throws IOException {
        /*int players_connected = from_server.getPlayers().length;

         */
        int players_connected = 1;


        if(players_connected == 1)
            changeScene("mode.fxml");


        else
            chooseNickname(mouseEvent);
    }

    public void changeScene(String path) throws IOException
    {
        root=null;
        loader=null;
        loader=new FXMLLoader();

        System.out.println(getClass().getResource(path));
        URL newresource=getClass().getResource(path);

        //Scene s = primary.getScene();
        //loader.setController(this);
        loader.setLocation(newresource);
        loader.setController(this);
        root = loader.load();
        System.out.println(root);

        Scene s=new Scene(root);
        //s.setRoot(root);
        primary.setScene(s);

        primary.show();
    }

    @FXML
    public void chooseNickname(MouseEvent mouseEvent) throws IOException {
        changeScene("choose_nickname.fxml");
        messageToServer(CommandType.NICKNAME, nickname.getText());
    }


    @FXML
    public void twoPlayers (MouseEvent event) throws IOException {
        messageToServer(CommandType.MODE, 2);
        //System.out.println("Premuto 2 gioc");

        System.out.println(from_server.getPlayers().length);

        //changeScene("loading.fxml");
    }

    @FXML
    public void threePlayers (MouseEvent event) throws IOException {
        messageToServer(CommandType.MODE, 3);
        //System.out.println("Premuto 3 gioc");
        //changeScene("loading.fxml");
    }

    @FXML
    public void modeOk (MouseEvent event) throws IOException
    {
        //System.out.println("Premuto ok gioc");
        changeScene("choose_nickname.fxml");
    }

    @FXML
    public void nicknameOk (MouseEvent mouseEvent) throws IOException
    {
        changeScene("loading.fxml");
    }

    @FXML
    public void selectedCell(MouseEvent mouseEvent)
    {
        /*
        Node source=(Node)mouseEvent.getSource();
        int rowIndex=(GridPane.getRowIndex(source))==null ? 0:(GridPane.getRowIndex(source));
        int colIndex=(GridPane.getColumnIndex(source))==null ? 0:(GridPane.getColumnIndex(source));
        int[] new_position= new int[]{rowIndex,colIndex};

        if(activeWorker==null){return;}

        if(getWorkers().size()<2)
            messageToServer(CommandType.NEWWORKER,new Worker(from_server.getActivePlayer(),new_position));

        if(from_server.getBoard().GetOccupant(new_position).GetProprietary().GetNickname().compareTo(from_server.getActivePlayer().GetNickname())==0)
        {
            activeWorker = from_server.getBoard().GetOccupant(new_position);
            if(from_server.getActivePlayer().GetGodCard().getType()== GodCardType.MOVE)
                activeMoveCells();
            else if(from_server.getActivePlayer().GetGodCard().getType()==GodCardType.BUILD)
                activeBuildCells();
        }
        else if(from_server.getActivePlayer().GetGodCard().getType()==GodCardType.MOVE&&possibleCells_activeWorker.contains(new_position))
        {
            //CommandType cmd_type = from_server.getActivePlayer().GetGodCard().Move(from_server.getBoard(), activeWorker, new_position);
            //messageToServer(cmd_type,activeWorker,new_position);

            messageToServer(CommandType.MOVE,activeWorker,new_position);
        }
        else if(from_server.getActivePlayer().GetGodCard().getType()==GodCardType.BUILD&&possibleCells_activeWorker.contains(new_position))
        {
            //CommandType cmd_type = from_server.getActivePlayer().GetGodCard().Build(from_server.getBoard(), activeWorker, new_position);
            //messageToServer(cmd_type,activeWorker,new_position);

            messageToServer(CommandType.BUILD,activeWorker,new_position);
        }
        */

    }

    @Override
    public void update(Observable o, Object arg)
    {

    }

    public void messageToServer(CommandType cmd_type,Object obj) {
        Command cmd_toserver=new Command(cmd_type,obj,null);
        while (true) {
            try {
                ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(cmd_toserver);
                break;
            }
            catch (IOException io){}
        }
    }

    public void messageToServer(CommandType cmd_type,Object obj,int[] new_pos) {
        Command cmd_toserver=new Command(cmd_type,obj,new_pos);
        while (true) {
            try {
                ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(cmd_toserver);
                break;
            }
            catch (IOException io){}
        }
    }

    public void messageToServer(CommandType cmd_type) {
        Command cmd_toserver=new Command(cmd_type,from_server.getActivePlayer(),null);
        while (true) {
            try {
                ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(cmd_toserver);
                break;
            }
            catch (IOException io)
            {
                new SendException("SendMessage error!(code:"+cmd_type+")");
            }
        }
    }
}


