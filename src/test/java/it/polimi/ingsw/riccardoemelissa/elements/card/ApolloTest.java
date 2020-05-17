package it.polimi.ingsw.riccardoemelissa.elements.card;

import it.polimi.ingsw.riccardoemelissa.GameState;
import it.polimi.ingsw.riccardoemelissa.elements.BoardGame;
import it.polimi.ingsw.riccardoemelissa.elements.Player;
import it.polimi.ingsw.riccardoemelissa.elements.PowerType;
import it.polimi.ingsw.riccardoemelissa.elements.Worker;
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

    // The box is occupied by a opponent's worker
    @Test
    void moveInOccupiedBox (){
        startGame();
        BoardGame boardGame = GameState.GetBoard();
        player1.SetGodCard(new Apollo());
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

        assertTrue(GameState.IsPossibleMove(worker11, pos21));

        player1.GetGodCard().Move(boardGame, worker11, pos21);
        assertArrayEquals(pos21, worker11.GetPosition());
        assertArrayEquals(pos11, worker21.GetPosition());
    }

    //The box is occupied by the player's worker
    @Test
    void moveInOccupiedBox2 (){
        startGame();
        BoardGame boardGame = GameState.GetBoard();
        player1.SetGodCard(new Apollo());
        player2.SetGodCard(new Pan());
        int[] pos11 = new int[]{1,1};
        worker11.SetPosition(pos11);
        int[] pos12 = new int[]{1,2};
        worker12.SetPosition(pos12);
        int[] pos21 = new int[]{2,1};
        worker21.SetPosition(pos21);
        int[] pos22 = new int[]{0,3};
        worker22.SetPosition(pos22);

        boardGame.setOccupant(pos11, worker11);
        boardGame.setOccupant(pos12, worker12);
        boardGame.setOccupant(pos21, worker21);
        boardGame.setOccupant(pos22, worker22);

        assertFalse(GameState.IsPossibleMove(worker11, pos12));
    }

    // The opponent's Godcard is Athena, but she is not in action
    @Test
    void moveAthena (){
        startGame();
        BoardGame boardGame = GameState.GetBoard();
        player1.SetGodCard(new Apollo());
        player2.SetGodCard(new Athena());
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

        boardGame.DoBuild(pos21);
        player2.GetGodCard().setIn_action(PowerType.DISABLE);

        assertTrue(GameState.IsPossibleMove(worker11, pos21));

        player1.GetGodCard().Move(boardGame, worker11, pos21);
        assertArrayEquals(pos21, worker11.GetPosition());
        assertArrayEquals(pos11, worker21.GetPosition());
    }

    // The opponent's Godcard is Athena and she is in action
    @Test
    void moveAthena2 (){
        startGame();
        BoardGame boardGame = GameState.GetBoard();
        player1.SetGodCard(new Apollo());
        player2.SetGodCard(new Athena());
        player2.GetGodCard().setOpponentTrue("true");
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

        boardGame.DoBuild(pos21);
        boardGame.DoBuild(new int[]{0,4});
        player2.GetGodCard().Move(boardGame, worker22, new int[]{0,4}); //in_action = true
        String result = player2.GetGodCard().Move(boardGame, worker11, pos21).toString();

        assertEquals("ERROR", result);

        assertFalse(GameState.IsPossibleMove(worker11, pos21));
    }

}