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
import javafx.scene.control.Button;
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

public class Client extends Application {

    GameProxy from_server=new GameProxy(null,null,null);
    Stage primary;
    FXMLLoader loader;
    Parent root;
    static ListenerServer listener;
    static Socket socket;
    ArrayList<int[]> possibleCells_activeWorker =new ArrayList<>();
    Worker activeWorker;
    Boolean modifiable_selectedWorker=true;

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
    public TextField playerBoard;
    @FXML
    public Circle colorPlayerBoard;
    @FXML
    public Circle colorOpponent1;
    @FXML
    public Circle colorOpponent2;
    @FXML
    public Button endTurn;


    /**
     * get the cell clicket by user
     *
     * @param e
     * @return
     */
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

    /**
     * create a worker
     *
     * @param pos
     * @param worker_color
     */
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

    /**
     * create the cell on gridpane board
     *
     * @param myboard
     */
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

    /**
     * set and show gui
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

    primary= primaryStage;
    System.out.println("Loading board");
    activeWorker=null;

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

    primary.setScene(new Scene(root,640,480));
    primary.setTitle("Santorini");

    primary.show();
    listener = new ListenerServer(socket,this);
    listener.start();

    }

    public static void main(String[] args) {
        socket = null;

        try {
            socket = new Socket(InetAddress.getLocalHost(), 33500);
        } catch (IOException e) {
            //mettere una scena dove diciamo che cè un errore e chiudere il gioco
        }
        System.out.println("Connected to server");
        launch(args);
    }

    /**
     * start the game, so if another player have created the game the user join it
     *
     * @param mouseEvent
     * @throws IOException
     */
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

    /**
     * change the user view
     *
     * @param path: fxml path
     * @throws IOException
     */
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

    /*
    @FXML
    public void chooseNickname(MouseEvent mouseEvent) throws IOException {
        changeScene("choose_nickname.fxml");
        System.out.println("Passaggio a inserimento nickname");
    }

     */

    /**
     * set number of players and change to nickname view
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void twoPlayers (MouseEvent event) throws IOException {
        messageToServer(CommandType.MODE, 2);
        System.out.println("Premuto 2 gioc");
        changeScene("choose_nickname.fxml");
    }

    /**
     * set number of players and change to nickname view
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void threePlayers (MouseEvent event) throws IOException {
        messageToServer(CommandType.MODE, 3);
        System.out.println("Premuto 3 gioc");
        changeScene("choose_nickname.fxml");
    }

    /*

    @FXML
    public void modeOk (MouseEvent event) throws IOException
    {
        System.out.println("Premuto ok mode giocatori");
        chooseNickname(event);
    }

     */

    /**
     * set nickname and go to loading view
     * @param mouseEvent
     * @throws IOException
     */
    @FXML
    public void nicknameOk (MouseEvent mouseEvent) throws IOException
    {
        messageToServer(CommandType.NICKNAME,nickname.getText());
        changeScene("loading.fxml");

        System.out.println("Premuto ok inserimento nickname");
    }

    /*
    @FXML
    public void showBoard (String active_player_name) throws IOException
    {
        changeScene("board2.fxml");
        set_turn.setText("Turn of " + active_player_name);
    }

     */
    /**
     * set the power and change image in the selected case
     *
     * @param mouseEvent
     * @throws IOException
     */
    @FXML
    public void changeButtonImage (MouseEvent mouseEvent) throws IOException
    {
        if (!button_setpower.isSelected())
        {
            System.out.println("cambia potere in disattivo");
            button_setpower.setText("DISABLE");
            set_power.setImage(new Image(String.valueOf((getClass().getResource("images/heropower_inactive.png")))));
            for (Player p :
                    from_server.getPlayers()) {
                if (p.GetNickname().compareTo(nickname.getText()) == 0)
                    p.GetGodCard().setIn_action(PowerType.DISABLE);
            }
            activedPower(PowerType.DISABLE);
        }

        if(button_setpower.isSelected())
        {
            System.out.println("cambia potere in attivo");
            button_setpower.setText("ACTIVE");
            set_power.setImage(new Image(String.valueOf(getClass().getResource("images/heropower_active.png"))));
            for (Player p :
                    from_server.getPlayers()) {
                if (p.GetNickname().compareTo(nickname.getText()) == 0)
                    p.GetGodCard().setIn_action(PowerType.ACTIVE);
            }
            activedPower(PowerType.ACTIVE);
        }
    }

