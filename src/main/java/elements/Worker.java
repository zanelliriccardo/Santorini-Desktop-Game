package elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Worker
{
    private int[] position;
    private Player proprietary;

    public Worker(Player player)
    {
        proprietary=player;
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

    public void SetProprietary (Player p)
    {
       proprietary = p;
    }

}