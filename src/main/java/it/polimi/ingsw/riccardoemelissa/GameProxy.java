package it.polimi.ingsw.riccardoemelissa;

import it.polimi.ingsw.riccardoemelissa.elements.*;

import java.io.Serializable;
import java.util.ArrayList;

public class GameProxy implements Serializable {
    private BoardGame board_copy;
    private Player active_player;
    private ArrayList<Player> players;

    public GameProxy(BoardGame getBoard, Player getActivePlayer, ArrayList<Player> players) {
        board_copy=getBoard;
        active_player=getActivePlayer;
        this.players=players;
    }

    public Player getActivePlayer() {
        return active_player;
    }

    public BoardGame getBoard() {
        return board_copy;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setGame(GameProxy fromServer) {
        this.board_copy=fromServer.getBoard();
        this.active_player=fromServer.getActivePlayer();
        this.players=fromServer.getPlayers();
    }
}
