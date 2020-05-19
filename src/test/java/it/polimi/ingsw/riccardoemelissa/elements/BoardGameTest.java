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

   /* @Test
    void isAPossibleMove() {
        BoardGame boardGame = new BoardGame();

        int[] oldpos = new int[]{2,3};

        boardGame.DoBuild(oldpos); //oldpos -> level=1

        int[] newpos = new int[]{2,4};

        for (int i = 0; i< 4; i++) {

            if(i <= 2)
                assertTrue(boardGame.IsAPossibleMove(newpos, oldpos));
            else
                assertFalse(boardGame.IsAPossibleMove(newpos,oldpos));
            boardGame.DoBuild(newpos);
        }

        Worker worker = new Worker();
        int[] newpos2 = new int[]{2,2};

        boardGame.GetBoard()[newpos2[0]][newpos2[1]].ChangeState(worker);
        assertFalse(boardGame.IsAPossibleMove(newpos2, oldpos));

        int[] newpos3 = new int[]{2,1};
        boardGame.BuildDome(newpos3);
        assertFalse(boardGame.IsAPossibleMove(newpos3, oldpos));
    }

    */


    //MAI USATO :
    /*@Test
    void isAPossibleBuild() {
        BoardGame boardGame = new BoardGame();

        Worker worker = new Worker();
        int[] oldpos = new int[]{2,3};
        int[] newpos = new int[]{2,4};

        boardGame.DoBuild(oldpos);
        assertTrue(boardGame.IsAPossibleBuild(newpos, oldpos));

        boardGame.setOccupant(newpos, worker);
        assertFalse(boardGame.IsAPossibleBuild(newpos, oldpos)); //mettere a posto codice

        boardGame.BuildDome(newpos);
        assertFalse(boardGame.IsAPossibleBuild(newpos, oldpos));
    }

     */

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

    /*@Test
    void isAdjacentBox() {
        BoardGame boardGame = new BoardGame();
        int[] worker_pos = new int[]{1, 2};
        int[] pos1 = new int[]{1, 1};
        int[] pos2 = new int[]{4, 0};

        assertTrue(boardGame.IsAdjacentBox(worker_pos, pos1));
        assertFalse(boardGame.IsAdjacentBox(worker_pos, pos2));
        assertFalse(boardGame.IsAdjacentBox(worker_pos, worker_pos));
    }

     */

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

    //NON VA IL METODO --> executor client command TEST
    /*@Test
    void getWorkers() {
        BoardGame boardGame = new BoardGame();
        Player player = new Player("nickname");
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        int[] pos1 = new int[]{0,1};
        int[] pos2 = new int[]{1,1};

        worker1.setProprietary(player);
        worker1.SetPosition(pos1);
        boardGame.setOccupant(pos1, worker1);

        worker2.setProprietary(player);
        worker2.SetPosition(pos2);
        boardGame.setOccupant(pos2, worker2);

        boardGame.setActivePlayer(player);

        assertEquals(worker1, boardGame.getWorkers(player).get(0));
        assertEquals(worker2, boardGame.getWorkers(player).get(1));
    }

     */

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