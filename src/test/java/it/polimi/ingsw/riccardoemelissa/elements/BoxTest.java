package it.polimi.ingsw.riccardoemelissa.elements;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoxTest extends Object {

    @Test
    void getState() {
        Box box = new Box(); //state = true, level = 0
        Worker worker = new Worker();

        box.changeState(worker); //state=false
        assertFalse(box.getState());

        box.changeState();//state = true
        assertTrue(box.getState());

        box.setOccupant(worker);//state=false
        assertFalse(box.getState());

        box.removeOccupant(); //state = true
        assertTrue(box.getState());
    }

    @Test
    void getLevel()
    {
        Box box = new Box(); //state = true, level = 0

        box.build(); //level=1
        assertEquals(1, box.getLevel());

        box.setDome(); //level=4
        assertEquals(4, box.getLevel());
    }


    @Test
    void getOccupant() {
        Box box = new Box(); //state = true, level = 0
        Worker worker = new Worker();

        assertNull(box.getOccupant());

        box.setOccupant(worker); //occupant = worker
        assertEquals(worker, box.getOccupant());

        box.removeOccupant();
        assertNull(box.getOccupant());
    }
}