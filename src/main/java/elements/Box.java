package elements;

public class Box {
    private boolean state;
    private int level;

    public Box(boolean b, int i) {
        state=b;
        level=i;
    }

    public void ChangeState()
    {
        state = false;
    }

    public void SetLevel (int l)
    {
        level = l;
    }

    public boolean GetState ()
    {
        return state;
    }

    public int GetLevel ()
    {
        return level;
    }
}
