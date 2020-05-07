package it.polimi.ingsw.riccardoemelissa.client;

import it.polimi.ingsw.riccardoemelissa.elements.*;
import it.polimi.ingsw.riccardoemelissa.Command;
import it.polimi.ingsw.riccardoemelissa.CommandType;
import it.polimi.ingsw.riccardoemelissa.GameProxy;
import it.polimi.ingsw.riccardoemelissa.exception.SendException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

public class Client extends Application implements CustomObserver {

    private static GameProxy from_server=new GameProxy(null,null,null);
    private Stage primary;
    private FXMLLoader loader;
    private Parent root;
    private static ControllerBoard controller;
    private static ListenerServer listener;
    private static Socket socket;
    private ArrayList<int[]> possibleCells_activeWorker =new ArrayList<>();
    private Worker activeWorker;
    @FXML
    public TextField nickname;
    @FXML
    public ImageView set_power;
    @FXML
    public TextField set_turn;

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
        //listener = new ListenerServer(socket,from_server);

        //listener.start();

        launch(args);
    }

    @FXML
    public void startGame(MouseEvent mouseEvent) throws IOException {
        //String active_player_name = "nickname";

        /*try
        {
            System.out.println("Chiedo update al server");
            messageToServer(CommandType.UPDATE);
            update();
            System.out.println("ricevuto update dal server");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

         */

        updateServer();
        System.out.println("Premuto start -> n= " + from_server.getPlayers().size());

        if(from_server.getPlayers().size()<1) {
            changeScene("mode.fxml");
            System.out.println("Sono il primo giocatore");
            //showBoard(active_player_name);
        }
        else {
            System.out.println("Connessione a partita già esistente");
            changeScene("choose_nickname.fxml");
        }
    }

    public void updateServer() throws IOException
    {
        try
        {
            System.out.println("Chiedo update al server");
            messageToServer(CommandType.UPDATE);
            update();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void changeScene(String path) throws IOException
    {
        root=null;
        loader=null;
        loader=new FXMLLoader();

        //System.out.println(getClass().getResource(path));
        URL newresource=getClass().getResource(path);

        //Scene s = primary.getScene();
        //loader.setController(this);
        loader.setLocation(newresource);
        loader.setController(this);
        root = loader.load();

        Scene s=new Scene(root);
        //s.setRoot(root);
        primary.setScene(s);

        primary.show();
        System.out.println("Cambio scena");
    }

    @FXML
    public void chooseNickname(MouseEvent mouseEvent) throws IOException {
        updateServer();
        changeScene("choose_nickname.fxml");
        System.out.println("Passaggio a inserimento nickname");
        messageToServer(CommandType.NICKNAME, nickname.getText());
        updateServer();

        ArrayList<Player> players = from_server.getPlayers();
        for(int i = 0; i<players.size(); i++)
            System.out.println(players.get(i).GetNickname());
    }


    @FXML
    public void twoPlayers (MouseEvent event) throws IOException {
        //updateServer();
        messageToServer(CommandType.MODE, 2);
        System.out.println("Premuto 2 gioc");
        //changeScene("loading.fxml");
        updateServer();
        System.out.println("Giocatori collegati = " + from_server.getPlayers().size());
    }

    @FXML
    public void threePlayers (MouseEvent event) throws IOException {
        messageToServer(CommandType.MODE, 3);
        System.out.println("Premuto 3 gioc");
        //changeScene("loading.fxml");
    }

    @FXML
    public void modeOk (MouseEvent event) throws IOException
    {
        System.out.println("Premuto ok mode giocatori");
        chooseNickname(event);
    }

    @FXML
    public void nicknameOk (MouseEvent mouseEvent) throws IOException
    {
        System.out.println("Premuto ok inserimento nickname");
        changeScene("loading.fxml");
    }

    @FXML
    public void showBoard (String active_player_name) throws IOException
    {
        changeScene("board.fxml");
        set_turn.setText("Turn of " + active_player_name);
    }

    @FXML
    public void changeButtonImage (MouseEvent mouseEvent) throws IOException
    {
        if(set_power.getImage().getUrl().compareTo(String.valueOf(getClass().getResource("images/heropower_active.png")))==0)
        {
            System.out.println("cambia in disattivo");
            set_power.setImage(new Image(String.valueOf((getClass().getResource("images/heropower_inactive.png")))));
        }
        else if(set_power.getImage().getUrl().compareTo(String.valueOf(getClass().getResource("images/heropower_inactive.png")))==0)
        {
            System.out.println("cambia in attivo");
            set_power.setImage(new Image(String.valueOf(getClass().getResource("images/heropower_active.png"))));
        }

    }

    @Override
    public void update(Object obj)
    {
        from_server=(GameProxy) obj;

        ArrayList<Worker> workers=getWorkers();

        for (Worker w : workers)
        {
            possibleCells_activeWorker.addAll(checkMoves(from_server.getBoard(),w));
        }

        if(possibleCells_activeWorker.isEmpty())
        {
            messageToServer(CommandType.LOSE);
            //schermata sconfitta
            return;
        }
        possibleCells_activeWorker.clear();

        //fare guiii

        if(from_server.getActivePlayer().GetGodCard().GetType()==GodCardType.ENDTURN)
        {
            //abilita click fine turno bottone di endturn() e disabilita il resto
        }
    }

    public void activedPower(boolean bool)
    {
        messageToServer(CommandType.SETPOWER,bool);
        if(from_server.getActivePlayer().GetGodCard().GetType()==GodCardType.MOVE) activeMoveCells();
        if(from_server.getActivePlayer().GetGodCard().GetType()==GodCardType.BUILD) activeBuildCells();
    }

    public void setNickname(String nickname)
    {
        messageToServer(CommandType.NICKNAME,nickname);
    }

    public void messageToServer(CommandType cmd_type,Object obj) {
        Command cmd_toserver=new Command(cmd_type,obj,null);
        while (true) {
            try {
                ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(cmd_toserver);
                out.flush();
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

    public ArrayList<int[]> checkMoves(BoardGame board, Worker worker_toMove)
    {
        ArrayList<int[]> possiblemoves= worker_toMove.GetProprietary().GetGodCard().adjacentBoxNotOccupiedNotDome(board, worker_toMove.GetPosition());

        possiblemoves.removeIf(pos -> board.GetLevelBox(pos) - board.GetLevelBox(worker_toMove.GetPosition()) > 1);

        for (int[] pos: possiblemoves)
        {
            for (Player opponent : from_server.getPlayers())
            {
                if((opponent.GetNickname().compareTo(from_server.getActivePlayer().GetNickname())==0)&&opponent.GetGodCard().GetOpponentTurn())//check is an opponent && check opponent card act in active player turn
                    if(opponent.GetGodCard().Move(board, worker_toMove,pos)==CommandType.ERROR);//check move is possible for opponent card
                possiblemoves.remove(pos);
            }
        }
        return possiblemoves;
    }

    public ArrayList<int[]> checkBuilds(BoardGame board, Worker builder)
    {
        ArrayList<int[]> possiblebuild=board.AdjacentBox(builder.GetPosition());

        possiblebuild.removeIf(pos -> board.GetLevelBox(pos) == 4);

        for (int[] pos: possiblebuild)
        {
            for (Player opponent : from_server.getPlayers())
            {
                if((opponent.GetNickname().compareTo(from_server.getActivePlayer().GetNickname())==0)&&opponent.GetGodCard().GetOpponentTurn())//check is an opponent && check opponent card act in active player turn
                    if(opponent.GetGodCard().Build(board,builder,pos)==CommandType.ERROR);//check build is possible for opponent card
                possiblebuild.remove(pos);
            }
        }
        return possiblebuild;
    }

    public void endTurn()
    {
        from_server.getActivePlayer().GetGodCard().resetCard(GodCardType.MOVE);
        messageToServer(CommandType.CHANGE_TURN);
    }

    @FXML
    public void selectedCell(MouseEvent mouseEvent)
    {
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
            if(from_server.getActivePlayer().GetGodCard().getType()==GodCardType.MOVE)
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
    }

    private void activeMoveCells()
    {
        possibleCells_activeWorker= checkMoves(from_server.getBoard(),activeWorker);
        //metttere celle blu

    }

    private void activeBuildCells()
    {
        possibleCells_activeWorker= checkBuilds(from_server.getBoard(),activeWorker);
        //metttere celle blu
    }

    public ArrayList<Worker> getWorkers()
    {
        ArrayList<Worker> workers=new ArrayList<Worker>();
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if(from_server.getBoard().GetOccupant(i,j).GetProprietary().GetNickname().compareTo(from_server.getActivePlayer().GetNickname())==0)
                    workers.add(from_server.getBoard().GetOccupant(i,j));
            }
        }
        return workers;
    }

    public void update()
    {
        try {
            ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
            from_server=(GameProxy)in.readObject();
            System.out.println("ricevuto update dal server (Client -> update): " + from_server.getPlayers().size());
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

    }
}


