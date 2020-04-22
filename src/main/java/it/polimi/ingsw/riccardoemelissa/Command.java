package it.polimi.ingsw.riccardoemelissa;


import elements.Worker;

import java.io.Serializable;

public class Command implements Serializable
{
    private CommandType type;
    private Worker activeWorker;
    private int[] newpos;

    public Command(CommandType t,Worker w,int[] pos)
    {
        type=t;
        activeWorker=w;
        newpos=pos;
    }


}
