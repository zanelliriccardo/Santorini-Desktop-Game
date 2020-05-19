package it.polimi.ingsw.riccardoemelissa;


import java.io.Serializable;

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

    public CommandType getType() {
        return type;
    }

    public int[] getPos()
    {
        return newpos;
    }

    public Object getObj()
    {
        return Obj;
    }
}
