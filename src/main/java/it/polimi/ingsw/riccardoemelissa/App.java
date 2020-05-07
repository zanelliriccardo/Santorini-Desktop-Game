package it.polimi.ingsw.riccardoemelissa;


import java.io.IOException;


public class App
{
    public static void main( String[] args ) throws IOException {
        MultiEchoServer echoServer = new MultiEchoServer(35500);
        echoServer.startServer();
    }
}



