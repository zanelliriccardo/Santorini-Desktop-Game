package elements;

import java.net.ServerSocket;

public class Player {
    private String nickname;
    //private String worker_color;
    private God god_card;

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
}