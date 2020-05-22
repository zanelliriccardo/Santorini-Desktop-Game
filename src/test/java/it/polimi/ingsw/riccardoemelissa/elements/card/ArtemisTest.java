package it.polimi.ingsw.riccardoemelissa.elements.card;

import it.polimi.ingsw.riccardoemelissa.GameState;
import it.polimi.ingsw.riccardoemelissa.elements.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArtemisTest {
    private Player player1;
    private Player player2;
    private Worker worker11;
    private Worker worker12;
    private Worker worker21;
    private Worker worker22;

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
    void moveTwoTimes ()
    {
        startGame();
        BoardGame boardGame = GameState.getBoard();
        player1.setGodCard(new Artemis());
        player2.setGodCard(new Pan());
        int[] pos11 = new int[]{1,1};
        worker11.setPosition(pos11);
        int[] pos12 = new int[]{3,3};
        worker12.setPosition(pos12);
        int[] pos21 = new int[]{2,1};
        worker21.setPosition(pos21);
        int[] pos22 = new int[]{0,3};
        worker22.setPosition(pos22);

        boardGame.setOccupant(pos11, worker11);
        boardGame.setOccupant(pos12, worker12);
        boardGame.setOccupant(pos21, worker21);
        boardGame.setOccupant(pos22, worker22);

        boardGame.setActivePlayer(player1);

        player1.getGodCard().setIn_action(PowerType.ACTIVE);

        assertTrue(GameState.isPossibleMove(worker11, new int[]{1,2}));
        player1.getGodCard().move(boardGame, worker11, new int[]{1,2});
        assertArrayEquals(new int[]{1,2}, worker11.getPosition());
        assertEquals("MOVE", player1.getGodCard().getCardType().toString());

        assertTrue(GameState.isPossibleMove(worker11, new int[]{1,3}));
        player1.getGodCard().move(boardGame, worker11, new int[]{1,3});
        assertArrayEquals(new int[]{1,3}, worker11.getPosition());
        assertEquals("BUILD", player1.getGodCard().getCardType().toString());

        assertTrue(GameState.isPossibleMove(worker11, new int[]{1,4}));
        player1.getGodCard().move(boardGame, worker11, new int[]{1,4});
        assertArrayEquals(new int[]{1,4}, worker11.getPosition());
        assertEquals("MOVE", player1.getGodCard().getCardType().toString());

        assertTrue(GameState.isPossibleMove(worker11, new int[]{2,4}));
        player1.getGodCard().move(boardGame, worker11, new int[]{2,4});
        assertArrayEquals(new int[]{2,4}, worker11.getPosition());
        assertEquals("BUILD", player1.getGodCard().getCardType().toString());
    }

    //If the worker wants to go back to his initial position -> not allowed
    @Test
    void moveTwoTimesNotAllowed () {
        startGame();
        BoardGame boardGame = GameState.getBoard();
        player1.setGodCard(new Artemis());
        player2.setGodCard(new Pan());
        int[] pos11 = new int[]{1, 1};
        worker11.setPosition(pos11);
        int[] pos12 = new int[]{3, 3};
        worker12.setPosition(pos12);
        int[] pos21 = new int[]{2, 1};
        worker21.setPosition(pos21);
        int[] pos22 = new int[]{0, 3};
        worker22.setPosition(pos22);

        boardGame.setOccupant(pos11, worker11);
        boardGame.setOccupant(pos12, worker12);
        boardGame.setOccupant(pos21, worker21);
        boardGame.setOccupant(pos22, worker22);

        boardGame.setActivePlayer(player1);

        player1.getGodCard().setIn_action(PowerType.ACTIVE);

        assertTrue(GameState.isPossibleMove(worker11, new int[]{1, 2}));
        player1.getGodCard().move(boardGame, worker11, new int[]{1, 2});
        assertArrayEquals(new int[]{1, 2}, worker11.getPosition());
        assertEquals("MOVE", player1.getGodCard().getCardType().toString());

        assertFalse(GameState.isPossibleMove(worker11, pos11));
        assertEquals("MOVE", player1.getGodCard().getCardType().toString());
    }

    @Test
    void moveTwoTimesBlockedByAthena () {
        startGame();
        BoardGame boardGame = GameState.getBoard();
        player1.setGodCard(new Artemis());
        player2.setGodCard(new Athena());
        player2.getGodCard().setOpponentTrue("true");
        int[] pos11 = new int[]{1, 1};
        worker11.setPosition(pos11);
        int[] pos12 = new int[]{3, 3};
        worker12.setPosition(pos12);
        int[] pos21 = new int[]{2, 1};
        worker21.setPosition(pos21);
        int[] pos22 = new int[]{0, 3};
        worker22.setPosition(pos22);

        boardGame.setOccupant(pos11, worker11);
        boardGame.setOccupant(pos12, worker12);
        boardGame.setOccupant(pos21, worker21);
        boardGame.setOccupant(pos22, worker22);

        boardGame.setActivePlayer(player2);

        boardGame.doBuild(new int[]{2, 2});
        player2.getGodCard().move(boardGame, worker21, new int[]{2, 2});

        boardGame.setActivePlayer(player1);

        player1.getGodCard().setIn_action(PowerType.ACTIVE);

        assertTrue(GameState.isPossibleMove(worker11, new int[]{1, 2}));
        player1.getGodCard().move(boardGame, worker11, new int[]{1, 2});

        boardGame.doBuild(new int[]{1, 3});
        assertFalse(GameState.isPossibleMove(worker11, new int[]{1, 3}));
    }

    @Test
    void getIn_action() {
        startGame();
        player1.setGodCard(new Artemis());
        player2.setGodCard(new Athena());
        player2.getGodCard().setOpponentTrue("true");

        player1.getGodCard().setIn_action(PowerType.ACTIVE);
        assertEquals("ACTIVE", player1.getGodCard().getIn_action().toString());

        player1.getGodCard().setIn_action(PowerType.DISABLE);
        assertEquals("DISABLE", player1.getGodCard().getIn_action().toString());
    }

    @Test
    void resetCard() {
        startGame();
        player1.setGodCard(new Artemis());
        player2.setGodCard(new Athena());
        player2.getGodCard().setOpponentTrue("true");

        player1.getGodCard().setCardType(GodCardType.BUILD);
        assertEquals("BUILD", player1.getGodCard().getCardType().toString());
        player1.getGodCard().resetCard();
        assertEquals("MOVE", player1.getGodCard().getCardType().toString());
    }
}