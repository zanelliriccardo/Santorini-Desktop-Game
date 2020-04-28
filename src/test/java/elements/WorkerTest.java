package elements;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerTest extends Object {

    @Test
    void getX() {
        String str = "name";
        Player p = new Player(str);
        Worker w = new Worker(p);
        int[] pos = new int[2];

        pos[0] = 1;
        w.SetPosition(pos);

        assertEquals(1, w.GetX());
    }

    @Test
    void getY() {
        String str = "name";
        Player p = new Player(str);
        Worker w = new Worker(p);
        int[] pos = new int[2];

        pos[1] = 2;
        w.SetPosition(pos[0], pos[1]);

        assertEquals(2, w.GetY());
    }

    @Test
    void getProprietary() {
        String str = "name";
        Player p = new Player(str);
        Worker w = new Worker(p);

        assertEquals(p, w.GetProprietary());
    }

    @Test
    void getPosition() {
        String str = "name";
        Player p = new Player(str);
        int[] pos = new int[2];
        Worker w = new Worker(p, pos);


        pos[0]= 1;
        pos[1]= 2;

        w.SetPosition(pos);

        int [] actual = w.GetPosition();

        assertEquals(pos[0], actual[0]);
        assertEquals(pos[1], actual[1]);
    }

    @Test
    void setColor() {
    }
}