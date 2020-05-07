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

    public void ChangeState(Worker worker)
    {
        occupant=worker;
        state=false;
    }

    public void ChangeState()
    {
        state=true;
    }

    public boolean GetState ()
    {
        return state;
    }

    public int GetLevel ()
    {
        return level;
    }

    public void Build()
    {
        level++;
    }

    public Worker GetOccupant() {
        return occupant;
    }

    public void SetOccupant(Worker worker) {
        occupant= worker;
        state=false;
    }

    public void SetDome()
    {
        level=4;
    }

    public void removeOccupant() {
        occupant=null;
        state=true;
    }
}
