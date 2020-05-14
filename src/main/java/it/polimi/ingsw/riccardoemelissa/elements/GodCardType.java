package it.polimi.ingsw.riccardoemelissa.elements;

import java.io.Serializable;

public enum GodCardType  implements Serializable
{
    PASSIVE,MOVE,BUILD,OK,NOTPOSSIBLE,ENDTURN,WIN;

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
}
