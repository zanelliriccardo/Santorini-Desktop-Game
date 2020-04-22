package elements;

public class Atlas extends God {
    private boolean opponent_turn = false;

    @Override
    public boolean Build(BoardGame b, Worker activeWorker, int[] pos)
    {

        if(/*messagio per la possibilità di costruire cupola*/true&&b.IsAPossibleBuild(pos,activeWorker.GetPosition()))
        {
            b.BuildDome(pos);
        }
        return false;
    }


}
