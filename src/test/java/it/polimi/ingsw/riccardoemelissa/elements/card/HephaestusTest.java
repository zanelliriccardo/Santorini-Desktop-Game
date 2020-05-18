package it.polimi.ingsw.riccardoemelissa.elements.card;

import it.polimi.ingsw.riccardoemelissa.GameState;
import it.polimi.ingsw.riccardoemelissa.elements.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HephaestusTest {
    private Player player1;
    private Player player2;
    private Worker worker11;
    private Worker worker12;
    private Worker worker21;
    private Worker worker22;

    void startGame() {
        GameState.SetNumPlayer(2);
        GameState.NewPlayer("nickname1");
        player1 = GameState.getPlayer("nickname1#0");
        GameState.NewPlayer("nickname2");
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

    //build two blocks
    @Test
    void buildPowerActive() {
        startGame();
        BoardGame boardGame = GameState.GetBoard();
        player1.SetGodCard(new Hephaestus());
        player2.SetGodCard(new Pan());
        int[] pos11 = new int[]{1, 1};
        worker11.SetPosition(pos11);
        int[] pos12 = new int[]{3, 3};
        worker12.SetPosition(pos12);
        int[] pos21 = new int[]{2, 1};
        worker21.SetPosition(pos21);
        int[] pos22 = new int[]{0, 3};
        worker22.SetPosition(pos22);

        boardGame.setOccupant(pos11, worker11);
        boardGame.setOccupant(pos12, worker12);
        boardGame.setOccupant(pos21, worker21);
        boardGame.setOccupant(pos22, worker22);

        player1.GetGodCard().setIn_action(PowerType.ACTIVE);
        player1.GetGodCard().setCardType(GodCardType.BUILD);

        assertTrue(GameState.IsPossibleBuild(worker11, new int[]{1, 2}));
        player1.GetGodCard().Build(boardGame, worker11, new int[]{1, 2});
        assertEquals(2, boardGame.GetLevelBox(new int[]{1, 2}));
        assertEquals("ENDTURN", player1.GetGodCard().getCardType().toString());
    }

    //build one block
    @Test
    void buildPowerInactive() {
        startGame();
        BoardGame boardGame = GameState.GetBoard();
        player1.SetGodCard(new Hephaestus());
        player2.SetGodCard(new Pan());
        int[] pos11 = new int[]{1, 1};
        worker11.SetPosition(pos11);
        int[] pos12 = new int[]{3, 3};
        worker12.SetPosition(pos12);
        int[] pos21 = new int[]{2, 1};
        worker21.SetPosition(pos21);
        int[] pos22 = new int[]{0, 3};
        worker22.SetPosition(pos22);

        boardGame.setOccupant(pos11, worker11);
        boardGame.setOccupant(pos12, worker12);
        boardGame.setOccupant(pos21, worker21);
        boardGame.setOccupant(pos22, worker22);

        player1.GetGodCard().setCardType(GodCardType.BUILD);

        assertTrue(GameState.IsPossibleBuild(worker11, new int[]{1, 2}));
        player1.GetGodCard().Build(boardGame, worker11, new int[]{1, 2});
        assertEquals(1, boardGame.GetLevelBox(new int[]{1, 2}));
        assertEquals("ENDTURN", player1.GetGodCard().getCardType().toString());
    }
}