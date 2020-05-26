package it.polimi.ingsw.riccardoemelissa.elements;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardGameTest {

    @Test
    void getStateBox() {
        BoardGame boardGame = new BoardGame();
        int[] pos1 = new int[]{2,3};
        int[] pos2 = new int[]{2,3};
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();

        boardGame.getBoard()[pos1[0]][pos1[1]].changeState(worker1);
        assertFalse(boardGame.getStateBox(pos1));

        boardGame.setOccupant(pos2, worker2);
        assertFalse(boardGame.getStateBox(pos2));

        boardGame.removeWorker(pos2);
        assertTrue(boardGame.getStateBox(pos2));
    }

    @Test
    void getLevelBox() {
        BoardGame boardGame = new BoardGame();
        int[] pos1 = new int[] {1,4};
        int[] pos2 = new int[] {1,1};

        boardGame.doBuild(pos1);
        assertEquals(1, boardGame.getLevelBox(pos1));

        boardGame.buildDome(pos2);
        assertEquals(4, boardGame.getLevelBox(pos2));
    }

    @Test
    void getOccupant() {
        int [] pos = new int[]{1,2};
        int [] pos2 = new int[]{1,3};
        Worker worker = new Worker();
        Worker worker2 = new Worker();
        BoardGame boardGame = new BoardGame();

        boardGame.setOccupant(pos, worker);
        assertEquals(worker, boardGame.getOccupant(pos));
        boardGame.setOccupant(pos2, worker2);
        assertEquals(worker2, boardGame.getOccupant(pos2[0], pos2[1]));
    }

    @Test
    void getOccupantProprietary() {
        BoardGame boardGame = new BoardGame();
        Player player = new Player("nickname");
        Worker worker= new Worker();
        worker.setProprietary(player);
        int[] pos = new int[]{1,1};

        worker.setPosition(pos);
        boardGame.setOccupant(pos , worker);

        assertEquals(player, boardGame.getOccupantProprietary(pos));
        assertEquals(player, boardGame.getOccupantProprietary(pos[0], pos[1]));

    }

    @Test
    void getGameover() {
        BoardGame boardGame = new BoardGame();

        boardGame.setGameOver(true);
        assertTrue(boardGame.getGameover());
    }

    @Test
    void removeWorker()
    {
        BoardGame boardGame = new BoardGame();
        Worker worker = new Worker();
        int pos[] = new int[]{4,4};

        worker.setPosition(pos);
        boardGame.setOccupant(pos, worker);
        assertEquals(worker, boardGame.getOccupant(pos));

        boardGame.removeWorker(pos);
        assertNull(boardGame.getOccupant(pos));
    }
}