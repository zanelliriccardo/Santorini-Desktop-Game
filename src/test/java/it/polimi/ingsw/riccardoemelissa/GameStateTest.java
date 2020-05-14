package it.polimi.ingsw.riccardoemelissa;

import it.polimi.ingsw.riccardoemelissa.elements.BoardGame;
import it.polimi.ingsw.riccardoemelissa.elements.Player;
import it.polimi.ingsw.riccardoemelissa.elements.Worker;
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

        boardGame.setActivePlayer(GameState.getPlayer("nickname1"));
        assertEquals("nickname1", boardGame.getActivePlayer().GetNickname());
        assertEquals("nickname1", GameState.getActivePlayer().GetNickname());

        worker1.setProprietary(GameState.getPlayer("nickname1"));
        worker2.setProprietary(GameState.getPlayer("nickname1"));
        assertEquals("nickname1", worker1.GetProprietary().GetNickname());
        assertEquals("nickname1", worker2.GetProprietary().GetNickname());

        GameState.SetNewWorker(worker1);
        GameState.SetNewWorker(worker2);

        boardGame.setOccupant(new int[]{0,0}, worker1);
        boardGame.setOccupant(new int[]{0,1}, worker2);
        assertEquals(worker1, boardGame.GetOccupant(new int[]{0,0}));
        assertEquals(worker2, boardGame.GetOccupant(new int[]{0,1}));

        //ArrayList<Worker> workers = boardGame.getWorkers(GameState.getPlayer("nickname1"));

        //assertEquals(worker1, workers.get(0));
        //assertEquals(worker2, workers.get(1));

        //assertEquals(worker1, boardGame.getWorkers(GameState.getPlayer("nickname1")).get(0));
        //assertEquals(worker2, boardGame.getWorkers(GameState.getPlayer("nickname1")).get(1));
    }

    @Test
    void checkMove() {
    }

    @Test
    void getGameOver() {
    }

    @Test //codice non presente
    void undoTurn() {
    }

    @Test //codice non presente
    void removePlayer() {
    }

    @Test
    void updateBoard() {
    }

    @Test
    void isPossibleMove() {
    }

    @Test
    void isPossibleBuild() {
    }

    @Test
    void checkMoves() {
    }

    @Test
    void checkBuilds() {
    }

    @Test
    void possibleMoves() {
    }

    @Test
    void getWorkers() {
    }

    @Test
    void getNumPlayers() {
    }

    @Test
    void setActiveWorker() {
    }

    @Test
    void getActiveWorker() {
    }
}