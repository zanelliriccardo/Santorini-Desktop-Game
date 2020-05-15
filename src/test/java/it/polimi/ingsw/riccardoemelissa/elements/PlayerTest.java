package it.polimi.ingsw.riccardoemelissa.elements;

import it.polimi.ingsw.riccardoemelissa.elements.Player;
import it.polimi.ingsw.riccardoemelissa.elements.card.Apollo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest extends Object {

    @Test
    void getNickname() {
        String str = "nickname";
        Player p = new Player(str);

        assertEquals(str, p.GetNickname());
    }

    @Test
    void getColor() {
        String color = "color";
        Player p= new Player("nickname");

        p.SetColor(color);
        assertEquals(color, p.GetColor());
    }

    @Test
    void getGodCard() {
        Apollo apollo = new Apollo();
        Player p = new Player("nickname");

        p.SetGodCard(apollo);
        assertEquals(apollo, p.GetGodCard());
    }

    @Test
    void getGodImagePath() {
        Player p = new Player("nickname");
        String path = "Apollo.png";

        p.setGodImagePath(path);

        assertEquals(path, p.getGodImagePath());
    }
}