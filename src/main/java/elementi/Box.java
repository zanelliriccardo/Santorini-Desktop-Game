package elementi;

public class Box {
    private boolean state;
    private int level;

    public Box ()
    {
        state = true;
        level = 0;
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
