package it.polimi.ingsw.riccardoemelissa;

import it.polimi.ingsw.riccardoemelissa.elements.*;

import java.io.Serializable;
import java.util.ArrayList;

public class GameProxy implements Serializable {
    private BoardGame board_copy;
    private Worker active_worker;
    private ArrayList<Player> players;

    public GameProxy(BoardGame getBoard, Worker getActivePlayer, ArrayList<Player> players) {
        board_copy=getBoard;
        active_worker=getActivePlayer;
        this.players=players;
    }

    public Worker getActive_worker() {
        return active_worker;
    }

    public BoardGame getBoard() {
        return board_copy;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setGame(GameProxy fromServer) {
        this.board_copy=fromServer.getBoard();
        this.active_worker=fromServer.getActive_worker();
        this.players=fromServer.getPlayers();
    }
}
