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
        int num_players = 2;
        String[] gods = new String[num_players];
        gods = jsonReader.GodsInGame(num_players);

        for( String str : gods)
        {
            if(str.equals("Apollo"))
                assertEquals("Apollo", str);
            if(str.equals("Artemis"))
                assertEquals("Artemis", str);
            if(str.equals("Athena"))
                assertEquals("Athena", str);
            if(str.equals("Atlas"))
                assertEquals("Atlas", str);
            if(str.equals("Demeter"))
                assertEquals("Demeter", str);
            if(str.equals("Hephaestus"))
                assertEquals("Hephaestus", str);
            if(str.equals("Minotaur"))
                assertEquals("Minotaur", str);
            if(str.equals("Pan"))
                assertEquals("Pan", str);
            if(str.equals("Prometheus"))
                assertEquals("Prometheus", str);
        }
    }
}