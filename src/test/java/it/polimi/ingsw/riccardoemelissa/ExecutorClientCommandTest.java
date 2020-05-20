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
        assertEquals(2,GameState.getPlayers().size());

        ecc.update(new Command(CommandType.NICKNAME,"gioc1",null));
        assertEquals(GameState.getPlayers().get(0),"gioc1#0");

        Worker w=new Worker();
        w.setProprietary(new Player("gioc1"));

        ecc.update(new Command(CommandType.NEWWORKER,new Worker(),new int[]{2,2}));
        assertEquals(GameState.getBoard().getOccupant(2,2),w);

        ecc.update(new Command(CommandType.MOVE,w,new int[]{3,2}));
        assertEquals(GameState.getBoard().getOccupant(3,2),w);

        ecc.update(new Command(CommandType.BUILD,w,new int[]{4,2}));
        assertEquals(GameState.getBoard().getLevelBox(3,2),1);

        ecc.update(new Command(CommandType.NICKNAME,"gioc2",null));
        ecc.update(new Command(CommandType.CHANGE_TURN,null,null));
        assertEquals(GameState.getBoard().getActivePlayer(), GameState.getPlayers().get(1));
    }

    @Test
    void lose() {
        GameState.setNumPlayer(3);
        GameState.newPlayer("nickname1");
        GameState.newPlayer("nickname2");
        GameState.newPlayer("nickname3");

        ExecutorClientCommand ecc=new ExecutorClientCommand();
        ecc.lose();

        assertEquals(GameState.getPlayers().size(),2);
    }

    @Test
    void checkBuilds() {
        GameState.setNumPlayer(3);
        GameState.newPlayer("nickname1");
        GameState.newPlayer("nickname2");
        GameState.newPlayer("nickname3");

        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        worker1.setProprietary(GameState.getPlayer("nickname1#0"));
        worker2.setProprietary(GameState.getPlayer("nickname1#0"));
        int[] pos1= new int[]{0,0};
        int[] pos2= new int[]{1,1};
        GameState.getBoard().setOccupant(pos1,worker1);
        GameState.getBoard().setOccupant(pos2,worker2);

        ExecutorClientCommand ecc=new ExecutorClientCommand();
        ArrayList<int[]> possiblebuild=new ArrayList<>();
        possiblebuild.add(new int[]{1,0});
        possiblebuild.add(new int[]{0,1});

        assertEquals(possiblebuild,ecc.checkBuilds(GameState.getBoard(),worker1));
    }

    @Test
    void checkMoves() {
        GameState.setNumPlayer(3);
        GameState.newPlayer("nickname1");
        GameState.newPlayer("nickname2");
        GameState.newPlayer("nickname3");

        BoardGame boardGame = GameState.getBoard();
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        worker1.setProprietary(GameState.getPlayer("nickname1#0"));
        worker2.setProprietary(GameState.getPlayer("nickname1#0"));
        int[] pos1= new int[]{0,0};
        int[] pos2= new int[]{1,1};
        GameState.getBoard().setOccupant(pos1,worker1);
        GameState.getBoard().setOccupant(pos2,worker2);

        ExecutorClientCommand ecc=new ExecutorClientCommand();
        ArrayList<int[]> possiblemoves=new ArrayList<>();
        possiblemoves.add(new int[]{1,0});
        possiblemoves.add(new int[]{0,1});

        assertEquals(possiblemoves,ecc.checkMoves(GameState.getBoard(),worker1));
    }

    @Test
    void getWorkers() {
        BoardGame boardGame = GameState.getBoard();
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        int[] pos1= new int[]{1,1};
        int[] pos2= new int[]{3,1};

        worker1.setPosition(pos1);
        boardGame.setOccupant(pos1 , worker1);
        worker2.setPosition(pos2);
        boardGame.setOccupant(pos2 , worker2);

        ArrayList<Worker> workers=new ArrayList<Worker>();
        workers.add(worker1);
        workers.add(worker2);

        ArrayList<Worker> workersToControl=new ArrayList<Worker>();
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if(!GameState.getBoard().getStateBox(i,j))
                    if(GameState.getBoard().getOccupantProprietary(i,j).getNickname().compareTo(GameState.getBoard().getActivePlayer().getNickname())==0)
                        workersToControl.add(GameState.getBoard().getOccupant(i,j));
            }
        }

        assertEquals(workers,workersToControl);
    }
}