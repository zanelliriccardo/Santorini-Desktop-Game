package it.polimi.ingsw.riccardoemelissa;

import elements.*;

import java.io.Serializable;
import java.util.Observable;

public class GameProxy extends CustomObservable implements Serializable {
    private Box[][] board_copy;
    private Player active_player;
    private Player[] players;

    public GameProxy(BoardGame getBoard, Player getActivePlayer,Player[] players) {
        board_copy=getBoard.GetBoard();
        active_player=getActivePlayer;
        this.players=players;
    }

    public Player getActivePlayer() {
        return active_player;
    }
}
