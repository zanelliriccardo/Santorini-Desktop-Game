package it.polimi.ingsw.riccardoemelissa.elements;

import java.io.Serializable;

public enum PowerType implements Serializable
{
    PASSIVE,ACTIVE,DISABLE;

    public boolean IsActive() {
        return this==ACTIVE;
    }

    public boolean IsPassive() {
        return this==PASSIVE;
    }
}
