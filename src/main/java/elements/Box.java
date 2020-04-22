package elements;

import it.polimi.ingsw.riccardoemelissa.App;

public class Box {
    private boolean state;
    private int level;
    private String color;
    private Worker occupant;

    public Box(boolean b, int i) {
        state=b;
        level=i;
    }

    public void ChangeState(String nick)
    {
        color=nick;
        state=false;
    }

    public void ChangeState()
    {
        color=null;
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

    public String GetColor()
    {
        return color;
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
}
