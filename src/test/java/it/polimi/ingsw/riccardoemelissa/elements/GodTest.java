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
        BoardGame boardGame = GameState.GetBoard();
        Pan pan = new Pan();
        Worker worker = new Worker();
        int[] oldpos = new int[]{1,1};
        int[] newpos = new int[]{1,2};

        worker.SetPosition(oldpos);
        boardGame.setOccupant(oldpos, worker);
        pan.Move(boardGame, worker, newpos);

        assertArrayEquals(newpos, worker.GetPosition());
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
        BoardGame boardGame = GameState.GetBoard();
        Pan pan = new Pan();
        Worker worker = new Worker();
        int[] oldpos = new int[]{1,1};
        int[] newpos = new int[]{1,2};

        worker.SetPosition(oldpos);
        boardGame.setOccupant(oldpos, worker);
        pan.Build(boardGame, worker, newpos);

        assertEquals(1, boardGame.GetLevelBox(newpos));
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

    @Test //usato in altri metodi
    void adjacentBoxNotOccupiedNotDome() {
    }

    @Test //No usato
    void doPower() {
    }
}