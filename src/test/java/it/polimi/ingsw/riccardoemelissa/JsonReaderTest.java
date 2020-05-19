package it.polimi.ingsw.riccardoemelissa;

import it.polimi.ingsw.riccardoemelissa.reader.JsonReader;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends Object {

    @Test
    void godsInGame() throws FileNotFoundException, URISyntaxException {
        JsonReader jsonReader = new JsonReader();
        int num_players = 3;
        String[] gods = jsonReader.godsInGame(num_players);
        int count = 0;

        for(int i = 0; i < gods.length; i= i+3)
        {
            switch (gods[i])
            {
                case "it.polimi.ingsw.riccardoemelissa.elements.card.Apollo":
                    count++;
                    assertEquals("Apollo.png", gods[i+1]);
                    assertEquals("false", gods[i+2]);
                    break;

                case "it.polimi.ingsw.riccardoemelissa.elements.card.Artemis":
                    count++;
                    assertEquals("Artemis.png", gods[i+1]);
                    assertEquals("false", gods[i+2]);
                    break;

                case "it.polimi.ingsw.riccardoemelissa.elements.card.Athena":
                    count++;
                    assertEquals("Athena.png", gods[i+1]);
                    assertEquals("true", gods[i+2]);
                    break;

                case "it.polimi.ingsw.riccardoemelissa.elements.card.Atlas":
                    count++;
                    assertEquals("Atlas.png", gods[i+1]);
                    assertEquals("false", gods[i+2]);
                    break;

                case "it.polimi.ingsw.riccardoemelissa.elements.card.Demeter":
                    count++;
                    assertEquals("Demeter.png", gods[i+1]);
                    assertEquals("false", gods[i+2]);
                    break;

                case "it.polimi.ingsw.riccardoemelissa.elements.card.Hephaestus":
                    count++;
                    assertEquals("Hephaestus.png", gods[i+1]);
                    assertEquals("false", gods[i+2]);
                    break;

                case "it.polimi.ingsw.riccardoemelissa.elements.card.Minotaur":
                    count++;
                    assertEquals("Minotaur.png", gods[i+1]);
                    assertEquals("false", gods[i+2]);
                    break;

                case "it.polimi.ingsw.riccardoemelissa.elements.card.Pan":
                    count++;
                    assertEquals("Pan.png", gods[i+1]);
                    assertEquals("false", gods[i+2]);
                    break;

                case "it.polimi.ingsw.riccardoemelissa.elements.card.Prometheus":
                    count++;
                    assertEquals("Prometheus.png", gods[i+1]);
                    assertEquals("false", gods[i+2]);
                    break;

            }
        }
        assertEquals(3, count);
    }
}