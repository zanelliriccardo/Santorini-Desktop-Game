package elementi;

public class BoardGame {
    private Box[][] boxes;

    public BoardGame (){
        boxes = new Box [5][5];
    }

    public boolean GetStateBox (int x, int y)
    {
        return boxes[x][y].GetState();
    }

    public int GetLevelBox (int x, int y)
    {
        return boxes[x][y].GetLevel();
    }
}

