package it.polimi.ingsw.riccardoemelissa.client;

import it.polimi.ingsw.riccardoemelissa.elements.*;
import it.polimi.ingsw.riccardoemelissa.Command;
import it.polimi.ingsw.riccardoemelissa.CommandType;
import it.polimi.ingsw.riccardoemelissa.GameProxy;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
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
     * Get the cell clicked by user
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

        } catch (Exception exception) {
            colIndex = GridPane.getColumnIndex(source.getParent());
            rowIndex = GridPane.getRowIndex(source.getParent());
        }
        return new int[]{rowIndex, colIndex};
    }

    /**
     * Set and show gui
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

    primary= primaryStage;
    activeWorker=null;
    loader=new FXMLLoader();

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

    /**
     * Notify the server that a user has closed the game
     *
     * @throws Exception
     */
    @Override
    public void stop() throws Exception {
        if((loader.getLocation().getPath()).compareTo(getClass().getResource("board2.fxml").getPath())==0)
            messageToServer(CommandType.DISCONNECTED);
        socket.close();
        super.stop();
    }

    public static void main(String[] args) {
        socket = null;

        try {
            socket = new Socket(InetAddress.getLocalHost(), 33500);
        } catch (IOException ignored) {
        }
        launch(args);
    }

    /**
     * Start the game, so if another player have created the game the user join it
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
        }
        else {
            changeScene("choose_nickname.fxml");
        }
    }

    /**
     * Ask for an update on the status of the game
     */
    public void callUpdate_fromServer()
    {
        try
        {
            messageToServer(CommandType.UPDATE);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Change the user view
     *
     * @param path: fxml path
     * @throws IOException
     */
    public void changeScene(String path) throws IOException
    {
        root=null;
        loader=null;
        loader=new FXMLLoader();

        URL newresource=getClass().getResource(path);

        loader.setLocation(newresource);
        loader.setController(this);
        root = loader.load();

        Scene s=new Scene(root);
        primary.setScene(s);

        primary.show();
    }

    /**
     * Set number of players and change to nickname view
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void twoPlayers (MouseEvent event) throws IOException {
        messageToServer(CommandType.MODE, 2);
        changeScene("choose_nickname.fxml");
    }

    /**
     * Set number of players and change to nickname view
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void threePlayers (MouseEvent event) throws IOException {
        messageToServer(CommandType.MODE, 3);
        changeScene("choose_nickname.fxml");
    }

    /**
     * Set nickname and go to loading view
     * @param mouseEvent
     * @throws IOException
     */
    @FXML
    public void nicknameOk (MouseEvent mouseEvent) throws IOException
    {
        ArrayList<Player> players=new ArrayList<Player>();
        players.addAll(from_server.getPlayers());
        messageToServer(CommandType.NICKNAME,nickname.getText());
        for (int i = 0; i < players.size(); i++) {
            if(players.get(i).getNickname().compareTo("nome")==0)
            {
                nickname.setText(nickname.getText()+"#"+i);
                break;
            }
        }
        changeScene("loading.fxml");
    }

    /**
     * Set the power and change image in the selected case
     *
     * @param mouseEvent
     * @throws IOException
     */
    @FXML
    public void changeButtonImage (MouseEvent mouseEvent) throws IOException
    {
        if (!button_setpower.isSelected())
        {
            button_setpower.setText("INACTIVE");
            set_power.setImage(new Image(String.valueOf((getClass().getResource("images/heropower_inactive.png")))));
            for (Player p :
                    from_server.getPlayers()) {
                if (p.getNickname().compareTo(nickname.getText()) == 0)
                    p.getGodCard().setIn_action(PowerType.DISABLE);
            }
            activedPower(PowerType.DISABLE);
        }

        if(button_setpower.isSelected())
        {
            button_setpower.setText("ACTIVE");
            set_power.setImage(new Image(String.valueOf(getClass().getResource("images/heropower_active.png"))));
            for (Player p :
                    from_server.getPlayers()) {
                if (p.getNickname().compareTo(nickname.getText()) == 0)
                    p.getGodCard().setIn_action(PowerType.ACTIVE);
            }
            activedPower(PowerType.ACTIVE);
        }
    }

    /**
     * Clean possible cells colored on board
     */
    public void cleanBoard()
    {
        for (int[] pos :
                possibleCells_activeWorker)
        {
            for (Node child : myboard.getChildren()) {
                Pane pane = (Pane) child;
                Integer r = GridPane.getRowIndex(child);
                Integer c = GridPane.getColumnIndex(child);
                if(r!=null && r == pos[0] && c != null && c == pos[1])
                {
                    pane.setStyle("-fx-background-color: default");
                }
            }
        }
    }

    /**
     * Show cells for move or build when the power is activated od deactivated
     *
     * @param powerType
     */
    public void activedPower(PowerType powerType)
    {
        if(from_server.getActivePlayer().getGodCard().getCardType()==GodCardType.MOVE) activeMoveCells();
        if(from_server.getActivePlayer().getGodCard().getCardType()==GodCardType.BUILD) activeBuildCells();
    }

    /**
     * Send a commmand to server
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
            catch (IOException ignored){}
        }
    }

    /**
     * Send a commmand to server
     *
     * @param cmd_type
     * @param obj
     * @param new_pos
     */
    public void messageToServer(CommandType cmd_type,Object obj,int[] new_pos) {
        Command cmd_toserver=new Command(cmd_type,obj,new_pos);
        while (true) {
            try {
                ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(cmd_toserver);
                break;
            }
            catch (IOException ignored){}
        }
    }

    /**
     * Send a commmand to server
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
            catch (IOException ignored)
            {
            }
        }
    }

    /**
     * Return the possible moves for the selected worker
     *
     * @param board
     * @param worker_toMove
     * @return
     */
    public ArrayList<int[]> checkMoves(BoardGame board, Worker worker_toMove)
    {
        ArrayList<int[]> possiblemoves= worker_toMove.getProprietary().getGodCard().adjacentBoxNotOccupiedNotDome(board, worker_toMove.getPosition());

        possiblemoves.removeIf(pos -> board.getLevelBox(pos) - board.getLevelBox(worker_toMove.getPosition()) > 1);

        ArrayList<int[]> removes=new ArrayList<>();

        for (int[] pos: possiblemoves)
        {
            for (Player opponent : from_server.getPlayers())
            {
                if((opponent.getNickname().compareTo(from_server.getBoard().getActivePlayer().getNickname())!=0)&&opponent.getGodCard().getOpponentTurn())//check is an opponent && check opponent card act in active player turn
                    if(opponent.getGodCard().move(board, worker_toMove,pos)==CommandType.ERROR)//check move is possible for opponent card
                        removes.add(pos);
            }
        }
        possiblemoves.removeAll(removes);
        return possiblemoves;
    }

    /**
     * Return the possible build for the selected worker
     *
     * @param board
     * @param builder
     * @return
     */
    public ArrayList<int[]> checkBuilds(BoardGame board, Worker builder)
    {
        ArrayList<int[]> possiblebuild=builder.getProprietary().getGodCard().adjacentBoxNotOccupiedNotDome(board,builder.getPosition());

        possiblebuild.removeIf(pos -> board.getLevelBox(pos) == 4);

        return possiblebuild;
    }

    /**
     * Manage the click on a cell: create a worker, select a worker, move and build
     * @param mouseEvent
     */
    @FXML
    public void selectedCell(MouseEvent mouseEvent)
    {
        int[] new_position= mouseEntered(mouseEvent);

        //create a worker
        if(nickname.getText().compareTo(from_server.getActivePlayer().getNickname())==0&&getWorkers().size()<2)
        {
            if(!from_server.getBoard().getStateBox(new_position))
                return;
            messageToServer(CommandType.NEWWORKER, new Worker(), new_position);
            setDisable(true);
        }
        //select worker
        else if(!from_server.getBoard().getStateBox(new_position)&&modifiable_selectedWorker)
        {
            if(from_server.getBoard().getOccupantProprietary(new_position).getNickname().compareTo(from_server.getActivePlayer().getNickname())==0) {
                activeWorker = from_server.getBoard().getOccupant(new_position);
                if (from_server.getActivePlayer().getGodCard().getCardType() == GodCardType.MOVE)
                    activeMoveCells();
                else if (from_server.getActivePlayer().getGodCard().getCardType() == GodCardType.BUILD)
                    activeBuildCells();
            }
        }

        if(activeWorker==null)
        return;
        // do move
        else if(activeWorker.getProprietary().getGodCard().getCardType()==GodCardType.MOVE&&contains(new_position))
        {
            messageToServer(CommandType.MOVE,activeWorker,new_position);
            myboard.setDisable(true);
            button_setpower.setDisable(true);
            modifiable_selectedWorker=false;
            cleanBoard();
        }
        // do build
        else if(activeWorker.getProprietary().getGodCard().getCardType()==GodCardType.BUILD&&contains(new_position))
        {
            messageToServer(CommandType.BUILD,activeWorker,new_position);
            myboard.setDisable(true);
            button_setpower.setDisable(true);
            modifiable_selectedWorker=false;
            cleanBoard();
        }
    }

    /**
     * Redefinition of list.contains
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
     * Show the possible cells to move on it
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
                Integer r = GridPane.getRowIndex(child);
                Integer c = GridPane.getColumnIndex(child);
                if(r!=null && r == pos[0] && c != null && c == pos[1])
                {
                    pane.setStyle("-fx-background-color: #1E90FF");
                }
            }
        }
    }

    /**
     * Show the cells to build on it
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
                Integer r = GridPane.getRowIndex(child);
                Integer c = GridPane.getColumnIndex(child);
                if(r!=null && r == pos[0] && c != null && c == pos[1])
                {
                    pane.setStyle("-fx-background-color: #FF0000");
                }
            }
        }
    }

    /**
     * Return the workers of the user
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
                if(!from_server.getBoard().getStateBox(i,j))
                    if(from_server.getBoard().getOccupantProprietary(i,j).getNickname().compareTo(from_server.getBoard().getActivePlayer().getNickname())==0)
                        workers.add(from_server.getBoard().getOccupant(i,j));
            }
        }
        return workers;
    }

    /**
     * Send end turn to server and clean board
     *
     * @param mouseEvent
     */
    @FXML
    public void endTurn (MouseEvent mouseEvent)
    {
        if(!from_server.getActivePlayer().getGodCard().getCardType().isEndTurn())
            return;
        cleanBoard();
        messageToServer(CommandType.CHANGE_TURN);
    }

    /**
     * Enable/disable click on board
     *
     * @param value
     */
    public void setDisable (boolean value)
    {
        myboard.setDisable(value);
        for (Player p : from_server.getPlayers()) {
            if(!p.getGodCard().getIn_action().isPassive()&&p.getNickname().compareTo(nickname.getText())==0)
                button_setpower.setDisable(value);
        }

    }

    /**
     * Update the possible cell for move/build using the active worker
     */
    public void updatePossibleCell()
    {
        if(from_server.getActive_worker()!=null&&from_server.getActivePlayer().getNickname().compareTo(nickname.getText())==0)
        {
            activeWorker = from_server.getBoard().getOccupant(from_server.getActive_worker().getPosition());

            if (activeWorker.getProprietary().getGodCard().getCardType().isMove()&&from_server.getActive_worker().getProprietary().getNickname().compareTo(nickname.getText())==0)
                activeMoveCells();
            else if (activeWorker.getProprietary().getGodCard().getCardType().isBuild()&&from_server.getActive_worker().getProprietary().getNickname().compareTo(nickname.getText())==0)
                activeBuildCells();
            modifiable_selectedWorker=false;
        }
        else
        {
            cleanBoard();
            activeWorker=null;
            modifiable_selectedWorker=true;
        }
    }

    /**
     * Check if is the player turn
     * @return
     */
    public boolean isMyTurn() {
        return from_server.getBoard().getActivePlayer().getNickname().compareTo(nickname.getText())==0;
    }

    /**
     * Check if this client win or lose, change scene and close the connection to server
     */
    public void checkGameOver() {
        for (Player p : from_server.getPlayers()) {
            if (p.getNickname().compareTo(nickname.getText()) == 0)
                try {
                    if (p.getGodCard().getCardType().isWin()) {
                        changeScene("winner.fxml");
                        socket.close();
                    }
                    else if (p.getGodCard().getCardType().isLose()&&from_server.getBoard().getGameover()) {
                        changeScene("loser.fxml");
                        socket.close();
                    }
                    else if(p.getGodCard().getCardType().isLose())
                    {
                        changeScene("disconnect.fxml");
                        socket.close();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

        }
    }
}


