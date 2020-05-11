package elements;


import it.polimi.ingsw.riccardoemelissa.elements.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GodTest extends Object {

    @Test
    void move() {
        /*Pan pan = new Pan();
        pan.SetPosition();

         */
    }

    @Test
    void setPosition() {
        Box[][] boxes = new Box[5][5];

        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes.length; j++)
                boxes[i][j] = new Box(true, 0);
        }

        BoardGame boardGame = new BoardGame();

        String player_name = "name";
        Player player = new Player(player_name);
        int[] oldpos = new int[]{2,2};
        int[] newpos = new int[]{2,1};
        Worker active_worker = new Worker();
        boardGame.GetBoard()[oldpos[0]][oldpos[1]].SetOccupant(active_worker);

        active_worker.SetPosition(newpos);
        boardGame.GetBoard()[newpos[0]][newpos[1]].ChangeState(active_worker);
        boardGame.GetBoard()[oldpos[0]][oldpos[1]].removeOccupant();

        assertArrayEquals(new int[]{2,2}, oldpos);
        assertArrayEquals(new int[]{2,1}, newpos);

        assertFalse(boardGame.GetBoard()[newpos[0]][newpos[1]].GetState());
        assertTrue(boardGame.GetBoard()[oldpos[0]][oldpos[1]].GetState());
        assertEquals(null, boardGame.GetBoard()[oldpos[0]][oldpos[1]].GetOccupant());
        assertEquals(active_worker, boardGame.GetBoard()[newpos[0]][newpos[1]].GetOccupant());
        assertArrayEquals(new int[]{2,1}, active_worker.GetPosition());
    }
}