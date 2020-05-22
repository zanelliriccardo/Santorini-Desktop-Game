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

    /**
     * Change the status of a box
     * @param worker
     */
    public void changeState(Worker worker)
    {
        occupant=worker;
        state=false;
    }

    /**
     * Change the status of a box
     */
    public void changeState()
    {
        state=true;
    }

    /**
     * Get the status of a box
     * @return
     */
    public boolean getState()
    {
        return state;
    }

    /**
     * Get the level of a box
     * @return
     */
    public int getLevel()
    {
        return level;
    }

    /**
     * Build a block
     */
    public void build()
    {
        level++;
    }

    /**
     * Get the occupant of a box
     * @return
     */
    public Worker getOccupant() {
        return occupant;
    }

    /**
     * Put a box occupied by a worker
     * @param worker
     */
    public void setOccupant(Worker worker) {
        occupant= worker;
        state=false;
    }

    /**
     * Build a dome
     */
    public void setDome()
    {
        level=4;
    }

    /**
     * Put an unoccupied box
     */
    public void removeOccupant() {
        occupant=null;
        state=true;
    }
}
