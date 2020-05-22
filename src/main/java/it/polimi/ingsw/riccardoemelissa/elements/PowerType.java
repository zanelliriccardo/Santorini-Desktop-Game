package it.polimi.ingsw.riccardoemelissa.elements;

import java.io.Serializable;

public enum PowerType implements Serializable
{
    PASSIVE,ACTIVE,DISABLE;

    /**
     * Return if a God's power is "ACTIVE"
     * @return
     */
    public boolean isActive() {
        return this==ACTIVE;
    }

    /**
     * Return if a God's power is "PASSIVE"
     * @return
     */
    public boolean isPassive() {
        return this==PASSIVE;
    }
}
