package it.polimi.ingsw.riccardoemelissa.reader;

import it.polimi.ingsw.riccardoemelissa.GameState;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.*;


public class JsonReader {
    private JSONParser parser;
    private JSONObject obj;

    public JsonReader() {
        parser = new JSONParser();
        obj = null;
    }

    /**
     * Choose Gods in game
     *
     * Based on the number of players chosen for the game, which is indicate by the parameter n,
     * this method randomly chooses n gods
     *
     * @param n
     * @return
     * @throws FileNotFoundException
     * @throws URISyntaxException
     */
    public String[] godsInGame(int n) throws FileNotFoundException, URISyntaxException {
        ArrayList<String> gods = new ArrayList<>();
        String[] gods_chosen = new String[n*3];

        String path = (getClass().getResource("god_list.json")).getPath();

        try {
            obj = (JSONObject) parser.parse(new FileReader(path));
        } catch (FileNotFoundException e) {
            System.out.println(("File Not Found Exception!\n"));
        } catch (IOException i) {
            System.out.print("I/O EXCEPTION!\n");
        } catch (ParseException p) {
            System.out.print("Parse EXCEPTION!\n");
        }

        JSONArray god_list = (JSONArray) obj.get("GodList");
        JSONObject god;

        for (Object o : god_list) {
            god = (JSONObject) o;
            gods.add((String) god.get("name"));
        }

        Collections.shuffle(gods);

        for (int i = 0; i < n; i++) {
            gods_chosen[i * 3] = gods.get(i);

            for (Object o : god_list) {
                god = (JSONObject) o;

                if (god.get("name").equals(gods_chosen[i*3]))
                {
                    gods_chosen[i * 3 + 1] = (String) god.get("path");
                    gods_chosen[i * 3 + 2] = (String) god.get("opponent_turn");
                }
            }
        }

        return gods_chosen;
    }
}