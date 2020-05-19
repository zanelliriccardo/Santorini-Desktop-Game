package it.polimi.ingsw.riccardoemelissa.elements;

import java.io.Serializable;

public class Box implements Serializable {
    private boolean state;
    private int level;
    private Worker occupant;

    public Box() {
        this.state=true;
        this.level=0;
    }

    public Box(boolean state, int level) {
        this.state=state;
        this.level=level;
    }

    public void changeState(Worker worker)
    {
        occupant=worker;
        state=false;
    }

    public void changeState()
    {
        state=true;
    }

    public boolean getState()
    {
        return state;
    }

    public int getLevel()
    {
        return level;
    }

    public void build()
    {
        level++;
    }

    public Worker getOccupant() {
        return occupant;
    }

    public void setOccupant(Worker worker) {
        occupant= worker;
        state=false;
    }

    public void setDome()
    {
        level=4;
    }

    public void removeOccupant() {
        occupant=null;
        state=true;
    }
}
