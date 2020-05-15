package it.polimi.ingsw.riccardoemelissa.elements;

import it.polimi.ingsw.riccardoemelissa.elements.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerTest extends Object {

    @Test
    void getPosition() {
        Worker worker = new Worker();
        int[] pos = new int[]{2,4};

        worker.SetPosition(pos[0], pos[1]);
        assertEquals(2, worker.GetX());
        assertEquals(4, worker.GetY());
        assertArrayEquals(pos, worker.GetPosition());
    }

    @Test
    void getProprietary() {
        String str = "nickname";
        Player player = new Player(str);
        Worker worker = new Worker();

        worker.setProprietary(player);
        assertEquals(player, worker.GetProprietary());
    }
}