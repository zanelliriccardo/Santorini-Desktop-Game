package it.polimi.ingsw.riccardoemelissa;


import elements.Worker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

public class Command extends Observable implements Serializable {
    private Object Obj;
    private CommandType type;
    //private Worker activeWorker;
    private int[] newpos;

    public Command(CommandType t,Object obj_client,int[] pos)
    {
        type=t;
        Obj=obj_client;
        //activeWorker=w;
        newpos=pos;
    }

    public CommandType GetType() {
        return type;
    }

    /*public Worker GetActiveWorker()
    {
        return activeWorker;
    }*/

    public int[] GetPos()
    {
        return newpos;
    }

    public Object GetObj()
    {
        return Obj;
    }
}
