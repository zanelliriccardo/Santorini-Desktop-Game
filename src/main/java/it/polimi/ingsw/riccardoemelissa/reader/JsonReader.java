package it.polimi.ingsw.riccardoemelissa.reader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;


public class JsonReader {
    private JSONParser parser;
    private JSONObject obj;

    public JsonReader() {
        parser = new JSONParser();
        obj = null;
    }

    public String[] GodsInGame(int n) throws FileNotFoundException, URISyntaxException {
        ArrayList<String> gods = new ArrayList<>();
        String[] gods_chosen = new String[n*2];

        //"/Users/Utente/Desktop/PROGETTO INGSW/progettoingsw/src/main/resources/it/polimi/ingsw/riccardoemelissa/reader/GodList.json"
        //"file:/C:/Users/Utente/Desktop/PROGETTO INGSW/progettoingsw/src/main/resources/it/polimi/ingsw/riccardoemelissa/reader/god_list.json

        String path = (getClass().getResource("god_list.json")).getPath();
        //String path =  "/Users/Utente/Desktop/PROGETTOINGSW/progettoingsw/src/main/resources/it/polimi/ingsw/riccardoemelissa/reader/god_list.json";

        System.out.println("The path is : " + path);

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
            gods_chosen[i * 2] = gods.get(i);
            System.out.println("pos " + i*2 + " --> name : " + gods_chosen[i*2]);

            for (Object o : god_list) {
                god = (JSONObject) o;

                if (god.get("name").equals(gods_chosen[i*2]))
                {
                    gods_chosen[i * 2 + 1] = (String) god.get("path");
                    System.out.println("pos " + (i*2+1) + " --> path : " + gods_chosen[i*2 + 1]);
                }
            }
        }

        return gods_chosen;
    }

    /*public static void main( String[] args ) throws IOException, ParseException, URISyntaxException {
        JsonReader jsonReader = new JsonReader();
        jsonReader.GodsInGame(3);
    }

     */
}