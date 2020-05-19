package it.polimi.ingsw.riccardoemelissa;

import it.polimi.ingsw.riccardoemelissa.elements.BoardGame;
import it.polimi.ingsw.riccardoemelissa.elements.GodCardType;
import it.polimi.ingsw.riccardoemelissa.elements.Player;
import it.polimi.ingsw.riccardoemelissa.elements.Worker;
import it.polimi.ingsw.riccardoemelissa.elements.card.Apollo;
import it.polimi.ingsw.riccardoemelissa.elements.card.Athena;
import it.polimi.ingsw.riccardoemelissa.elements.card.Pan;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {

    void setPlayers()
    {
        GameState.setNumPlayer(3);
        GameState.newPlayer("nickname1");
        GameState.newPlayer("nickname2");
        GameState.newPlayer("nickname3");
    }

    @Test
    void getPlayers() {
        setPlayers();

        for(int i = 0; i< GameState.getPlayers().size(); i++)
        {
            assertEquals(i, GameState.getIndexPlayer(GameState.getPlayers().get(i).getNickname()));
        }

        assertEquals(3, GameState.getPlayerNumber());
    }

    @Test
    void gameReady() {
        getPlayers();
        assertTrue(GameState.gameReady());
    }

   /* @Test
    void getWorkerToMove() {
    }



    @Test
    void setTurnOrder() {
    }

    */

    @Test //godfactory è usato in setPlayers
    void godFactory() {
        int count = 0;
        setPlayers();

        for(Player player : GameState.getPlayers()) {

            switch (player.getGodCard().getClass().getName())
            {
                case "it.polimi.ingsw.riccardoemelissa.elements.card.Apollo":
                    count++;
                    assertEquals("Apollo.png", player.getGodImagePath());
                    assertFalse(player.getGodCard().GetOpponentTurn());
                    break;

                case "it.polimi.ingsw.riccardoemelissa.elements.card.Artemis":
                    count++;
                    assertEquals("Artemis.png", player.getGodImagePath());
                    assertFalse(player.getGodCard().GetOpponentTurn());
                    break;

                case "it.polimi.ingsw.riccardoemelissa.elements.card.Athena":
                    count++;
                    assertEquals("Athena.png", player.getGodImagePath());
                    assertTrue(player.getGodCard().GetOpponentTurn());
                    break;

                case "it.polimi.ingsw.riccardoemelissa.elements.card.Atlas":
                    count++;
                    assertEquals("Atlas.png", player.getGodImagePath());
                    assertFalse(player.getGodCard().GetOpponentTurn());
                    break;

                case "it.polimi.ingsw.riccardoemelissa.elements.card.Demeter":
                    count++;
                    assertEquals("Demeter.png", player.getGodImagePath());
                    assertFalse(player.getGodCard().GetOpponentTurn());
                    break;

                case "it.polimi.ingsw.riccardoemelissa.elements.card.Hephaestus":
                    count++;
                    assertEquals("Hephaestus.png", player.getGodImagePath());
                    assertFalse(player.getGodCard().GetOpponentTurn());
                    break;

                case "it.polimi.ingsw.riccardoemelissa.elements.card.Minotaur":
                    count++;
                    assertEquals("Minotaur.png", player.getGodImagePath());
                    assertFalse(player.getGodCard().GetOpponentTurn());
                    break;

                case "it.polimi.ingsw.riccardoemelissa.elements.card.Pan":
                    count++;
                    assertEquals("Pan.png", player.getGodImagePath());
                    assertFalse(player.getGodCard().GetOpponentTurn());
                    break;

                case "it.polimi.ingsw.riccardoemelissa.elements.card.Prometheus":
                    count++;
                    assertEquals("Prometheus.png", player.getGodImagePath());
                    assertFalse(player.getGodCard().GetOpponentTurn());
                    break;
            }
        }
        assertEquals(3, count);
    }

   /* @Test
    void endGame() {
    }

    */

    /*@Test
    void setNewWorker() {
        setPlayers();

        BoardGame boardGame = GameState.GetBoard();
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        Player player1 = GameState.getPlayer("nickname1#0");

        boardGame.setActivePlayer(player1);
        assertEquals("nickname1#0", boardGame.getActivePlayer().GetNickname());

        worker1.setProprietary(player1);
        worker2.setProprietary(player1);
        assertEquals("nickname1#0", worker1.GetProprietary().GetNickname());
        assertEquals("nickname1#0", worker2.GetProprietary().GetNickname());

        GameState.SetNewWorker(worker1);
        GameState.SetNewWorker(worker2);

        boardGame.setOccupant(new int[]{0, 0}, worker1);
        boardGame.setOccupant(new int[]{0, 1}, worker2);
        assertEquals(worker1, boardGame.GetOccupant(new int[]{0, 0}));
        assertEquals(worker2, boardGame.GetOccupant(new int[]{0, 1}));
    }

     */

    @Test // ???
    void checkMove() {
    }

    @Test //codice non presente
    void undoTurn() {
    }

    @Test //codice non presente
    void removePlayer() {
    }

    //God : Pan and Artemis
    @Test
    void isPossibleMove() {
        setPlayers();
        BoardGame boardGame = GameState.getBoard();
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        Player player1 = GameState.getPlayer("nickname1#0");
        Player player2= GameState.getPlayer("nickname2#1");
        int[] pos1 = new int[]{1,1};
        int[] pos2 = new int[]{1,2};
        int[] pos3 = new int[]{0,1};
        int[] pos4 = new int[]{3,1};
        int[] pos = new int[]{2,1};
        worker1.setProprietary(player1);
        worker1.setPosition(pos1);
        player1.setGodCard(new Pan());
        worker2.setProprietary(player2);
        player2.setGodCard(new Athena());
        player2.getGodCard().setOpponentTrue("true");
        worker2.setPosition(pos2);
        boardGame.setOccupant(pos1, worker1);
        boardGame.setOccupant(pos2, worker2);

        assertFalse(GameState.isPossibleMove(worker1, pos2));

        boardGame.doBuild(pos3);
        boardGame.doBuild(pos3);

        assertFalse(GameState.isPossibleMove(worker1, pos3));

        boardGame.doBuild(pos);
        assertTrue(GameState.isPossibleMove(worker1, pos));
        player1.getGodCard().move(boardGame, worker1, pos);

        boardGame.doBuild(new int[]{1,3});
        player2.getGodCard().move(boardGame, worker2, new int[]{1,3}); //in action = true

        boardGame.doBuild(new int[]{2,2});
        boardGame.doBuild(new int[]{2,2});
        assertFalse(GameState.isPossibleMove(worker1, new int[]{2,2}));
    }

    @Test // Pan and Apollo
    void isPossibleBuild() {
        setPlayers();
        BoardGame boardGame = GameState.getBoard();
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        Player player1 = GameState.getPlayer("nickname1#0");
        Player player2= GameState.getPlayer("nickname2#1");
        int[] pos1 = new int[]{1,1};
        int[] pos2 = new int[]{1,2};
        int[] pos3 = new int[]{0,1};
        int[] pos4 = new int[]{3,1};
        worker1.setProprietary(player1);
        worker1.setPosition(pos1);
        worker1.getProprietary().setGodCard(new Pan());
        worker2.setProprietary(player2);
        worker2.getProprietary().setGodCard(new Apollo());
        worker2.setPosition(pos2);
        boardGame.setOccupant(pos1, worker1);
        boardGame.setOccupant(pos2, worker2);

        player1.getGodCard().setCardType(GodCardType.BUILD);

        assertFalse(GameState.isPossibleBuild(worker1, pos2));

        boardGame.buildDome(pos3);
        assertFalse(GameState.isPossibleBuild(worker1, pos3));
        assertFalse(GameState.isPossibleBuild(worker1, pos4));

        int[] pos = new int[]{2,1};
        assertTrue(GameState.isPossibleBuild(worker1, pos));
    }

    /*@Test //usato in isPossibleMove
    void checkMoves() {
    }

    @Test //usato in isPossibleBuild
    void checkBuilds() {
    }

     */

    /*@Test //non va
    void getWorkers() {
    }

     */

    @Test
    void getActiveWorker() {
        BoardGame boardGame = GameState.getBoard();
        Worker worker = new Worker();
        int[] pos= new int[]{1,1};

        worker.setPosition(pos);
        boardGame.setOccupant(pos , worker);

        GameState.setActiveWorker(pos);
        assertEquals(worker, GameState.getActiveWorker());
    }

    @Test
    void possibleMoves(){
        setPlayers();
        BoardGame boardGame = GameState.getBoard();
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        Player player1 = GameState.getPlayer("nickname1#0");
        Player player2= GameState.getPlayer("nickname2#1");
        int[] pos1 = new int[]{1,1};
        int[] pos2 = new int[]{1,2};

        worker1.setProprietary(player1);
        worker1.setPosition(pos1);
        player1.setGodCard(new Pan());
        worker2.setProprietary(player2);
        player2.setGodCard(new Athena());
        player2.getGodCard().setOpponentTrue("true");
        worker2.setPosition(pos2);
        boardGame.setOccupant(pos1, worker1);
        boardGame.setOccupant(pos2, worker2);

        ArrayList<int[]> moves = GameState.possibleMoves();
        for(int[] pos : moves)
            if(Arrays.equals(pos, new int[]{1,2}))
                assertTrue(GameState.isPossibleMove(worker1, new int[]{1,2}));
    }
}