package it.polimi.ingsw.riccardoemelissa;

import elements.BoardGame;
import elements.Player;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class App
{
    protected static GameState g;

    public static void main( String[] args ) throws IOException {
        MultiEchoServer echoServer = new MultiEchoServer(1337);
        echoServer.startServer();

        while(true)
        {
            Player[] p=g.GetPlayers();
            if(p[g.GetPlayerNumber()]==null)
                continue;

            break;
        }

        g.SetTurnOrder();
        g.SetProprietaryWorker();
        g.GodsChosen();

        JsonReader jasonReader = new JsonReader();





}

}
