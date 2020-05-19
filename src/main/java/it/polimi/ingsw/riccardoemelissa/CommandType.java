package it.polimi.ingsw.riccardoemelissa;

import java.io.Serializable;

public enum CommandType implements Serializable {
    NICKNAME,MOVE,BUILD,CHANGE_TURN,DISCONNECTED,NEWWORKER,MODE,ERROR,UPDATE;
}
