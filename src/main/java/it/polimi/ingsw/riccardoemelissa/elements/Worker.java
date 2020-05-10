package it.polimi.ingsw.riccardoemelissa.elements;

import java.awt.*;
import java.io.Serializable;

public class Worker  implements Serializable
{
    private int[] position;
    private Player proprietary;

    public Worker(Player proprietary,int[] pos)
    {
        position = pos;
        this.proprietary=proprietary;
    }

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
        position[0] = pos[0];
        position[1] = pos[1];
    }

    public int[] GetPosition() {
        return position;
    }
}