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
    public static void main( String[] args ) {
        MultiEchoServer echoServer = new MultiEchoServer(1337);
        echoServer.startServer();
    }
}