    /**
     * clean possible cells colored on board
     */
    public void cleanBoard()
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
                    pane.setStyle("-fx-background-color: transparent");
                }
            }
        }
    }

    /*
    @FXML
    public void clickedButtonEndTurn (MouseEvent event)
    {
        messageToServer(CommandType.NEXTTURN);
    }

     */

    /*
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

        if(from_server.getBoard().getActivePlayer().GetGodCard().getCardType()==GodCardType.ENDTURN)
        {
            //abilita click fine turno bottone di endturn() e disabilita il resto
        }
    }

     */

    /**
     * show cells for move or build when the power is activated od deactivated
     *
     * @param powerType
     */
    public void activedPower(PowerType powerType)
    {
        if(from_server.getActivePlayer().GetGodCard().getCardType()==GodCardType.MOVE) activeMoveCells();
        if(from_server.getActivePlayer().GetGodCard().getCardType()==GodCardType.BUILD) activeBuildCells();
    }

    /**
     * send a commmand to server
     *
     * @param cmd_type
     * @param obj
     */
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

    /**
     * send a commmand to server
     *
     * @param cmd_type
     * @param obj
     * @param new_pos
     */
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

    /**
     * send a commmand to server
     *
     * @param cmd_type
     */
    public void messageToServer(CommandType cmd_type) {
        Command cmd_toserver=new Command(cmd_type,from_server.getBoard().getActivePlayer(),null);
        while (true) {
            try {
                ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(cmd_toserver);
                break;
            }
            catch (IOException io)
            {
            }
        }
    }

    /**
     * return the possible moves for the selected worker
     *
     * @param board
     * @param worker_toMove
     * @return
     */
    public ArrayList<int[]> checkMoves(BoardGame board, Worker worker_toMove)
    {
        ArrayList<int[]> possiblemoves= worker_toMove.GetProprietary().GetGodCard().adjacentBoxNotOccupiedNotDome(board, worker_toMove.GetPosition());

        possiblemoves.removeIf(pos -> board.GetLevelBox(pos) - board.GetLevelBox(worker_toMove.GetPosition()) > 1);

        for (int[] pos: possiblemoves)
        {
            for (Player opponent : from_server.getPlayers())
            {
                if((opponent.GetNickname().compareTo(from_server.getBoard().getActivePlayer().GetNickname())==0)&&opponent.GetGodCard().GetOpponentTurn())//check is an opponent && check opponent card act in active player turn
                    if(opponent.GetGodCard().Move(board, worker_toMove,pos)==CommandType.ERROR)//check move is possible for opponent card
                        possiblemoves.remove(pos);
            }
        }
        return possiblemoves;
    }

    /**
     * return the possible build for the selected worker
     *
     * @param board
     * @param builder
     * @return
     */
    public ArrayList<int[]> checkBuilds(BoardGame board, Worker builder)
    {
        ArrayList<int[]> possiblebuild=board.AdjacentBoxforBuild(builder.GetPosition());

        possiblebuild.removeIf(pos -> board.GetLevelBox(pos) == 4);

        for (int[] pos: possiblebuild)
        {
            for (Player opponent : from_server.getPlayers())
            {
                if((opponent.GetNickname().compareTo(from_server.getActivePlayer().GetNickname())==0)&&opponent.GetGodCard().GetOpponentTurn())//check is an opponent && check opponent card act in active player turn
                    if(opponent.GetGodCard().Build(board,builder,pos)==CommandType.ERROR)//check build is possible for opponent card
                        possiblebuild.remove(pos);
            }
        }
        return possiblebuild;
    }

    /**
     * return the possiblemoves for every workers, so if the array is empty the player lose
     * @return
     */
    public ArrayList<int[]> possibleMoves()
    {
        ArrayList<Worker> workers=getWorkers();
        ArrayList<int[]> possiblemoves = null;

        for (Worker w : workers)
        {
            possiblemoves.addAll(checkMoves(from_server.getBoard(),w));
        }
        return possiblemoves;
    }

    /*
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

     */

    /**
     * manage the click on a cell: create a worker, select a worker, move and build
     * @param mouseEvent
     */
    @FXML
    public void selectedCell(MouseEvent mouseEvent)
    {
        int[] new_position= mouseEntered(mouseEvent);

        System.out.println("La posizione cliccata è ( " + new_position[0] + " , "+ new_position[1] + ")" );

        //crea worker
        if(nickname.getText().compareTo(from_server.getActivePlayer().GetNickname())==0&&getWorkers().size()<2)
        {
            if(!from_server.getBoard().GetStateBox(new_position))
                return;
            messageToServer(CommandType.NEWWORKER, new Worker(), new_position);
            setDisable(true);
        }
        //selezione worker
        else if(!from_server.getBoard().GetStateBox(new_position))
        {
            if(from_server.getBoard().GetOccupantProprietary(new_position).GetNickname().compareTo(from_server.getActivePlayer().GetNickname())==0) {
                activeWorker = from_server.getBoard().GetOccupant(new_position);
                if (from_server.getActivePlayer().GetGodCard().getCardType() == GodCardType.MOVE)
                    activeMoveCells();
                else if (from_server.getActivePlayer().GetGodCard().getCardType() == GodCardType.BUILD)
                    activeBuildCells();
            }
            setDisable(false);
        }

        if(activeWorker==null)
        return;

        else if(activeWorker.GetProprietary().GetGodCard().getCardType()==GodCardType.MOVE&&contains(new_position))
        {
            messageToServer(CommandType.MOVE,activeWorker,new_position);
            setDisable(true);
            cleanBoard();
        }
        else if(activeWorker.GetProprietary().GetGodCard().getCardType()==GodCardType.BUILD&&contains(new_position))
        {
            messageToServer(CommandType.BUILD,activeWorker,new_position);
            setDisable(true);
            cleanBoard();
        }
    }

    /**
     * redefinition of list.contains
     *
     * @param new_position
     * @return
     */
    private boolean contains(int[] new_position) {
            for ( int[] i : possibleCells_activeWorker)
            {
                if(Arrays.equals(i, new_position))
                    return true;
            }
            return false;
    }

    /**
     * show the possible cells to move on it
     */
    public void activeMoveCells()
    {
        if(activeWorker==null)
            return;

        cleanBoard();
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
        }
    }

    /**
     * show the cells to build on it
     */
    public void activeBuildCells()
    {
        if(activeWorker==null)
            return;

        cleanBoard();
        possibleCells_activeWorker= checkBuilds(from_server.getBoard(),activeWorker);

        for (int[] pos :
                possibleCells_activeWorker) {

            for (Node child : myboard.getChildren()) {
                Pane pane = (Pane) child;
                Integer r = myboard.getRowIndex(child);
                Integer c = myboard.getColumnIndex(child);
                if(r!=null && r.intValue() == pos[0] && c != null && c.intValue() == pos[1])
                {
                    pane.setStyle("-fx-background-color: #FF0000");
                }
            }
        }
    }

    /**
     * return the workers of the user
     *
     * @return
     */
    public ArrayList<Worker> getWorkers()
    {
        ArrayList<Worker> workers=new ArrayList<Worker>();
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if(!from_server.getBoard().GetStateBox(i,j))
                    if(from_server.getBoard().GetOccupantProprietary(i,j).GetNickname().compareTo(from_server.getBoard().getActivePlayer().GetNickname())==0)
                        workers.add(from_server.getBoard().GetOccupant(i,j));
            }
        }
        return workers;
    }

    /*
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

     */

    /**
     * send end turn to server and clean board
     *
     * @param mouseEvent
     */
    @FXML
    public void endturn(MouseEvent mouseEvent)
    {
        if(!from_server.getActivePlayer().GetGodCard().getCardType().isEndTurn())
            return;
        cleanBoard();
        messageToServer(CommandType.CHANGE_TURN);
    }

    /**
     * enable/disable click on board
     *
     * @param value
     */
    public void setDisable (boolean value)
    {
        myboard.setDisable(value);
        for (Player p : from_server.getPlayers()) {
            if(!p.GetGodCard().getIn_action().isPassive()&&p.GetNickname().compareTo(nickname.getText())==0)
                button_setpower.setDisable(value);
        }

    }
}


