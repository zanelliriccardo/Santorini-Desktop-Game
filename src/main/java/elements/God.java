package elements;

public abstract class God {

    public void Turn()
    {

    }

    public boolean Move(BoardGame b, Worker activeWorker, int[] newpos)
    {
            int[] oldpos=activeWorker.GetPosition();
            if(b.IsAPossibleMove(newpos,oldpos))
            {
                activeWorker.SetPosition(newpos);
                b.ChangeState(newpos,activeWorker.GetProprietary().GetColor());
                b.ChangeState(oldpos);
                return true;
            }
        return false;
    }

    public boolean Build(BoardGame b, Worker activeWorker, int[] pos)
    {
            int[] workerpos=activeWorker.GetPosition();

            if(b.IsAPossibleBuild(pos,workerpos))
            {
                b.DoBuild(pos);
                return true;
            }
            else
                return false;
        }
    }
