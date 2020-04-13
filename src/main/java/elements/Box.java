package elements;

public class Box {
    private boolean state;
    private int level;
    private String color;

    public Box(boolean b, int i) {
        state=b;
        level=i;
    }

    public void ChangeState(String nick)
    {
        color=nick;
        state=!state;
    }

    public void ChangeState()
    {
        color=null;
        state=!state;
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
}
