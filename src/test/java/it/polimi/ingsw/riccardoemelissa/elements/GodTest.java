package it.polimi.ingsw.riccardoemelissa.elements;

import it.polimi.ingsw.riccardoemelissa.GameState;
import it.polimi.ingsw.riccardoemelissa.elements.card.Apollo;
import it.polimi.ingsw.riccardoemelissa.elements.card.Athena;
import it.polimi.ingsw.riccardoemelissa.elements.card.Pan;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GodTest {

    @Test
    void move() {
    }

    @Test
    void setPosition() {
        BoardGame boardGame = GameState.GetBoard();

        assertNull(boardGame.GetOccupant(new int []{0,0}));

        int[] oldpos = new int[]{2,3};
        int[] newpos = new int[]{3,3};

        Pan pan = new Pan();
        //Player player = new Player("nickname");
        //player.SetGodCard(pan);
        Worker active_worker = new Worker();
        //active_worker.setProprietary(player);

        active_worker.SetPosition(oldpos);
        boardGame.setOccupant(oldpos, active_worker);
        assertEquals(active_worker, boardGame.GetOccupant(oldpos));

        pan.SetPosition(active_worker, oldpos, newpos, boardGame);
        assertNull(boardGame.GetOccupant(oldpos));
        assertArrayEquals(newpos, active_worker.GetPosition());
        assertEquals(active_worker, boardGame.GetOccupant(newpos));
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