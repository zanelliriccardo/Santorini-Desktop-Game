package it.polimi.ingsw.riccardoemelissa.elements;

import java.io.Serializable;

public class Player  implements Serializable {
    private String nickname;
    private God god_card;
    private String color;
    private String godImagePath;

    public Player (String str)
    {
        nickname = str;
    }


    public String getNickname() {
        return nickname;
    }

    public void setGodCard(God god) {
        god_card=god;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public String getColor()
    {
        return color;
    }

    public God getGodCard()
    {
        return god_card;
    }

    public void setNickname(String getObj)
    {
        nickname=getObj;
    }

    public void setGodImagePath(String path) {
        godImagePath=path;
    }

    public String getGodImagePath()
    {
        return godImagePath;
    }
}
