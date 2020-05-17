package it.polimi.ingsw.riccardoemelissa.elements.card;

import it.polimi.ingsw.riccardoemelissa.GameState;
import it.polimi.ingsw.riccardoemelissa.elements.BoardGame;
import it.polimi.ingsw.riccardoemelissa.elements.Player;
import it.polimi.ingsw.riccardoemelissa.elements.PowerType;
import it.polimi.ingsw.riccardoemelissa.elements.Worker;
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
        GameState.SetNumPlayer(2);
        GameState.NewPlayer("nickname1");
        player1 = GameState.getPlayer("nickname1");
        GameState.NewPlayer("nickname2");
        player2 = GameState.getPlayer("nickname2");
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
        BoardGame boardGame = GameState.GetBoard();
        player1.SetGodCard(new Artemis());
        player2.SetGodCard(new Pan());
        int[] pos11 = new int[]{1,1};
        worker11.SetPosition(pos11);
        int[] pos12 = new int[]{3,3};
        worker12.SetPosition(pos12);
        int[] pos21 = new int[]{2,1};
        worker21.SetPosition(pos21);
        int[] pos22 = new int[]{0,3};
        worker22.SetPosition(pos22);

        boardGame.setOccupant(pos11, worker11);
        boardGame.setOccupant(pos12, worker12);
        boardGame.setOccupant(pos21, worker21);
        boardGame.setOccupant(pos22, worker22);

        player1.GetGodCard().setIn_action(PowerType.ACTIVE);

        assertTrue(GameState.IsPossibleMove(worker11, new int[]{1,2}));
        player1.GetGodCard().Move(boardGame, worker11, new int[]{1,2});
        assertArrayEquals(new int[]{1,2}, worker11.GetPosition());
        assertEquals("MOVE", player1.GetGodCard().getCardType().toString());

        assertTrue(GameState.IsPossibleMove(worker11, new int[]{1,3}));
        player1.GetGodCard().Move(boardGame, worker11, new int[]{1,3});
        assertArrayEquals(new int[]{1,3}, worker11.GetPosition());
        assertEquals("BUILD", player1.GetGodCard().getCardType().toString());

        assertTrue(GameState.IsPossibleMove(worker11, new int[]{1,4}));
        player1.GetGodCard().Move(boardGame, worker11, new int[]{1,4});
        assertArrayEquals(new int[]{1,4}, worker11.GetPosition());
        assertEquals("MOVE", player1.GetGodCard().getCardType().toString());

        assertTrue(GameState.IsPossibleMove(worker11, new int[]{2,4}));
        player1.GetGodCard().Move(boardGame, worker11, new int[]{2,4});
        assertArrayEquals(new int[]{2,4}, worker11.GetPosition());
        assertEquals("BUILD", player1.GetGodCard().getCardType().toString());
    }

    //If the worker wants to go back to his initial position -> not allowed
    @Test
    void moveTwoTimesNotAllowed () {
        startGame();
        BoardGame boardGame = GameState.GetBoard();
        player1.SetGodCard(new Artemis());
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

        assertTrue(GameState.IsPossibleMove(worker11, new int[]{1, 2}));
        player1.GetGodCard().Move(boardGame, worker11, new int[]{1, 2});
        assertArrayEquals(new int[]{1, 2}, worker11.GetPosition());
        assertEquals("MOVE", player1.GetGodCard().getCardType().toString());

        assertFalse(GameState.IsPossibleMove(worker11, pos11));
        assertEquals("MOVE", player1.GetGodCard().getCardType().toString());
    }

    @Test
    void moveTwoTimesBlockedByAthena () {
        startGame();
        BoardGame boardGame = GameState.GetBoard();
        player1.SetGodCard(new Artemis());
        player2.SetGodCard(new Athena());
        player2.GetGodCard().setOpponentTrue("true");
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

        boardGame.DoBuild(new int[]{2, 2});
        player2.GetGodCard().Move(boardGame, worker21, new int[]{2, 2});

        player1.GetGodCard().setIn_action(PowerType.ACTIVE);

        assertTrue(GameState.IsPossibleMove(worker11, new int[]{1, 2}));
        player1.GetGodCard().Move(boardGame, worker11, new int[]{1, 2});
        //assertArrayEquals(new int[]{1, 2}, worker11.GetPosition());
        //assertEquals("MOVE", player1.GetGodCard().getCardType().toString());
        //worker11.SetPosition(new int[]{1, 2});

        boardGame.DoBuild(new int[]{1, 3});
        assertFalse(GameState.IsPossibleMove(worker11, new int[]{1, 3}));
    }
}