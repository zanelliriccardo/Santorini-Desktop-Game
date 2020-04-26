package it.polimi.ingsw.riccardoemelissa;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class JsonReader {
    private JSONParser parser;
    private JSONObject obj;

    public JsonReader(){
        parser = new JSONParser();
        obj=null;
    }

    public String[] GodsInGame (int n) {
        ArrayList<String> gods = new ArrayList<>();
        String[] gods_chosen = new String[n];

        try {
            obj = (JSONObject) parser.parse((new FileReader("/Users/Utente/Desktop/PROGETTO INGSW/progettoingsw/src/main/java/Resources/GodList.json")));
        } catch (FileNotFoundException e) {
            System.out.println(("File Not Found Exception!\n"));
        } catch (IOException i) {
            System.out.print("I/O EXCEPTION!\n");
        } catch (ParseException p) {
            System.out.print("Parse EXCEPTION!\n");
        }

        JSONArray array = (JSONArray) obj.get("GodList");
        JSONObject name;

        for (Object o : array) {
            name = (JSONObject) o;
            gods.add((String) name.get("name"));
        }

        Collections.shuffle(gods);

        for (int i = 0; i<n ; i++)
        {
            gods_chosen[i] = gods.get(i);
        }
        return gods_chosen;
    }
}