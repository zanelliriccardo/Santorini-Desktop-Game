package it.polimi.ingsw.riccardoemelissa.elements;

import java.io.Serializable;

public enum GodCardType  implements Serializable
{
    PASSIVE,MOVE,BUILD,OK,NOTPOSSIBLE,ENDTURN,WIN, LOSE;

    /**
     * Return if the type of the God card is "BUILD"
     *
     * @return
     */
    public boolean isBuild()
    {
        return this==BUILD;
    }

    /**
     * Return if the type of the God card is "MOVE"
     * @return
     */
    public boolean isMove() {
        return this==MOVE;
    }

    /**
     * Return if the type of the God card is "ENDTURN"
     * @return
     */
    public boolean isEndTurn() {
        return this==ENDTURN;
    }

    /**
     * Return if the type of the God card is "WIN"
     * @return
     */
    public boolean isWin() {
        return this==WIN;
    }

    /**
     * Return if the type of the God card is "LOSE"
     * @return
     */
    public boolean isLose() {
        return this==LOSE;
    }
}
