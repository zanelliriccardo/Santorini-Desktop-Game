package it.polimi.ingsw.riccardoemelissa.elements;

import java.io.Serializable;

public class Worker implements Serializable
{
    private int[] position=new int[]{-1,-1};
    private Player proprietary;

    /**
     * Get the x-coordinate of a worker position
     *
     * @return
     */
    public int getX() {
        return position[0];
    }

    /**
     * Get the y-coordinate of a worker position
     *
     * @return
     */
    public int getY() {
        return position[1];
    }

    /**
     * Get the proprietary of a worker
     *
     * @return
     */
    public Player getProprietary() {
        return proprietary;
    }

    /**
     * Set the position of a worker
     *
     * @param x
     * @param y
     */
    public void setPosition(int x, int y)
    {
        position[0] = x;
        position[1] = y;
    }

    /**
     * Set the position of a worker
     *
     * @param pos
     */
    public void setPosition(int[] pos)
    {
        position=pos;
        //position=pos;
    }

    /**
     * Get the position of a worker
     *
     * @return
     */
    public int[] getPosition() {
        return position;
    }

    /**
     * Set the proprietary of a worker
     *
     * @param activePlayer
     */
    public void setProprietary(Player activePlayer) {
        proprietary=activePlayer;
    }
}