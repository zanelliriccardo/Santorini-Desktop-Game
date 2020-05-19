package it.polimi.ingsw.riccardoemelissa;

import it.polimi.ingsw.riccardoemelissa.elements.BoardGame;
import it.polimi.ingsw.riccardoemelissa.elements.Player;
import it.polimi.ingsw.riccardoemelissa.elements.Worker;
import org.junit.jupiter.api.Test;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ExecutorClientCommandTest {

    @org.junit.jupiter.api.Test
    void update() {
    }

    @Test
    void testUpdate()
    {
        ExecutorClientCommand ecc=new ExecutorClientCommand();

        ecc.update(new Command(CommandType.MODE,2,null));
        assertEquals(2,GameState.GetPlayers().size());

        ecc.update(new Command(CommandType.NICKNAME,"gioc1",null));
        assertEquals(GameState.GetPlayers().get(0),"gioc1#0");

        Worker w=new Worker();
        w.setProprietary(new Player("gioc1"));

        ecc.update(new Command(CommandType.NEWWORKER,new Worker(),new int[]{2,2}));
        assertEquals(GameState.GetBoard().GetOccupant(2,2),w);

        ecc.update(new Command(CommandType.MOVE,w,new int[]{3,2}));
        assertEquals(GameState.GetBoard().GetOccupant(3,2),w);

        ecc.update(new Command(CommandType.BUILD,w,new int[]{4,2}));
        assertEquals(GameState.GetBoard().GetLevelBox(3,2),1);

        ecc.update(new Command(CommandType.NICKNAME,"gioc2",null));
        ecc.update(new Command(CommandType.CHANGE_TURN,null,null));
        assertEquals(GameState.GetBoard().getActivePlayer(), GameState.GetPlayers().get(1));
    }

    @Test
    void lose() {
        GameState.SetNumPlayer(3);
        GameState.NewPlayer("nickname1");
        GameState.NewPlayer("nickname2");
        GameState.NewPlayer("nickname3");

        ExecutorClientCommand ecc=new ExecutorClientCommand();
        ecc.lose();

        assertEquals(GameState.GetPlayers().size(),2);
    }

    @Test
    void checkBuilds() {
        GameState.SetNumPlayer(3);
        GameState.NewPlayer("nickname1");
        GameState.NewPlayer("nickname2");
        GameState.NewPlayer("nickname3");

        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        worker1.setProprietary(GameState.getPlayer("nickname1#0"));
        worker2.setProprietary(GameState.getPlayer("nickname1#0"));
        int[] pos1= new int[]{0,0};
        int[] pos2= new int[]{1,1};
        GameState.GetBoard().setOccupant(pos1,worker1);
        GameState.GetBoard().setOccupant(pos2,worker2);

        ExecutorClientCommand ecc=new ExecutorClientCommand();
        ArrayList<int[]> possiblebuild=new ArrayList<>();
        possiblebuild.add(new int[]{1,0});
        possiblebuild.add(new int[]{0,1});

        assertEquals(possiblebuild,ecc.checkBuilds(GameState.GetBoard(),worker1));
    }

    @Test
    void checkMoves() {
        GameState.SetNumPlayer(3);
        GameState.NewPlayer("nickname1");
        GameState.NewPlayer("nickname2");
        GameState.NewPlayer("nickname3");

        BoardGame boardGame = GameState.GetBoard();
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        worker1.setProprietary(GameState.getPlayer("nickname1#0"));
        worker2.setProprietary(GameState.getPlayer("nickname1#0"));
        int[] pos1= new int[]{0,0};
        int[] pos2= new int[]{1,1};
        GameState.GetBoard().setOccupant(pos1,worker1);
        GameState.GetBoard().setOccupant(pos2,worker2);

        ExecutorClientCommand ecc=new ExecutorClientCommand();
        ArrayList<int[]> possiblemoves=new ArrayList<>();
        possiblemoves.add(new int[]{1,0});
        possiblemoves.add(new int[]{0,1});

        assertEquals(possiblemoves,ecc.checkMoves(GameState.GetBoard(),worker1));
    }

    @Test
    void getWorkers() {
        BoardGame boardGame = GameState.GetBoard();
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        int[] pos1= new int[]{1,1};
        int[] pos2= new int[]{3,1};

        worker1.SetPosition(pos1);
        boardGame.setOccupant(pos1 , worker1);
        worker2.SetPosition(pos2);
        boardGame.setOccupant(pos2 , worker2);

        ArrayList<Worker> workers=new ArrayList<Worker>();
        workers.add(worker1);
        workers.add(worker2);

        assertEquals(workers,(new ExecutorClientCommand()).getWorkers());
    }
}