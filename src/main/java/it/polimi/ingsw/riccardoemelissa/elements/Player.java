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

    /**
     * Get the nickname of a player
     * @return
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Get the God card of a player
     * @param god
     */
    public void setGodCard(God god) {
        god_card=god;
    }

    /**
     * Set the color of a player
     * @param color
     */
    public void setColor(String color)
    {
        this.color = color;
    }

    /**
     * Get the color of a player
     * @return
     */
    public String getColor()
    {
        return color;
    }

    /**
     * Get the God card of a player
     * @return
     */
    public God getGodCard()
    {
        return god_card;
    }

    /**
     * Set the nickname of a player
     * @param getObj
     */
    public void setNickname(String getObj)
    {
        nickname=getObj;
    }

    /**
     * Set the image path of a player's God card
     * @param path
     */
    public void setGodImagePath(String path) {
        godImagePath=path;
    }

    /**
     * Get the image path of a player's God card
     * @return
     */
    public String getGodImagePath()
    {
        return godImagePath;
    }
}
