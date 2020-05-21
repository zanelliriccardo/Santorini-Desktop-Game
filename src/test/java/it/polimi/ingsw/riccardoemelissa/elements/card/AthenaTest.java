package it.polimi.ingsw.riccardoemelissa.elements.card;

import it.polimi.ingsw.riccardoemelissa.GameState;
import it.polimi.ingsw.riccardoemelissa.elements.GodCardType;
import it.polimi.ingsw.riccardoemelissa.elements.Player;
import it.polimi.ingsw.riccardoemelissa.elements.PowerType;
import it.polimi.ingsw.riccardoemelissa.elements.Worker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AthenaTest {
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
    void getIn_action() {
        startGame();
        player1.setGodCard(new Artemis());
        player2.setGodCard(new Athena());
        player2.getGodCard().setOpponentTrue("true");

        player2.getGodCard().setIn_action(PowerType.ACTIVE);
        assertEquals("PASSIVE", player2.getGodCard().getIn_action().toString());
    }

    @Test
    void resetCard() {
        startGame();
        player1.setGodCard(new Artemis());
        player2.setGodCard(new Athena());
        player2.getGodCard().setOpponentTrue("true");

        player2.getGodCard().setCardType(GodCardType.BUILD);
        assertEquals("BUILD", player2.getGodCard().getCardType().toString());
        player2.getGodCard().resetCard();
        assertEquals("MOVE", player2.getGodCard().getCardType().toString());
    }
}