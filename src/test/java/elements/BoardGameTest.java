package elements;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameTest extends Object {

    @Test
    void getStateBox() {
        Box[][] boxes = new Box[5][5];

        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes.length; j++)
                boxes[i][j] = new Box(true, 0);
        }

        BoardGame boardGame = new BoardGame(boxes);
        int[] pos = new int[]{2,3};

        boardGame.ChangeState(pos);
        boolean actual = boardGame.GetStateBox(pos);

        assertTrue(actual);

        boardGame.ChangeState(pos, "red");
        actual = boardGame.GetStateBox(pos);

        assertFalse(actual);

    }

    @Test
    void getLevelBox() {
        Box[][] boxes = new Box[5][5];

        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes.length; j++)
                boxes[i][j] = new Box(true, 0);
        }

        BoardGame boardGame = new BoardGame(boxes);
        int[] pos = new int[] {1,4};

        boardGame.DoBuild(pos);

        for(int i = boardGame.GetLevelBox(pos); i <= 4; i++)
        {
            assertEquals(i, boardGame.GetLevelBox(pos));
            boardGame.DoBuild(pos);
        }

        boardGame.BuildDome(pos);
        assertEquals(4, boardGame.GetLevelBox(pos));
    }

    @Test
    void isAPossibleMove() {
        Box[][] boxes = new Box[5][5];

        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes.length; j++)
                boxes[i][j] = new Box(true, 0);
        }

        BoardGame boardGame = new BoardGame(boxes);

        Player player = new Player("name");
        Worker worker = new Worker(player);
        int[] oldpos = new int[]{2,3};
        worker.SetPosition(oldpos);
        boardGame.DoBuild(oldpos);

        int[] newpos = new int[]{2,4};

        for (int i = boardGame.GetLevelBox(newpos); i<= 4; i++) {

            if(i <= 2)
                assertTrue(boardGame.IsAPossibleMove(newpos, oldpos));
            else
                assertFalse(boardGame.IsAPossibleMove(newpos,oldpos));
            boardGame.DoBuild(newpos);
        }

        boardGame.ChangeState(newpos, "red");
        assertFalse(boardGame.IsAPossibleMove(newpos, oldpos));
    }

    /*@Test
    void isAPossibleBuild() {
        Box[][] boxes = new Box[5][5];

        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes.length; j++)
                boxes[i][j] = new Box(true, 0);
        }

        BoardGame boardGame = new BoardGame(boxes);

        Player player = new Player("name");
        Worker worker = new Worker(player);
        int[] oldpos = new int[]{2,3};
        worker.SetPosition(oldpos);
        boardGame.DoBuild(oldpos);

        int[] newpos = new int[]{2,4};

        assertTrue(boardGame.IsAPossibleBuild(newpos, oldpos));
    }

     */

    @Test
    void getOccupant() {
        Player player = new Player("name");
        Worker worker = new Worker(player);

        Box[][] boxes = new Box[5][5];

        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes.length; j++)
                boxes[i][j] = new Box(true, 0);
        }

        BoardGame boardGame = new BoardGame(boxes);
        int [] pos = new int[]{1,2};

        boardGame.setOccupant(pos, worker);

        assertEquals(worker, boardGame.GetOccupant(pos));

    }

    @Test
    void isAdjacentBox() {
        Box[][] boxes = new Box[5][5];

        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes.length; j++)
                boxes[i][j] = new Box(true, 0);
        }

        BoardGame boardGame = new BoardGame(boxes);
        int[] worker_pos = new int[]{1, 2};
        int[] pos1 = new int[]{1, 1};
        int[] pos2 = new int[]{4, 0};

        assertTrue(boardGame.IsAdjacentBox(worker_pos, pos1));
        assertFalse(boardGame.IsAdjacentBox(worker_pos, pos2));
        assertFalse(boardGame.IsAdjacentBox(worker_pos, worker_pos));
    }
}