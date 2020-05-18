package it.polimi.ingsw.riccardoemelissa;

import it.polimi.ingsw.riccardoemelissa.elements.BoardGame;
import it.polimi.ingsw.riccardoemelissa.elements.GodCardType;
import it.polimi.ingsw.riccardoemelissa.elements.Player;
import it.polimi.ingsw.riccardoemelissa.elements.Worker;
import it.polimi.ingsw.riccardoemelissa.elements.card.Apollo;
import it.polimi.ingsw.riccardoemelissa.elements.card.Artemis;
import it.polimi.ingsw.riccardoemelissa.elements.card.Pan;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {

    void setPlayers()
    {
        GameState.SetNumPlayer(3);
        GameState.NewPlayer("nickname1");
        GameState.NewPlayer("nickname2");
        GameState.NewPlayer("nickname3");
    }

    @Test
    void getPlayers() {
        setPlayers();

        for(int i=0; i< GameState.GetPlayers().size(); i++)
        {
            assertEquals(i, GameState.GetIndexPlayer(GameState.GetPlayers().get(i).GetNickname()));
        }

        assertEquals(3, GameState.GetPlayerNumber());
    }

    @Test
    void gameReady() {
        getPlayers();
        assertTrue(GameState.GameReady());
    }

    @Test
    void getWorkerToMove() {
    }

    @Test
    void setTurnOrder() {
    }

    @Test //godfactory è usato in setPlayers
    void godFactory() {
        int count = 0;
        setPlayers();

        for(Player player : GameState.GetPlayers()) {

            switch (player.GetGodCard().getClass().getName())
            {
                case "it.polimi.ingsw.riccardoemelissa.elements.card.Apollo":
                    count++;
                    assertEquals("Apollo.png", player.getGodImagePath());
                    assertFalse(player.GetGodCard().GetOpponentTurn());
                    break;

                case "it.polimi.ingsw.riccardoemelissa.elements.card.Artemis":
                    count++;
                    assertEquals("Artemis.png", player.getGodImagePath());
                    assertFalse(player.GetGodCard().GetOpponentTurn());
                    break;

                case "it.polimi.ingsw.riccardoemelissa.elements.card.Athena":
                    count++;
                    assertEquals("Athena.png", player.getGodImagePath());
                    assertTrue(player.GetGodCard().GetOpponentTurn());
                    break;

                case "it.polimi.ingsw.riccardoemelissa.elements.card.Atlas":
                    count++;
                    assertEquals("Atlas.png", player.getGodImagePath());
                    assertFalse(player.GetGodCard().GetOpponentTurn());
                    break;

                case "it.polimi.ingsw.riccardoemelissa.elements.card.Demeter":
                    count++;
                    assertEquals("Demeter.png", player.getGodImagePath());
                    assertFalse(player.GetGodCard().GetOpponentTurn());
                    break;

                case "it.polimi.ingsw.riccardoemelissa.elements.card.Hephaestus":
                    count++;
                    assertEquals("Hephaestus.png", player.getGodImagePath());
                    assertFalse(player.GetGodCard().GetOpponentTurn());
                    break;

                case "it.polimi.ingsw.riccardoemelissa.elements.card.Minotaur":
                    count++;
                    assertEquals("Minotaur.png", player.getGodImagePath());
                    assertFalse(player.GetGodCard().GetOpponentTurn());
                    break;

                case "it.polimi.ingsw.riccardoemelissa.elements.card.Pan":
                    count++;
                    assertEquals("Pan.png", player.getGodImagePath());
                    assertFalse(player.GetGodCard().GetOpponentTurn());
                    break;

                case "it.polimi.ingsw.riccardoemelissa.elements.card.Prometheus":
                    count++;
                    assertEquals("Prometheus.png", player.getGodImagePath());
                    assertFalse(player.GetGodCard().GetOpponentTurn());
                    break;
            }
        }
        assertEquals(3, count);
    }

    @Test
    void endGame() {
    }

    @Test
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
        BoardGame boardGame = GameState.GetBoard();
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
        worker1.SetPosition(pos1);
        worker1.GetProprietary().SetGodCard(new Pan());
        worker2.setProprietary(player2);
        worker2.GetProprietary().SetGodCard(new Artemis());
        worker2.SetPosition(pos2);
        boardGame.setOccupant(pos1, worker1);
        boardGame.setOccupant(pos2, worker2);

        assertFalse(GameState.IsPossibleMove(worker1, pos2));

        boardGame.DoBuild(pos3);
        boardGame.DoBuild(pos3);

        assertFalse(GameState.IsPossibleMove(worker1, pos3));
        assertFalse(GameState.IsPossibleMove(worker1, pos4));

        boardGame.DoBuild(pos);
        assertTrue(GameState.IsPossibleMove(worker1, pos));
    }

    @Test // Pan and Apollo
    void isPossibleBuild() {
        setPlayers();
        BoardGame boardGame = GameState.GetBoard();
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        Player player1 = GameState.getPlayer("nickname1#0");
        Player player2= GameState.getPlayer("nickname2#1");
        int[] pos1 = new int[]{1,1};
        int[] pos2 = new int[]{1,2};
        int[] pos3 = new int[]{0,1};
        int[] pos4 = new int[]{3,1};
        worker1.setProprietary(player1);
        worker1.SetPosition(pos1);
        worker1.GetProprietary().SetGodCard(new Pan());
        worker2.setProprietary(player2);
        worker2.GetProprietary().SetGodCard(new Apollo());
        worker2.SetPosition(pos2);
        boardGame.setOccupant(pos1, worker1);
        boardGame.setOccupant(pos2, worker2);

        player1.GetGodCard().setCardType(GodCardType.BUILD);

        assertFalse(GameState.IsPossibleBuild(worker1, pos2));

        boardGame.BuildDome(pos3);
        assertFalse(GameState.IsPossibleBuild(worker1, pos3));
        assertFalse(GameState.IsPossibleBuild(worker1, pos4));

        int[] pos = new int[]{2,1};
        assertTrue(GameState.IsPossibleBuild(worker1, pos));
    }

    @Test //usato in isPossibleMove
    void checkMoves() {
    }

    @Test //usato in isPossibleBuild
    void checkBuilds() {
    }

    /*@Test //non va
    void getWorkers() {
    }

     */

    @Test
    void getActiveWorker() {
        BoardGame boardGame = GameState.GetBoard();
        Worker worker = new Worker();
        int[] pos= new int[]{1,1};

        worker.SetPosition(pos);
        boardGame.setOccupant(pos , worker);

        GameState.setActiveWorker(pos);
        assertEquals(worker, GameState.getActiveWorker());
    }
}