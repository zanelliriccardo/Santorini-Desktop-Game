package elements;

public class Demeter extends God {
    private boolean opponent_turn = false;
    @Override
    public boolean Build(BoardGame b, Worker activeWorker, int[] pos)
    {
        if(b.IsAPossibleBuild(pos,activeWorker.GetPosition()))
        {
            b.DoBuild(pos);
            if(/*messaggio per costruire ancora and position*/true) {
                return secondBuild(b, activeWorker, pos);
            }
            return true;
        }
        return false;
    }

    public boolean secondBuild(BoardGame b, Worker activeWorker, int[] pos)
    {
        if(b.IsAPossibleBuild(pos,activeWorker.GetPosition()))
        {
            b.DoBuild(pos);
            return true;
        }
        return false;
    }
}