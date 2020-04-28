package elements;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerTest extends Object {

    @Test
    void getX() {
        String str = "name";
        Player player = new Player(str);
        Worker worker = new Worker(player, new int[]{1,2});

        assertEquals(1, worker.GetX());
    }

    @Test
    void getY() {
        String str = "name";
        Player player = new Player(str);
        Worker worker = new Worker(player, new int[]{1,2});

        assertEquals(2, worker.GetY());
    }

    @Test
    void getProprietary() {
        String str = "name";
        Player player = new Player(str);
        Worker worker = new Worker(player, new int[]{1,2});
        assertEquals(player, worker.GetProprietary());
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