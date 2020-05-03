package it.polimi.ingsw.riccardoemelissa.client;

import it.polimi.ingsw.riccardoemelissa.GameProxy;

import com.sun.prism.Image;
import elements.*;
import it.polimi.ingsw.riccardoemelissa.Command;
import it.polimi.ingsw.riccardoemelissa.CommandType;
import it.polimi.ingsw.riccardoemelissa.exception.SendException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ControllerBoard
{
    private Socket socket;
    private AnchorPane root;//=FXMLLoader.load(getClass().getResource("@../filefxml/menu.fxml"));
    private GameProxy from_server;
    private Stage primary_stage;
    private Scene input_scene;
    private ArrayList<int[]> possibleCells_activeWorker =new ArrayList<>();
    private Worker activeWorker;
    private MouseEvent event;

    @FXML
    public TextField nickname;
    private Stage primaryStage;

    public ControllerBoard(Socket s)
    {
        /*primary_stage=new Stage();
        input_scene =new Scene(root);*/
        socket=s;
    }

    @FXML
    public void startGame(MouseEvent mouseEvent) throws IOException {
        //int num_players = from_server.getPlayers().length;
        int num_players = 1;

        if(num_players == 1)
        changeScene("mode.fxml");

        else
            chooseNickname(mouseEvent);
    }

    @FXML
    public void chooseNickname(MouseEvent mouseEvent) throws IOException {
        changeScene("choose_nickname.fxml");
        messageToServer(CommandType.NICKNAME, nickname.getText());
    }

    private void changeScene(String path) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        InputStream inputStream = getClass().getResource(path).openStream();

        loader.setController(this);

        try {
//            root = loader.load(input);
            root = loader.load(inputStream);
            //root = FXMLLoader.load(resource);
        }
        catch (LoadException e)
        {
            e.printStackTrace();
        }

        //root= FXMLLoader.load(getClass().getResource(path));
    }

    @FXML
    public void twoPlayers (MouseEvent event){
        messageToServer(CommandType.MODE, 2);
    }

    @FXML
    public void threePlayers (MouseEvent event){
        messageToServer(CommandType.MODE, 3);
    }

    @FXML
    public void modeOk (MouseEvent event) throws IOException
    {
        changeScene("choose_nickname.fxml");
    }

    @FXML
    public void nicknameOk (MouseEvent mouseEvent) throws IOException
    {
        changeScene("loading.fxml");
    }

    public void clickImageGodPower(MouseEvent mouseEvent)
    {
        Image image=(Image)mouseEvent.getSource();
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


    public void update(Object obj) {
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

    public void endTurn()
    {
        from_server.getActivePlayer().GetGodCard().resetCard(GodCardType.MOVE);
        messageToServer(CommandType.CHANGE_TURN);
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

    public void start() {
        /*try {
            root = FXMLLoader.load(getClass().getResource("/start.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        primaryStage.setTitle("Santorini");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }
}
