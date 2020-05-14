package it.polimi.ingsw.riccardoemelissa;

import it.polimi.ingsw.riccardoemelissa.elements.Player;
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
    void nextTurn() {
    }

    @Test
    void getActivePlayer() {
    }

    @Test
    void endGame() {
    }

    @Test
    void setNewWorker() {
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