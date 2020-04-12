package elements;

public class BoardGame {
    private Box[][] Board;

    public BoardGame(Box[][] boxes){
        this.Board = boxes;
    }

    public boolean GetStateBox (int x, int y)
    {
        return Board[x][y].GetState();
    }

    public int GetLevelBox (int x, int y)
    {
        return Board[x][y].GetLevel();
    }
}

