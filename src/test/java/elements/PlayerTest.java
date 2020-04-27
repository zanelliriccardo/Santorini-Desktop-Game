package elements;

import elements.card.Apollo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest extends Object {

    @Test
    void getNickname() {
        String str = "name";
        Player p = new Player(str);

        assertEquals(str, p.GetNickname());
    }

    @Test
    void getColor() {
        String color = "red";
        Player p= new Player("name");

        p.SetColor(color);

        assertEquals(color, p.GetColor());
    }

    @Test
    void getGodCard() {
        Apollo apollo = new Apollo();
        Player p = new Player("name");

        p.SetGodCard(apollo);

        assertEquals(apollo, p.GetGodCard());
    }
}