package elements;

import it.polimi.ingsw.riccardoemelissa.elements.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoxTest extends Object {

    @Test
    void getState() {
        Box b1 = new Box(true, 2);
        Box b2 = new Box(false, 1);
        Player player = new Player("name");
        Worker worker = new Worker(player, new int[]{1,1});

        b2.ChangeState();

        assertTrue(b2.GetState());

        b1.ChangeState(worker);

        assertFalse(b1.GetState());
    }

    @Test
    void getLevel()
    {
        Box b = new Box(true,0);

        b.Build();

        assertEquals(1, b.GetLevel());

        b.SetDome();

        assertEquals(4, b.GetLevel());
    }


    @Test
    void getOccupant() {
        Box box = new Box(true, 1);
        Player player = new Player("name");
        Worker worker = new Worker(player, new int[]{1,1});

        assertEquals(null, box.GetOccupant());

        box.SetOccupant(worker);

        assertEquals(worker, box.GetOccupant());
    }
}