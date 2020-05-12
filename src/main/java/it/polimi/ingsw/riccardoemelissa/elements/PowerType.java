package it.polimi.ingsw.riccardoemelissa.elements;

import java.io.Serializable;

public enum PowerType implements Serializable
{
    PASSIVE,ACTIVE,DISABLE;

    public boolean isActive() {
        return this==ACTIVE;
    }

    public boolean isPassive() {
        return this==PASSIVE;
    }
}
