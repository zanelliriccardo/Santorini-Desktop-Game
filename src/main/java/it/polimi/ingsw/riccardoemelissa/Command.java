package it.polimi.ingsw.riccardoemelissa;


import elements.Worker;

import java.io.Serializable;
import java.util.ArrayList;

public class Command implements Serializable
{
    private Object Obj;
    private CommandType type;
    //private Worker activeWorker;
    private int[] newpos;

    public Command(CommandType t,Worker w,int[] pos)
    {
        type=t;
        activeWorker=w;
        newpos=pos;
    }

    public CommandType GetType() {
        return type;
    }

    public Worker GetActiveWorker()
    {
        return activeWorker;
    }

    public int[] GetPos()
    {
        return newpos;
    }

    public Object GetObj()
    {
        return Obj;
    }
}
