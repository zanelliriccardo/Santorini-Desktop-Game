package it.polimi.ingsw.riccardoemelissa.elements;

import it.polimi.ingsw.riccardoemelissa.elements.card.Apollo;
import it.polimi.ingsw.riccardoemelissa.elements.card.Athena;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GodTest {

    @Test
    void move() {
    }

    @Test
    void setPosition() {
    }

    @Test
    void build() {
    }

    @Test
    void getOpponentTurn() {
        Apollo apollo = new Apollo();
        Athena athena = new Athena();
        apollo.setOpponentTrue("false");
        athena.setOpponentTrue("true");

        assertFalse(apollo.GetOpponentTurn());
        assertTrue(athena.GetOpponentTurn());
    }

    @Test
    void getCardType() {
    }

    @Test
    void adjacentBoxNotOccupiedNotDome() {
    }

    @Test
    void setIn_action() {
    }

    @Test
    void getIn_action() {
    }

    @Test
    void doPower() {
    }

    @Test
    void resetCard() {
    }

    @Test
    void setCardType() {
    }
}