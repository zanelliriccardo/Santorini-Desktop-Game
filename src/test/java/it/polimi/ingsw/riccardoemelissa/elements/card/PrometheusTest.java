package it.polimi.ingsw.riccardoemelissa.elements.card;

import it.polimi.ingsw.riccardoemelissa.GameState;
import it.polimi.ingsw.riccardoemelissa.elements.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrometheusTest {
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
    void powerActive() {
        startGame();
        BoardGame boardGame = GameState.GetBoard();
        player1.SetGodCard(new Prometheus());
        player2.SetGodCard(new Athena());
        player2.GetGodCard().setOpponentTrue("true");
        int[] pos11 = new int[]{0, 0};
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
        assertEquals("BUILD", player1.GetGodCard().getCardType().toString());

        boardGame.DoBuild(new int[]{0,4});
        player2.GetGodCard().Move(boardGame,worker22,new int[]{0,4}); //in action = true

        assertTrue(GameState.IsPossibleBuild(worker11, new int[]{0,1}));
        player1.GetGodCard().Build(boardGame, worker11, new int[]{0,1});
        assertEquals("MOVE", player1.GetGodCard().getCardType().toString());

        assertFalse(GameState.IsPossibleMove(worker11, new int[]{0,1}));

        assertTrue(GameState.IsPossibleMove(worker11, new int[]{1,1}));
        player1.GetGodCard().Move(boardGame,worker11,new int[]{1,1});
        assertEquals("BUILD", player1.GetGodCard().getCardType().toString());
    }

    @Test
    void powerInactive() {
        startGame();
        BoardGame boardGame = GameState.GetBoard();
        player1.SetGodCard(new Prometheus());
        player2.SetGodCard(new Athena());
        player2.GetGodCard().setOpponentTrue("true");
        int[] pos11 = new int[]{0, 0};
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

        assertEquals("MOVE", player1.GetGodCard().getCardType().toString());

        boardGame.DoBuild(new int[]{0,4});
        player2.GetGodCard().Move(boardGame,worker22,new int[]{0,4}); //in action = true

        boardGame.DoBuild(new int[]{0,1});
        assertFalse(GameState.IsPossibleMove(worker11, new int[]{0,1}));

        assertTrue(GameState.IsPossibleMove(worker11, new int[]{1,1}));
        player1.GetGodCard().Move(boardGame,worker11,new int[]{1,1});
        assertEquals("BUILD", player1.GetGodCard().getCardType().toString());
    }

    @Test
    void getIn_action() {
        startGame();
        player1.SetGodCard(new Prometheus());
        player2.SetGodCard(new Athena());
        player2.GetGodCard().setOpponentTrue("true");

        player1.GetGodCard().setIn_action(PowerType.ACTIVE);
        assertEquals("ACTIVE", player1.GetGodCard().getIn_action().toString());

        player1.GetGodCard().setIn_action(PowerType.DISABLE);
        assertEquals("DISABLE", player1.GetGodCard().getIn_action().toString());
    }

    @Test
    void resetCard() {
        startGame();
        player1.SetGodCard(new Prometheus());
        player2.SetGodCard(new Athena());
        player2.GetGodCard().setOpponentTrue("true");

        player1.GetGodCard().setCardType(GodCardType.BUILD);
        assertEquals("BUILD", player1.GetGodCard().getCardType().toString());
        player1.GetGodCard().resetCard();
        assertEquals("MOVE", player1.GetGodCard().getCardType().toString());
    }

}