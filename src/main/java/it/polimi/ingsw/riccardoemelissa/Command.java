package it.polimi.ingsw.riccardoemelissa;


import elements.CustomObservable;
import elements.Worker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

public class Command implements Serializable {
    private Object Obj;
    private CommandType type;
    private int[] newpos;

    public Command(CommandType t,Object obj_client,int[] pos)
    {
        type=t;
        Obj=obj_client;
        newpos=pos;
    }

    public CommandType GetType() {
        return type;
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
