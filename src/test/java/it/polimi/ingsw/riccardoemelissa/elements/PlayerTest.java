package it.polimi.ingsw.riccardoemelissa.elements;

import it.polimi.ingsw.riccardoemelissa.elements.card.Apollo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest extends Object {

    @Test
    void getNickname() {
        String str = "nickname";
        Player p = new Player(str);

        assertEquals(str, p.getNickname());
    }

    @Test
    void getColor() {
        String color = "color";
        Player p= new Player("nickname");

        p.setColor(color);
        assertEquals(color, p.getColor());
    }

    @Test
    void getGodCard() {
        Apollo apollo = new Apollo();
        Player p = new Player("nickname");

        p.setGodCard(apollo);
        assertEquals(apollo, p.getGodCard());
    }

    @Test
    void getGodImagePath() {
        Player p = new Player("nickname");
        String path = "Apollo.png";

        p.setGodImagePath(path);

        assertEquals(path, p.getGodImagePath());
    }
}