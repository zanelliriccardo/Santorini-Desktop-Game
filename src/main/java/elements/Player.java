package elements;

import java.net.ServerSocket;

public class Player {
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