package it.polimi.ingsw.riccardoemelissa.elements;

import java.io.Serializable;

public enum GodCardType  implements Serializable
{
    PASSIVE,MOVE,BUILD,OK,NOTPOSSIBLE,ENDTURN,WIN, LOSE;

    public boolean isBuild()
    {
        return this==BUILD;
    }

    public boolean isMove() {
        return this==MOVE;
    }

    public boolean isEndTurn() {
        return this==ENDTURN;
    }

    public boolean isWin() {
        return this==WIN;
    }

    public boolean isLose() {
        return this==LOSE;
    }
}
