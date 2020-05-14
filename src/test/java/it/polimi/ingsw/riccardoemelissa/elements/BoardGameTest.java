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

        boardGame.GetBoard()[pos1[0]][pos1[1]].ChangeState(worker1);
        assertFalse(boardGame.GetStateBox(pos1));

        boardGame.setOccupant(pos2, worker2);
        assertFalse(boardGame.GetStateBox(pos2));

        boardGame.removeWorker(pos2);
        assertTrue(boardGame.GetStateBox(pos2));
    }

    @Test
    void getLevelBox() {
        BoardGame boardGame = new BoardGame();
        int[] pos1 = new int[] {1,4};
        int[] pos2 = new int[] {1,1};

        boardGame.DoBuild(pos1);
        assertEquals(1, boardGame.GetLevelBox(pos1));

        boardGame.BuildDome(pos2);
        assertEquals(4, boardGame.GetLevelBox(pos2));
    }

    @Test
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
    void isABlockedWorker() {
    }

    @Test
    void getOccupant() {
        int [] pos = new int[]{1,2};
        Worker worker = new Worker();
        BoardGame boardGame = new BoardGame();

        boardGame.setOccupant(pos, worker);
        assertEquals(worker, boardGame.GetOccupant(pos));
    }

    @Test
    void isAdjacentBox() {
        BoardGame boardGame = new BoardGame();
        int[] worker_pos = new int[]{1, 2};
        int[] pos1 = new int[]{1, 1};
        int[] pos2 = new int[]{4, 0};

        assertTrue(boardGame.IsAdjacentBox(worker_pos, pos1));
        assertFalse(boardGame.IsAdjacentBox(worker_pos, pos2));
        assertFalse(boardGame.IsAdjacentBox(worker_pos, worker_pos));
    }
}