package it.polimi.ingsw.riccardoemelissa.elements;

import java.io.Serializable;

public class Worker implements Serializable
{
    private int[] position=new int[]{-1,-1};
    private Player proprietary;

    public int GetX() {
        return position[0];
    }

    public int GetY() {
        return position[1];
    }

    public Player GetProprietary() {
        return proprietary;
    }

    public void SetPosition (int x, int y)
    {
        position[0] = x;
        position[1] = y;
    }

    public void SetPosition (int[] pos)
    {
        position=pos;
        //position=pos;
    }

    public int[] GetPosition() {
        return position;
    }

    public void setProprietary(Player activePlayer) {
        proprietary=activePlayer;
    }
}