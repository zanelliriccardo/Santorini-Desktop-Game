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
    void testGetStateBox() {
    }

    @Test
    void getLevelBox() {
    }

    @Test
    void testGetLevelBox() {
    }

    @Test
    void isAPossibleMove() {
    }

    @Test
    void changeState() {
    }

    @Test
    void testChangeState() {
    }

    @Test
    void isAPossibleBuild() {
    }

    @Test
    void doBuild() {
    }

    @Test
    void isABlockedWorker() {
    }

    @Test
    void getOccupant() {
    }

    @Test
    void setOccupant() {
    }

    @Test
    void buildDome() {
    }

    @Test
    void getBoard() {
    }

    @Test
    void isAdjacentBox() {
    }

    @Test
    void adjacentBox() {
    }

    @Test
    void setActivePlayer() {
    }
}