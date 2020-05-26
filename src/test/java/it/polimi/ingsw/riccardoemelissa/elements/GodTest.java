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
        BoardGame boardGame = GameState.getBoard();
        Pan pan = new Pan();
        Worker worker = new Worker();
        int[] oldpos = new int[]{1,1};
        int[] newpos = new int[]{1,2};

        worker.setPosition(oldpos);
        boardGame.setOccupant(oldpos, worker);
        pan.move(boardGame, worker, newpos);

        assertArrayEquals(newpos, worker.getPosition());
    }

    @Test
    void setPosition() {
        BoardGame boardGame = GameState.getBoard();

        assertNull(boardGame.getOccupant(new int []{0,0}));

        int[] oldpos = new int[]{2,3};
        int[] newpos = new int[]{3,3};

        Pan pan = new Pan();
        Worker active_worker = new Worker();

        active_worker.setPosition(oldpos);
        boardGame.setOccupant(oldpos, active_worker);
        assertEquals(active_worker, boardGame.getOccupant(oldpos));

        pan.setPosition(active_worker, oldpos, newpos, boardGame);
        assertNull(boardGame.getOccupant(oldpos));
        assertArrayEquals(newpos, active_worker.getPosition());
        assertEquals(active_worker, boardGame.getOccupant(newpos));
    }

    @Test
    void build() {
        BoardGame boardGame = GameState.getBoard();
        Pan pan = new Pan();
        Worker worker = new Worker();
        int[] oldpos = new int[]{1,1};
        int[] newpos = new int[]{1,2};

        worker.setPosition(oldpos);
        boardGame.setOccupant(oldpos, worker);
        pan.build(boardGame, worker, newpos);

        assertEquals(1, boardGame.getLevelBox(newpos));
    }

    @Test
    void getOpponentTurn() {
        Apollo apollo = new Apollo();
        Athena athena = new Athena();
        apollo.setOpponentTrue("false");
        athena.setOpponentTrue("true");

        assertFalse(apollo.getOpponentTurn());
        assertTrue(athena.getOpponentTurn());
    }
}