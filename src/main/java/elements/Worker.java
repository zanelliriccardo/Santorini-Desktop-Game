package elements;

public class Worker
{
    private int[] position;
    private Player proprietary;

    public Worker ()
    {
        position = new int[2];
    }

    public void NewPosition (int x, int y)
    {
        position[0] = x;
        position[1] = y;
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

    public void SetPosition (int x, int y) {}


   public void SetProprietary (Player p)
   {
       proprietary = p;
   }

}