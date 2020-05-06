package it.polimi.ingsw.riccardoemelissa;

import elements.GodCardType;

import java.io.Serializable;

public enum CommandType implements Serializable {
    NICKNAME,MOVE,BUILD,CHANGE_TURN,DISCONNECTED,RESET,NEWWORKER,WIN,MODE,LOSE,BOARDCHANGE,ERROR,SETPOWER
}
