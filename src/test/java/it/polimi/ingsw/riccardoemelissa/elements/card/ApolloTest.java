package it.polimi.ingsw.riccardoemelissa.elements.card;

import it.polimi.ingsw.riccardoemelissa.GameState;
import it.polimi.ingsw.riccardoemelissa.elements.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApolloTest {
    private Player player1;
    private Player player2;
    private Worker worker11;
    private Worker worker12;
    private Worker worker21;
    private Worker worker22;

    void startGame (){
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
    void moveClassic(){
        startGame();
        BoardGame boardGame = GameState.getBoard();
        player1.setGodCard(new Apollo());
        player2.setGodCard(new Pan());
        int[] pos11 = new int[]{0,0};
        worker11.setPosition(pos11);
        int[] pos12 = new int[]{3,3};
        worker12.setPosition(pos12);
        int[] pos21 = new int[]{2,1};
        worker21.setPosition(pos21);
        int[] pos22 = new int[]{4,4};
        worker22.setPosition(pos22);

        boardGame.setOccupant(pos11, worker11);
        boardGame.setOccupant(pos12, worker12);
        boardGame.setOccupant(pos21, worker21);
        boardGame.setOccupant(pos22, worker22);

        assertTrue(GameState.isPossibleMove(worker11, new int[]{0,1}));
        player1.getGodCard().move(boardGame, worker11, new int[]{0,1});
        assertEquals("BUILD", player1.getGodCard().getCardType().toString());
        assertArrayEquals(new int[]{0,1}, worker11.getPosition());

        boardGame.buildDome(new int[]{4,3});
        assertTrue(GameState.isPossibleMove(worker22, new int[]{3,4}));
        player1.getGodCard().move(boardGame, worker11, new int[]{3,4});
        assertEquals("BUILD", player1.getGodCard().getCardType().toString());
        assertArrayEquals(new int[]{3,4}, worker11.getPosition());

        boardGame.buildDome(new int[]{2,3});
        assertFalse(GameState.isPossibleMove(worker12, new int[]{2,3}));
    }

    // The box is occupied by a opponent's worker
    @Test
    void moveInOccupiedBox (){
        startGame();
        BoardGame boardGame = GameState.getBoard();
        player1.setGodCard(new Apollo());
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

        assertTrue(GameState.isPossibleMove(worker11, pos21));

        player1.getGodCard().move(boardGame, worker11, pos21);
        assertEquals("BUILD", player1.getGodCard().getCardType().toString());
        assertArrayEquals(pos21, worker11.getPosition());
        assertArrayEquals(pos11, worker21.getPosition());
    }

    //The box is occupied by the player's worker
    @Test
    void moveInOccupiedBox2 (){
        startGame();
        BoardGame boardGame = GameState.getBoard();
        player1.setGodCard(new Apollo());
        player2.setGodCard(new Pan());
        int[] pos11 = new int[]{1,1};
        worker11.setPosition(pos11);
        int[] pos12 = new int[]{1,2};
        worker12.setPosition(pos12);
        int[] pos21 = new int[]{2,1};
        worker21.setPosition(pos21);
        int[] pos22 = new int[]{0,3};
        worker22.setPosition(pos22);

        boardGame.setOccupant(pos11, worker11);
        boardGame.setOccupant(pos12, worker12);
        boardGame.setOccupant(pos21, worker21);
        boardGame.setOccupant(pos22, worker22);

        assertFalse(GameState.isPossibleMove(worker11, pos12));
    }

    // The opponent's Godcard is Athena, but she is not in action
    @Test
    void moveAthena (){
        startGame();
        BoardGame boardGame = GameState.getBoard();
        player1.setGodCard(new Apollo());
        player2.setGodCard(new Athena());
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

        boardGame.doBuild(pos21);
        player2.getGodCard().setIn_action(PowerType.DISABLE);

        assertTrue(GameState.isPossibleMove(worker11, pos21));

        player1.getGodCard().move(boardGame, worker11, pos21);
        assertEquals("BUILD", player1.getGodCard().getCardType().toString());
        assertArrayEquals(pos21, worker11.getPosition());
        assertArrayEquals(pos11, worker21.getPosition());
    }

    // The opponent's Godcard is Athena and she is in action
    @Test
    void moveAthena2 (){
        startGame();
        BoardGame boardGame = GameState.getBoard();
        player1.setGodCard(new Apollo());
        player2.setGodCard(new Athena());
        player2.getGodCard().setOpponentTrue("true");
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

        boardGame.doBuild(pos21);
        boardGame.doBuild(new int[]{0,4});
        player2.getGodCard().move(boardGame, worker22, new int[]{0,4}); //in_action = true
        String result = player2.getGodCard().move(boardGame, worker11, pos21).toString();

        assertEquals("ERROR", result);
        assertFalse(GameState.isPossibleMove(worker11, pos21));
    }

    @Test
    void getIn_action() {
        startGame();
        player1.setGodCard(new Apollo());
        player2.setGodCard(new Athena());
        player2.getGodCard().setOpponentTrue("true");

        player1.getGodCard().setIn_action(PowerType.ACTIVE);
        assertEquals("PASSIVE", player1.getGodCard().getIn_action().toString());
    }

    @Test
    void resetCard() {
        startGame();
        player1.setGodCard(new Apollo());
        player2.setGodCard(new Athena());
        player2.getGodCard().setOpponentTrue("true");

        player1.getGodCard().setCardType(GodCardType.BUILD);
        assertEquals("BUILD", player1.getGodCard().getCardType().toString());
        player1.getGodCard().resetCard();
        assertEquals("MOVE", player1.getGodCard().getCardType().toString());
    }
}