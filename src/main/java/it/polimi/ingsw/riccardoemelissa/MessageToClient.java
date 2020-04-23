package it.polimi.ingsw.riccardoemelissa;

import elements.*;

import java.io.Serializable;
import java.util.Observable;

public class MessageToClient implements Serializable {
    private Box[][] board_copy;
    private Player active_player;

    public MessageToClient(BoardGame getBoard, Player getActivePlayer) {
        board_copy=getBoard.GetBoard();
        active_player=getActivePlayer;
    }

}
