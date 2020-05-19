package it.polimi.ingsw.riccardoemelissa.elements;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerTest extends Object {

    @Test
    void getPosition() {
        Worker worker = new Worker();
        int[] pos = new int[]{2,4};

        worker.setPosition(pos[0], pos[1]);
        assertEquals(2, worker.getX());
        assertEquals(4, worker.getY());
        assertArrayEquals(pos, worker.getPosition());
    }

    @Test
    void getProprietary() {
        String str = "nickname";
        Player player = new Player(str);
        Worker worker = new Worker();

        worker.setProprietary(player);
        assertEquals(player, worker.getProprietary());
    }
}