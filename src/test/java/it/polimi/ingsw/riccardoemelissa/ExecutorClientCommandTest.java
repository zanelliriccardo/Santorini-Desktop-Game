package it.polimi.ingsw.riccardoemelissa;

import it.polimi.ingsw.riccardoemelissa.elements.BoardGame;
import it.polimi.ingsw.riccardoemelissa.elements.GodCardType;
import it.polimi.ingsw.riccardoemelissa.elements.Player;
import it.polimi.ingsw.riccardoemelissa.elements.Worker;
import it.polimi.ingsw.riccardoemelissa.elements.card.Apollo;
import it.polimi.ingsw.riccardoemelissa.elements.card.Pan;
import org.junit.jupiter.api.Test;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ExecutorClientCommandTest {
    private Player player1;
    private Player player2;
    private Worker worker11;
    private Worker worker12;
    private Worker worker21;
    private Worker worker22;

    @Test
    void update()
    {
        GameState.reset();
        ExecutorClientCommand ecc=new ExecutorClientCommand();

        ecc.update(new Command(CommandType.MODE,2,null));
        assertEquals(2,GameState.getPlayers().size());

        ecc.update(new Command(CommandType.NICKNAME,"gioc1",null));
        assertEquals(GameState.getPlayers().get(0).getNickname(),"gioc1#0");

        Player player=new Player("gioc1#0");
        Worker w=new Worker();
        w.setPosition(2,2);
        w.setProprietary(player);
        w.getProprietary().setGodCard(new Apollo());
        GameState.getBoard().setActivePlayer(player);
        ecc.update(new Command(CommandType.NEWWORKER,new Worker(),new int[]{2,2}));
        assertFalse(GameState.getBoard().getStateBox(2, 2));

        ecc.update(new Command(CommandType.MOVE,w,new int[]{3,2}));
        assertFalse(GameState.getBoard().getStateBox(3, 2));

        w.getProprietary().getGodCard().setCardType(GodCardType.BUILD);
        w.setPosition(3,2);
        ecc.update(new Command(CommandType.BUILD,w,new int[]{4,2}));
        assertEquals(GameState.getBoard().getLevelBox(4,2),1);

        GameState.newPlayer("gioc1");
        ecc.update(new Command(CommandType.CHANGE_TURN,null,null));
        assertEquals(GameState.getBoard().getActivePlayer(), GameState.getPlayers().get(0));
    }

    @Test
    void lose() {
        startGame();
        BoardGame boardGame = GameState.getBoard();
        player1.setGodCard(new Apollo());
        player2.setGodCard(new Pan());
        worker11.setProprietary(GameState.getPlayers().get(1));
        worker12.setProprietary(GameState.getPlayers().get(1));

        worker21.setProprietary(GameState.getPlayers().get(0));
        worker22.setProprietary(GameState.getPlayers().get(0));

        int[] pos11 = new int[]{0, 0};
        boardGame.doBuild(new int[]{0, 1});
        boardGame.doBuild(new int[]{0, 1});
        boardGame.doBuild(new int[]{1, 1});
        boardGame.doBuild(new int[]{1, 1});
        boardGame.doBuild(new int[]{1, 0});
        boardGame.doBuild(new int[]{1, 0});

        worker11.setPosition(pos11);

        int[] pos12 = new int[]{4, 4};
        boardGame.doBuild(new int[]{4, 3});
        boardGame.doBuild(new int[]{4, 3});
        boardGame.doBuild(new int[]{3, 4});
        boardGame.doBuild(new int[]{3, 4});
        boardGame.doBuild(new int[]{3, 3});
        boardGame.doBuild(new int[]{3, 3});

        worker12.setPosition(pos12);

        int[] pos21 = new int[]{2, 1};
        worker21.setPosition(pos21);
        int[] pos22 = new int[]{2, 2};
        worker22.setPosition(pos22);

        boardGame.setOccupant(pos11, worker11);
        boardGame.setOccupant(pos12, worker12);
        boardGame.setOccupant(pos21, worker21);
        boardGame.setOccupant(pos22, worker22);

        boardGame.setActivePlayer(player2);

        new ExecutorClientCommand().update(new Command(CommandType.CHANGE_TURN,null,null));
        assertEquals("LOSE",player2.getGodCard().getCardType().toString());
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

        GameState.getBoard().setActivePlayer(GameState.getPlayer("nickname1#0"));

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

        GameState.getBoard().setActivePlayer(GameState.getPlayer("nickname1#0"));

        ArrayList<int[]> checkmoves=ecc.checkMoves(GameState.getBoard(),worker1);
        assertEquals(possiblemoves.get(0)[0],checkmoves.get(0)[0]);
        assertEquals(possiblemoves.get(0)[1],checkmoves.get(0)[1]);
        assertEquals(possiblemoves.get(1)[0],checkmoves.get(1)[0]);
        assertEquals(possiblemoves.get(1)[1],checkmoves.get(1)[1]);
    }


    void startGame (){
        GameState.reset();
        GameState.setNumPlayer(2);
        GameState.newPlayer("nickname1");
        player1 = GameState.getPlayer("nickname1#0");
        GameState.newPlayer("nickname2");
        player2 = GameState.getPlayer("nickname2#1");
        worker11 = new Worker();
        worker11.setProprietary(player1);
        worker12 = new Worker();
        worker12.setProprietary(player1);
        worker21 = new Worker();
        worker21.setProprietary(player2);
        worker22 = new Worker();
        worker22.setProprietary(player2);
    }
    @Test
    void win() {
        startGame();
        BoardGame boardGame = GameState.getBoard();
        player1.setGodCard(new Apollo());
        player2.setGodCard(new Pan());
        int[] pos11 = new int[]{0, 0};
        boardGame.doBuild(pos11);
        boardGame.doBuild(pos11);
        worker11.setPosition(pos11);
        int[] pos12 = new int[]{3, 3};
        worker12.setPosition(pos12);
        int[] pos21 = new int[]{2, 1};
        worker21.setPosition(pos21);
        int[] pos22 = new int[]{4, 4};
        worker22.setPosition(pos22);
        boardGame.setOccupant(pos11, worker11);
        boardGame.setOccupant(pos12, worker12);
        boardGame.setOccupant(pos21, worker21);
        boardGame.setOccupant(pos22, worker22);
        boardGame.setActivePlayer(player1);
        boardGame.doBuild(new int[]{0,1});
        boardGame.doBuild(new int[]{0,1});
        boardGame.doBuild(new int[]{0,1});
        assertEquals(2, boardGame.getLevelBox(pos11));
        assertEquals(3, boardGame.getLevelBox(new int[]{0,1}));
        assertTrue(GameState.isPossibleMove(worker11, new int[]{0,1}));
        new ExecutorClientCommand().update(new Command(CommandType.MOVE, worker11, new int[]{0,1}));
        assertArrayEquals(new int[]{0,1}, worker11.getPosition());
        assertEquals("WIN", player1.getGodCard().getCardType().toString());
    }



}