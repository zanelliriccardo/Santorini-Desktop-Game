package it.polimi.ingsw.riccardoemelissa;

import it.polimi.ingsw.riccardoemelissa.elements.BoardGame;
import it.polimi.ingsw.riccardoemelissa.elements.GodCardType;
import it.polimi.ingsw.riccardoemelissa.elements.Player;
import it.polimi.ingsw.riccardoemelissa.elements.Worker;
import it.polimi.ingsw.riccardoemelissa.elements.card.Apollo;
import org.junit.jupiter.api.Test;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ExecutorClientCommandTest {
    @Test
    void update()
    {
        GameState.reset();
        ExecutorClientCommand ecc=new ExecutorClientCommand();

        ecc.update(new Command(CommandType.MODE,2,null));
        assertEquals(2,GameState.getPlayers().size());

        ecc.update(new Command(CommandType.NICKNAME,"gioc1",null));
        assertEquals(GameState.getPlayers().get(0).getNickname(),"gioc1#0");

        Worker w=new Worker();
        w.setPosition(2,2);
        w.setProprietary(new Player("gioc1#0"));
        w.getProprietary().setGodCard(new Apollo());

        ecc.update(new Command(CommandType.NEWWORKER,new Worker(),new int[]{2,2}));
        assertFalse(GameState.getBoard().getStateBox(2, 2));

        ecc.update(new Command(CommandType.MOVE,w,new int[]{3,2}));
        assertFalse(GameState.getBoard().getStateBox(3, 2));

        w.getProprietary().getGodCard().setCardType(GodCardType.BUILD);
        ecc.update(new Command(CommandType.BUILD,w,new int[]{4,2}));
        assertEquals(GameState.getBoard().getLevelBox(4,2),1);

        ecc.update(new Command(CommandType.NICKNAME,"gioc2",null));
        ecc.update(new Command(CommandType.CHANGE_TURN,null,null));
        assertEquals(GameState.getBoard().getActivePlayer(), GameState.getPlayers().get(1));
    }

    @Test
    void lose() {
        GameState.reset();
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
        GameState.reset();
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
        worker1.setPosition(pos1);
        worker2.setPosition(pos2);
        GameState.getBoard().setOccupant(pos1,worker1);
        GameState.getBoard().setOccupant(pos2,worker2);

        ExecutorClientCommand ecc=new ExecutorClientCommand();
        ArrayList<int[]> possiblebuild=new ArrayList<>();
        possiblebuild.add(new int[]{0,1});
        possiblebuild.add(new int[]{1,0});

        ArrayList<int[]> checkbuild=ecc.checkBuilds(GameState.getBoard(),worker1);

        assertEquals(possiblebuild.get(0)[0],checkbuild.get(0)[0]);
        assertEquals(possiblebuild.get(0)[1],checkbuild.get(0)[1]);
        assertEquals(possiblebuild.get(1)[0],checkbuild.get(1)[0]);
        assertEquals(possiblebuild.get(1)[1],checkbuild.get(1)[1]);
    }

    @Test
    void checkMoves() {
        GameState.reset();
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
        worker1.setPosition(pos1);
        worker2.setPosition(pos2);
        GameState.getBoard().setOccupant(pos1,worker1);
        GameState.getBoard().setOccupant(pos2,worker2);

        ExecutorClientCommand ecc=new ExecutorClientCommand();
        ArrayList<int[]> possiblemoves=new ArrayList<>();
        possiblemoves.add(new int[]{0,1});
        possiblemoves.add(new int[]{1,0});


        ArrayList<int[]> checkmoves=ecc.checkMoves(GameState.getBoard(),worker1);
        assertEquals(possiblemoves.get(0)[0],checkmoves.get(0)[0]);
        assertEquals(possiblemoves.get(0)[1],checkmoves.get(0)[1]);
        assertEquals(possiblemoves.get(1)[0],checkmoves.get(1)[0]);
        assertEquals(possiblemoves.get(1)[1],checkmoves.get(1)[1]);
    }
}