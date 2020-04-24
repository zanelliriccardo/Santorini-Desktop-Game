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
import java.util.Random;


public class JsonReader {
    private JSONParser parser;
    private JSONObject obj;

    public JsonReader(){
        parser = new JSONParser();
        obj=null;
    }

    public int[] RandomNumbers(int n, int bound)
    {
        Random r = new Random(System.currentTimeMillis());

        int [] random_numbers = new int[n];

        for (int i = 0; i < n; i++)
        {
            random_numbers[i]= r.nextInt(bound);
        }
        return random_numbers;
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



    /*public String[] GodChosen (int n) //n= number of players
    {
        String[] gods = new String[n];

        try {
            obj = (JSONObject) parser.parse((new FileReader("@../Resources/GodList.json")));
        } catch (FileNotFoundException e)
        {
            System.out.println(("File Not Found Exception!\n"));
        } catch (IOException i){
            System.out.print("I/O EXCEPTION!\n");
        } catch (ParseException p) {
            System.out.print("Parse EXCEPTION!\n");
        }

        JSONArray array = (JSONArray) obj.get("GodList");

        int [] random_numbers = RandomNumbers(n, array.size());
        JSONObject nome;

        for (int i = 0; i<random_numbers.length; i++) {

            for (Object o : array) {
                nome = (JSONObject) array.get(random_numbers[i]);
                gods[i] = (String) nome.get("name");
            }
        }
        return gods;
    }

     */
}