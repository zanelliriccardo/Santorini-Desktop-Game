package client;

import elements.*;
import it.polimi.ingsw.riccardoemelissa.Command;
import it.polimi.ingsw.riccardoemelissa.CommandType;
import it.polimi.ingsw.riccardoemelissa.GameProxy;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ControllerBoard implements CustomObserver
{
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Parent root;
    private GameProxy from_server;
    private Stage primary_stage;
    private Scene input_scene;
    private ArrayList<int[]> possibleCellsfirstWorker =new ArrayList<>();
    private ArrayList<int[]> possibleCellsSecondWorker =new ArrayList<>();
    private ArrayList<int[]> possibleCells_activeWorker =new ArrayList<>();
    private Worker activeWorker;

    public ControllerBoard(Socket s, Parent root) throws IOException
    {
        primary_stage=new Stage();
        input_scene =new Scene(root);
        in=new ObjectInputStream(s.getInputStream());
        out=new ObjectOutputStream(s.getOutputStream());
        this.root=root;
    }

    @FXML
    public void startGame(MouseEvent mouseEvent) throws IOException {

        changeScene("mode.fxml");//portarlo su scelta 1 o 2 giocatori
    }

    private void changeScene(String s) throws IOException
    {
        root= FXMLLoader.load(getClass().getResource(s));
    }

    public void activedPower()
    {
        from_server.getActivePlayer().GetGodCard().setIn_action(true);

        if(from_server.getActivePlayer().GetGodCard().getType()==GodCardType.MOVE)
            activeMoveCells();
        if(from_server.getActivePlayer().GetGodCard().getType()==GodCardType.BUILD)
            activeBuildCells();
        //mettere le celle possibile blu
    }

    @FXML
    public void selectedCell(MouseEvent mouseEvent)
    {
        Node source=(Node)mouseEvent.getSource();
        int rowIndex=(GridPane.getRowIndex(source))==null ? 0:(GridPane.getRowIndex(source));
        int colIndex=(GridPane.getColumnIndex(source))==null ? 0:(GridPane.getColumnIndex(source)) ;
        int[] new_position= new int[]{rowIndex,colIndex};

        if(from_server.getBoard().GetOccupant(new_position).GetProprietary().GetNickname().compareTo(from_server.getActivePlayer().GetNickname())==0)
        {
            activeWorker = from_server.getBoard().GetOccupant(new_position);
            if(from_server.getActivePlayer().GetGodCard().getType()==GodCardType.MOVE)
                activeMoveCells();
            if(from_server.getActivePlayer().GetGodCard().getType()==GodCardType.BUILD)
                activeBuildCells();
        }

        if(from_server.getActivePlayer().GetGodCard().getType()==GodCardType.MOVE&&possibleCells_activeWorker.contains(new_position))
            from_server.getActivePlayer().GetGodCard().Move(from_server.getBoard(),activeWorker,new_position);
        if(from_server.getActivePlayer().GetGodCard().getType()==GodCardType.BUILD&&possibleCells_activeWorker.contains(new_position))
            from_server.getActivePlayer().GetGodCard().Build(from_server.getBoard(),activeWorker,new_position);
    }

    private void activeBuildCells()
    {
        ArrayList<Worker> workers=from_server.getBoard().getWorkers(from_server.getActivePlayer());
        possibleCells_activeWorker=checkBuild(from_server,activeWorker);
    }

    private void activeMoveCells()
    {
        ArrayList<Worker> workers=from_server.getBoard().getWorkers(from_server.getActivePlayer());
        possibleCells_activeWorker=checkMove(from_server,activeWorker);
    }

    @Override
    public void update(Object obj) {
        from_server=(GameProxy) obj;
        //fare guiii//prometeooooo


    }

    public void messageToServer(CommandType cmd_type,int[] new_pos) {
        Command cmd_toserver=new Command(cmd_type,from_server.getActivePlayer(),new_pos);
        while (true) {
            try {
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
                out.writeObject(cmd_toserver);
                break;
            }
            catch (IOException io){}
        }
    }

    public void Lose(GameProxy game)
    {
        //command lose da fare
    }

    public ArrayList<int[]> checkMove(GameProxy game,Worker worker_toControl)
    {
        ArrayList<int[]> possiblemoves=game.getBoard().AdjacentBox(worker_toControl.GetPosition());

        possiblemoves.removeIf(pos -> game.getBoard().GetLevelBox(pos) - game.getBoard().GetLevelBox(worker_toControl.GetPosition()) < 2);

        for (int[] pos: possiblemoves)
        {
            for (Player opponent : game.getPlayers())
            {
                if((opponent.GetNickname().compareTo(game.getActivePlayer().GetNickname())==0)&&opponent.GetGodCard().GetOpponentTurn())//check is an opponent && check opponent card act in active player turn
                    if(opponent.GetGodCard().Move(game.getBoard(),worker_toControl,pos)==GodCardType.NOTPOSSIBLE);//check move is possible for opponent card
                        possiblemoves.remove(pos);
            }
        }

        return possiblemoves;
    }

    public ArrayList<int[]> checkBuild(GameProxy game,Worker worker_toControl)
    {
        ArrayList<int[]> possiblebuild=game.getBoard().AdjacentBox(worker_toControl.GetPosition());

        possiblebuild.removeIf(pos -> game.getBoard().GetLevelBox(pos) == 4);

        for (int[] pos: possiblebuild)
        {
            for (Player opponent : game.getPlayers())
            {
                if((opponent.GetNickname().compareTo(game.getActivePlayer().GetNickname())==0)&&opponent.GetGodCard().GetOpponentTurn())//check is an opponent && check opponent card act in active player turn
                    if(opponent.GetGodCard().Build(game.getBoard(),worker_toControl,pos)==GodCardType.NOTPOSSIBLE);//check build is possible for opponent card
                        possiblebuild.remove(pos);
            }
        }
        return possiblebuild;
    }
}
