package elements;

import it.polimi.ingsw.riccardoemelissa.elements.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoxTest extends Object {

    @Test
    void getState() {
        Box box = new Box(); //state = true, level = 0
        Worker worker = new Worker();

        box.ChangeState(worker); //state=false
        assertFalse(box.GetState());

        box.ChangeState();//state = true
        assertTrue(box.GetState());

        box.SetOccupant(worker);//state=false
        assertFalse(box.GetState());

        box.removeOccupant(); //state = true
        assertTrue(box.GetState());
    }

    @Test
    void getLevel()
    {
        Box box = new Box(); //state = true, level = 0

        box.Build(); //level=1
        assertEquals(1, box.GetLevel());

        box.SetDome(); //level=4
        assertEquals(4, box.GetLevel());
    }


    @Test
    void getOccupant() {
        Box box = new Box(); //state = true, level = 0
        Worker worker = new Worker();

        assertNull(box.GetOccupant());

        box.SetOccupant(worker); //occupant = worker
        assertEquals(worker, box.GetOccupant());

        box.removeOccupant();
        assertNull(box.GetOccupant());
    }
}