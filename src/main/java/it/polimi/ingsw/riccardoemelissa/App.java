package it.polimi.ingsw.riccardoemelissa;

import elements.BoardGame;
import elements.Player;

public class App
{
    protected static GameState g;

    public static void main( String[] args )
    {
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

    }

}
