package it.polimi.ingsw.riccardoemelissa.elements;

import java.io.Serializable;

public class Player  implements Serializable {
    private String nickname;
    //private String worker_color;
    private God god_card;
    private String color;

    public Player (String str)
    {
        nickname = str;
    }


    public String GetNickname() {
        return nickname;
    }

    public void SetGodCard(God god) {
        god_card=god;
    }

    public void SetColor(String color)
    {
        this.color = color;
    }

    public String GetColor()
    {
        return color;
    }

    public God GetGodCard()
    {
        return god_card;
    }

    public void SetNickname(String getObj)
    {
        nickname=getObj;
    }
}