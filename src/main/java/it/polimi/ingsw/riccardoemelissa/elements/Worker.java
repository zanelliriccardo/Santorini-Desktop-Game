package it.polimi.ingsw.riccardoemelissa.elements;

import java.io.Serializable;

public class Worker implements Serializable
{
    private int[] position=new int[]{-1,-1};
    private Player proprietary;

    public int getX() {
        return position[0];
    }

    public int getY() {
        return position[1];
    }

    public Player getProprietary() {
        return proprietary;
    }

    public void setPosition(int x, int y)
    {
        position[0] = x;
        position[1] = y;
    }

    public void setPosition(int[] pos)
    {
        position=pos;
        //position=pos;
    }

    public int[] getPosition() {
        return position;
    }

    public void setProprietary(Player activePlayer) {
        proprietary=activePlayer;
    }
}