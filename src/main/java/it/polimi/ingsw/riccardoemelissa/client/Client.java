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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class Client extends Application implements CustomObserver {

    GameProxy from_server=new GameProxy(null,null,null);
    Stage primary;
    FXMLLoader loader;
    Parent root;
    static ControllerBoard controller;
    static ListenerServer listener;
    static Socket socket;
    ArrayList<int[]> possibleCells_activeWorker =new ArrayList<>();
    Worker activeWorker=null;

    @FXML
    public TextField nickname;
    @FXML
    public ImageView set_power;
    @FXML
    public TextField set_turn;
    @FXML
    public GridPane myboard;
    @FXML
    public ToggleButton button_setpower;
    @FXML
    public TextArea setServerMessage;
    @FXML
    public ImageView set_godcard;
    @FXML
    public ImageView godOpponent1;
    @FXML
    public ImageView godOpponent2;
    @FXML
    public TextField opponent1;
    @FXML
    public TextField opponent2;



    @FXML
    public int[] mouseEntered(MouseEvent e) {
        Node source = e.getPickResult().getIntersectedNode();
        Integer colIndex,rowIndex;
        try {
            Pane pane=(Pane) source;

            colIndex = GridPane.getColumnIndex(source);
            rowIndex = GridPane.getRowIndex(source);
            System.out.printf("Mouse entered cell [%d, %d]%n", colIndex, rowIndex);
        } catch (Exception exception) {
            colIndex = GridPane.getColumnIndex(source.getParent());
            rowIndex = GridPane.getRowIndex(source.getParent());
            System.out.printf("Mouse entered cell [%d, %d]%n", colIndex, rowIndex);
        }



        return new int[]{rowIndex, colIndex};
    }

    @FXML
    public void setWorkerPosition (int[] pos, String worker_color)
    {
        Pane box = (Pane) myboard.getChildren();

        Circle worker = new Circle(box.getHeight()/2, box.getWidth()/2, box.getHeight()/3);

        switch (worker_color)
        {
            case "magenta":
                worker.setFill(Color.MAGENTA);
                break;

            case "aquamarine":
                worker.setFill(Color.AQUAMARINE);
                break;

            case "gold":
                worker.setFill(Color.GOLD);
                break;
        }

        myboard.add(worker, pos[1],pos[0]);
    }

    @FXML
    public void initializeBoardgame (GridPane myboard) {
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                Pane pane = new Pane();
                myboard.getChildren().add(pane);
                GridPane.setColumnIndex(pane, x);
                GridPane.setRowIndex(pane, y);
            }
        }
    }

    public void loadingBoard(GridPane myboard)
    {
        myboard=new GridPane();
        initializeBoardgame(myboard);
        try {
            changeScene("board2.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

    primary= primaryStage;
    System.out.println("Loading board");

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

    primary.setScene(new Scene(root,600,400));
    primary.setTitle("Santorini");

    primary.show();
    //controller=new ControllerBoard(socket,this);
    //controller.start();
    listener = new ListenerServer(socket,this);
    listener.start();

    //initializeBoardgame();
    }

    public static void main(String[] args) {
        socket = null;

        try {
            socket = new Socket(InetAddress.getLocalHost(), 33500);
        } catch (IOException e) {
            System.out.println("socket");
        }
        System.out.println("Connected to server");
        launch(args);
    }

    @FXML
    public void startGame(MouseEvent mouseEvent) throws IOException {
        while(true)
        {
            if (from_server.getPlayers() == null)
                continue;
            break;
        }

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

    public void callUpdate_fromServer() throws IOException
    {
        try
        {
            System.out.println("Chiedo update al server");
            messageToServer(CommandType.UPDATE);
            //update();
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
        /*while (from_server.getPlayers().size()==0)
            callUpdate_fromServer();


         */
        changeScene("choose_nickname.fxml");
        System.out.println("Passaggio a inserimento nickname");

    }

    @FXML
    public void twoPlayers (MouseEvent event) throws IOException {
        messageToServer(CommandType.MODE, 2);
        System.out.println("Premuto 2 gioc");
        chooseNickname(event);
    }

    @FXML
    public void threePlayers (MouseEvent event) throws IOException {
        messageToServer(CommandType.MODE, 3);
        System.out.println("Premuto 3 gioc");

        System.out.println("Giocatori collegati = " + from_server.getPlayers().size());

        chooseNickname(event);
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
        messageToServer(CommandType.NICKNAME,nickname.getText());
        changeScene("loading.fxml");

        System.out.println("Premuto ok inserimento nickname");
    }

    private static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {}

    }

    @FXML
    public void showBoard (String active_player_name) throws IOException
    {
        changeScene("board2.fxml");
        set_turn.setText("Turn of " + active_player_name);
    }

    @FXML
    public void changeButtonImage (MouseEvent mouseEvent) throws IOException
    {
        if (!button_setpower.isSelected())
        //if(set_power.getImage().getUrl().compareTo(String.valueOf(getClass().getResource("images/heropower_active.png")))==0)
        {
            System.out.println("cambia potere in disattivo");
            set_power.setImage(new Image(String.valueOf((getClass().getResource("images/heropower_inactive.png")))));
            activedPower(PowerType.DISABLE);
        }

        if(button_setpower.isSelected())
        //else if(set_power.getImage().getUrl().compareTo(String.valueOf(getClass().getResource("images/heropower_inactive.png")))==0)
        {
            System.out.println("cambia potere in attivo");
            button_setpower.setText("ACTIVE");
            set_power.setImage(new Image(String.valueOf(getClass().getResource("images/heropower_active.png"))));
            activedPower(PowerType.ACTIVE);
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

        if(from_server.getActivePlayer().GetGodCard().getCardType()==GodCardType.ENDTURN)
        {
            //abilita click fine turno bottone di endturn() e disabilita il resto
        }
    }

    public void activedPower(PowerType powerType)
    {
        messageToServer(CommandType.SETPOWER,powerType);
        if(from_server.getActivePlayer().GetGodCard().getCardType()==GodCardType.MOVE) activeMoveCells();
        if(from_server.getActivePlayer().GetGodCard().getCardType()==GodCardType.BUILD) activeBuildCells();
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
        //update();
    }

    public void messageToServer(CommandType cmd_type,Object obj,int[] new_pos) {
        Command cmd_toserver=new Command(cmd_type,obj,new_pos);
        while (true) {
            try {
                System.out.println("message to server: "+new_pos[0]+","+new_pos[1]);
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
                    if(opponent.GetGodCard().Move(board, worker_toMove,pos)==CommandType.ERROR)//check move is possible for opponent card
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
    public void checkServerBoard ()
    {
        Worker worker;

        for(int i = 0; i< 5; i++)
        {
            for (int j=0; j< 5; j++)
            {
                if(!from_server.getBoard().GetStateBox(i,j)) {
                    System.out.println(from_server.getBoard().GetStateBox(i,j));
                    worker = from_server.getBoard().GetOccupant(i, j);
                    setWorkerPosition(worker.GetPosition(), "magenta");
                }
            }
        }
    }

    @FXML
    public void selectedCell(MouseEvent mouseEvent)
    {
        int[] new_position= mouseEntered(mouseEvent);

        System.out.println("La posizione cliccata è ( " + new_position[0] + " , "+ new_position[1] + ")" );

        if(nickname.getText().compareTo(from_server.getActivePlayer().GetNickname())==0&&getWorkers().size()<2)
        {
            if(!from_server.getBoard().GetStateBox(new_position))
                return;
            messageToServer(CommandType.NEWWORKER, new Worker(from_server.getActivePlayer(),null), new_position);
        }
        else if(!from_server.getBoard().GetStateBox(new_position))
        {
            if(from_server.getBoard().GetOccupant(new_position).GetProprietary().GetNickname().compareTo(from_server.getActivePlayer().GetNickname())==0) {
                activeWorker = from_server.getBoard().GetOccupant(new_position);
                if (from_server.getActivePlayer().GetGodCard().getCardType() == GodCardType.MOVE)
                    activeMoveCells();
                else if (from_server.getActivePlayer().GetGodCard().getCardType() == GodCardType.BUILD)
                    activeBuildCells();
            }
        }

        if(activeWorker==null)
        return;

        else if(from_server.getActivePlayer().GetGodCard().getCardType()==GodCardType.MOVE&&contains(new_position))
        {
            //CommandType cmd_type = from_server.getActivePlayer().GetGodCard().Move(from_server.getBoard(), activeWorker, new_position);
            //messageToServer(cmd_type,activeWorker,new_position);

            messageToServer(CommandType.MOVE,activeWorker,new_position);
        }
        else if(from_server.getActivePlayer().GetGodCard().getCardType()==GodCardType.BUILD&&contains(new_position))
        {
            //CommandType cmd_type = from_server.getActivePlayer().GetGodCard().Build(from_server.getBoard(), activeWorker, new_position);
            //messageToServer(cmd_type,activeWorker,new_position);

            messageToServer(CommandType.BUILD,activeWorker,new_position);
        }
    }

    private boolean contains(int[] new_position) {
            for ( int[] i : possibleCells_activeWorker)
            {
                if(Arrays.equals(i, new_position))
                    return true;
            }
            return false;
    }

    private void activeMoveCells()
    {
        for (int[] pos :
                possibleCells_activeWorker)
        {
            for (Node child : myboard.getChildren()) {
                Pane pane = (Pane) child;
                Integer r = myboard.getRowIndex(child);
                Integer c = myboard.getColumnIndex(child);
                if(r!=null && r.intValue() == pos[0] && c != null && c.intValue() == pos[1])
                {
                    pane.setStyle(null);
                }
            }
        }

        possibleCells_activeWorker= checkMoves(from_server.getBoard(),activeWorker);

        for (int[] pos :
                possibleCells_activeWorker)
        {
            for (Node child : myboard.getChildren()) {
                Pane pane = (Pane) child;
                Integer r = myboard.getRowIndex(child);
                Integer c = myboard.getColumnIndex(child);
                if(r!=null && r.intValue() == pos[0] && c != null && c.intValue() == pos[1])
                {
                    pane.setStyle("-fx-background-color: #1E90FF");
                }
            }


            //colora di blu
        }
    }

    private void activeBuildCells()
    {
        possibleCells_activeWorker= checkBuilds(from_server.getBoard(),activeWorker);
        for (int[] pos :
                possibleCells_activeWorker) {
            //colora di blu
        }
    }

    public ArrayList<Worker> getWorkers()
    {
        ArrayList<Worker> workers=new ArrayList<Worker>();
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if(!from_server.getBoard().GetStateBox(i,j))
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
            System.out.println("in attesa di messaggio dal server");
            from_server=(GameProxy)in.readObject();
            System.out.println("ricevuto update dal server (Client -> update): " + from_server.getPlayers().size());
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

    }
}


