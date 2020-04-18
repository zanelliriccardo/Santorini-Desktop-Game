package it.polimi.ingsw.riccardoemelissa;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class JsonReader {
    private JSONParser parser;
    private JSONObject obj;

    public JsonReader(){
        parser = new JSONParser();
        obj=null;
    }

    public int[] RandomNumbers(int n)
    {
        Random r = new Random(System.currentTimeMillis());

        int [] random_numbers = new int[n];

        for (int i = 0; i < n; i++)
        {
            random_numbers[i]= r.nextInt(9);
        }
        return random_numbers;
    }

    public void GodChosen (){
        ArrayList<Object> gods = new ArrayList<Object>();

        try {
            obj = (JSONObject) parser.parse((new FileReader("/Users/Utente/Desktop/PROGETTO INGSW/progettoingsw/src/main/java/Resource/GodList.json")));
        } catch (FileNotFoundException e)
        {
            System.out.println(("File Not Found Exception!\n"));
        } catch (IOException i){
            System.out.print("I/O EXCEPTION!\n");
        } catch (ParseException p) {
            System.out.print("Parse EXCEPTION!\n");
        }

        JSONArray array = (JSONArray) obj.get("GodList");

        int [] random_numbers = RandomNumbers(3);

        for (int k = 0; k < random_numbers.length; k++)
        {
            System.out.println(array.get(random_numbers[k]));
        }
    }
}