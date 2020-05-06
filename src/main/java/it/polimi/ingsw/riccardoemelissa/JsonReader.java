package it.polimi.ingsw.riccardoemelissa;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
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
        String[] gods_chosen = new String[n*2];

        URL path=getClass().getResource("GodList.json");


        try {
            obj = (JSONObject) parser.parse((new FileReader(path.getPath())));
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

        for (int i = 0; i<n ; i++)
        {
            gods_chosen[i*2] = gods.get(i);

            for(Object o : god_list)
            {
                god = (JSONObject) o;

                if(god.get("name").equals(gods_chosen[i*2]))
                    gods_chosen[i*2 + 1] = (String) god.get("path");
            }
        }

        return gods_chosen;
    }
}