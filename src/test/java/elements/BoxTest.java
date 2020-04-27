package elements;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoxTest extends Object {

    @Test
    void getState() {
        Box b1 = new Box(true, 2);
        Box b2 = new Box(false, 1);

        b2.ChangeState();

        assertEquals(true, b1.GetState());
        assertEquals(true, b2.GetState());

        b1.ChangeState("color");

        assertEquals(false, b1.GetState());
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
    void getColor() {
        Box b = new Box(true, 1);

        assertEquals(null, b.GetColor());

        b.ChangeState("red");

        assertEquals("red", b.GetColor());
    }

    @Test
    void getOccupant() {
        Box b = new Box(true, 1);
        Player p = new Player("name");
        Worker w = new Worker(p);

        assertEquals(null, b.GetOccupant());

        b.SetOccupant(w);

        assertEquals(w, b.GetOccupant());
    }
}